package ctgen;

import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.extension.ExtensionManager;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.Log;
import org.tzi.use.util.USEWriter;
import org.tzi.use.main.shell.*;
import org.tzi.use.parser.use.USECompiler;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.UIDefaults;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.tzi.use.config.Options;
import org.tzi.use.main.*;
import org.tzi.use.main.runtime.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.tzi.use.uml.ocl.expr.*;

public class CTGen {

	private static void initGUIdefaults() {
		MetalLookAndFeel.setCurrentTheme(new MyTheme());
	}
	
	public static void main(String args[]) {
		// set System.out to the OldUSEWriter to protocol the output.
		System.setOut(USEWriter.getInstance().getOut());
		// set System.err to the OldUSEWriter to protocol the output.
		System.setErr(USEWriter.getInstance().getErr());

		// read and set global options, setup application properties
		Options.processArgs(args);
		if (Options.doGUI) {
			initGUIdefaults();
		}

		Session session = new Session();
		IRuntime pluginRuntime = null;
		MModel model = null;
		MSystem system = null;

		if (!Options.disableExtensions) {
			ExtensionManager.EXTENSIONS_FOLDER = Options.homeDir + "/oclextensions";
			ExtensionManager.getInstance().loadExtensions();
		}
		
		// Plugin Framework
		if (Options.doPLUGIN) {
			// create URL from plugin directory
			Path pluginDirURL = Options.pluginDir;
			Log.verbose("Plugin path: [" + pluginDirURL + "]");
			Class<?> mainPluginRuntimeClass = null;
			try {
				mainPluginRuntimeClass = Class
						.forName("org.tzi.use.runtime.MainPluginRuntime");
			} catch (ClassNotFoundException e) {
				Log
						.error("Could not load PluginRuntime. Probably use-runtime-...jar is missing.\n"
								+ "Try starting use with -noplugins switch.\n"
								+ e.getMessage());
				System.exit(1);
			}
			try {
				Method run = mainPluginRuntimeClass.getMethod("run",
						new Class[] { Path.class });
				pluginRuntime = (IRuntime) run.invoke(null,
						new Object[] { pluginDirURL });
				Log.debug("Starting plugin runtime, got class ["
						+ pluginRuntime.getClass() + "]");
			} catch (Exception e) {
				e.printStackTrace();
				Log.error("FATAL ERROR.");
				System.exit(1);
			}
		}

		if (args.length != 2) {
			System.err.print("Wrong number of parameters");
			System.exit(1);
		}
		String inputFile = args[0];
		String outFile   = args[1];
		
		long startTime = System.currentTimeMillis();
		// compile spec if filename given as argument
		try (FileInputStream specStream = new FileInputStream(inputFile)){
				Log.verbose("compiling specification...");
				model = USECompiler.compileSpecification(specStream,
						inputFile, new PrintWriter(System.err),
						new ModelFactory());
			} catch (FileNotFoundException e) {
				Log.error("File `" + inputFile + "' not found.");
				System.exit(1);
			} catch (IOException e1) {
				// close failed
			}

			// compile errors?
			if (model == null) {
				System.exit(1);
			}

			if(!Options.quiet){
				Options.setLastDirectory(new java.io.File(Options.specFilename).getAbsoluteFile().toPath().getParent());
			}
			if (!Options.testMode)
				Options.getRecentFiles().push(Options.specFilename);
			
			if (Options.compileOnly) {
				Log.verbose("no errors.");
				if (Options.compileAndPrint) {
					MMVisitor v = new MMPrintVisitor(new PrintWriter(
							System.out, true));
					model.processWithVisitor(v);
				}
				System.exit(0);
			}

			// print some info about model
			Log.verbose(model.getStats());

			// create system
			system = new MSystem(model);
		
		session.setSystem(system);

		// Do not open GUI
		// if (Options.doGUI) {
		if (false) {
			Class<?> mainWindowClass = null;
			try {
				mainWindowClass = Class
						.forName("org.tzi.use.gui.main.MainWindow");
				Log.debug("Initializing [" + mainWindowClass.toString() + "]");
			} catch (ClassNotFoundException e) {
				Log
						.error("Could not load GUI. Probably use-gui-...jar is missing.\n"
								+ "Try starting use with -nogui switch.\n" + e);
				System.exit(1);
			}
			if (mainWindowClass == null) {
				Log.error("MainWindow could not be initialized! Exiting!");
				System.exit(1);
			}
			try {
				if (pluginRuntime == null) {
					Log.debug("Starting gui without plugin runtime!");
					Method create = mainWindowClass.getMethod("create",
							new Class[] { Session.class });
					Log.debug("Invoking method create with ["
							+ session.toString() + "]");
					create.invoke(null, new Object[] { session });
				} else {
					Log.debug("Starting gui with plugin runtime.");
					Method create = mainWindowClass.getMethod("create",
							new Class[] { Session.class, IRuntime.class });
					Log.debug("Invoking method create with ["
							+ session.toString() + "] ["
							+ pluginRuntime.toString() + "]");
					create
							.invoke(null,
									new Object[] { session, pluginRuntime });
				}
			} catch (Exception e) {
				Log.error("FATAL ERROR.", e);
				System.exit(1);
			}
		}

		
		generateClassifyingTerms(model, outFile);
		
		long endTime = System.currentTimeMillis();
		long execTime = endTime - startTime;
		System.out.println("Execution time: " + execTime + " ms");
		
		// We do not need a shell session
		/* // create thread for shell
		Shell.createInstance(session, pluginRuntime);
		Shell sh = Shell.getInstance();
		Thread t = new Thread(sh);
		t.start();

		// wait on exit from shell (this thread never returns)
		try {
			t.join();
		} catch (InterruptedException ex) {
			// ignored
		} */
		
	}
	
	private static void generateClassifyingTerms(MModel model, String fileName) {
		// Obtain a list of the invariants in the model 
		Collection<MClassInvariant> col = model.classInvariants();
		
		// Generate classifying terms for each invariant
		Map<MClassInvariant, List<Expression>> classifyingTerms = new HashMap<MClassInvariant, List<Expression>>();
		for(MClassInvariant inv: col) {
			// Generate classifying terms for this invariant
			Expression exp = inv.bodyExpression();
			List<Expression> ct = computeClassifyingTerms(exp);
			classifyingTerms.put(inv, ct);
		}
		
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cannot create output file '" + fileName + "'");
		}
		// Do something with the classifying terms
		for(Map.Entry<MClassInvariant, List<Expression>> item: classifyingTerms.entrySet()) {
			out.println("Invariant " + item.getKey().qualifiedName());
			out.println();
			out.println(" --Original body: ");
			out.println(" --" + item.getKey().bodyExpression().toString());
			out.println();
			for(Expression exp: item.getValue()) {
				out.println(exp.toString());
				// out.println(OptimizationVisitor.optimize(exp).toString());
			}
			out.println();
		}
		out.close();
	}
	
	private static List<Expression> computeClassifyingTerms(Expression exp) {
		StrengthenVisitor visitor = new StrengthenVisitor();
		exp.processWithVisitor(visitor);
		return visitor.getMutatedExpr();
	}
	
}

/**
 * A theme with full control over fonts and customized tree display.
 */
class MyTheme extends DefaultMetalTheme {
	private FontUIResource controlFont;

	private FontUIResource systemFont;

	private FontUIResource userFont;

	private FontUIResource smallFont;

	MyTheme() {
		// System.out.println("font: " + Font.getFont("use.gui.controlFont"));
		controlFont = new FontUIResource(Font.getFont("use.gui.controlFont",
				super.getControlTextFont()));
		systemFont = new FontUIResource(Font.getFont("use.gui.systemFont",
				super.getSystemTextFont()));
		userFont = new FontUIResource(Font.getFont("use.gui.userFont", super
				.getUserTextFont()));
		smallFont = new FontUIResource(Font.getFont("use.gui.smallFont", super
				.getSubTextFont()));
	}

	public String getName() {
		return "USE";
	}

	public FontUIResource getControlTextFont() {
		return controlFont;
	}

	public FontUIResource getSystemTextFont() {
		return systemFont;
	}

	public FontUIResource getUserTextFont() {
		return userFont;
	}

	public FontUIResource getMenuTextFont() {
		return controlFont;
	}

	public FontUIResource getWindowTitleFont() {
		return controlFont;
	}

	public FontUIResource getSubTextFont() {
		return smallFont;
	}

	public void addCustomEntriesToTable(UIDefaults table) {
		initIcon(table, "Tree.expandedIcon", "TreeExpanded.gif");
		initIcon(table, "Tree.collapsedIcon", "TreeCollapsed.gif");
		initIcon(table, "Tree.leafIcon", "TreeLeaf.gif");
		initIcon(table, "Tree.openIcon", "TreeOpen.gif");
		initIcon(table, "Tree.closedIcon", "TreeClosed.gif");
		table.put("Desktop.background", table.get("Menu.background"));
	}

	private void initIcon(UIDefaults table, String property, String iconFilename) {
		table.put(property, new ImageIcon(Options.getIconPath(iconFilename).toString()));
	}

}
	
	

