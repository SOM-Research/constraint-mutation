package ctgen;

import java.util.List;
import java.util.LinkedList;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpAny;
import org.tzi.use.uml.ocl.expr.ExpAsType;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpBagLiteral;
import org.tzi.use.uml.ocl.expr.ExpClosure;
import org.tzi.use.uml.ocl.expr.ExpCollect;
import org.tzi.use.uml.ocl.expr.ExpCollectNested;
import org.tzi.use.uml.ocl.expr.ExpConstBoolean;
import org.tzi.use.uml.ocl.expr.ExpConstEnum;
import org.tzi.use.uml.ocl.expr.ExpConstInteger;
import org.tzi.use.uml.ocl.expr.ExpConstReal;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.ExpConstUnlimitedNatural;
import org.tzi.use.uml.ocl.expr.ExpEmptyCollection;
import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpIf;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpIsKindOf;
import org.tzi.use.uml.ocl.expr.ExpIsTypeOf;
import org.tzi.use.uml.ocl.expr.ExpIsUnique;
import org.tzi.use.uml.ocl.expr.ExpIterate;
import org.tzi.use.uml.ocl.expr.ExpLet;
import org.tzi.use.uml.ocl.expr.ExpNavigation;
import org.tzi.use.uml.ocl.expr.ExpNavigationClassifierSource;
import org.tzi.use.uml.ocl.expr.ExpObjAsSet;
import org.tzi.use.uml.ocl.expr.ExpObjOp;
import org.tzi.use.uml.ocl.expr.ExpObjRef;
import org.tzi.use.uml.ocl.expr.ExpObjectByUseId;
import org.tzi.use.uml.ocl.expr.ExpOclInState;
import org.tzi.use.uml.ocl.expr.ExpOne;
import org.tzi.use.uml.ocl.expr.ExpOrderedSetLiteral;
import org.tzi.use.uml.ocl.expr.ExpQuery;
import org.tzi.use.uml.ocl.expr.ExpRange;
import org.tzi.use.uml.ocl.expr.ExpReject;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.ExpSelectByKind;
import org.tzi.use.uml.ocl.expr.ExpSelectByType;
import org.tzi.use.uml.ocl.expr.ExpSequenceLiteral;
import org.tzi.use.uml.ocl.expr.ExpSetLiteral;
import org.tzi.use.uml.ocl.expr.ExpSortedBy;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.ExpTupleLiteral;
import org.tzi.use.uml.ocl.expr.ExpTupleSelectOp;
import org.tzi.use.uml.ocl.expr.ExpUndefined;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.ExpressionVisitor;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;

public class StrengthenVisitor extends BooleanVisitor {

	private List<Expression> mutatedExpr;
	
	public StrengthenVisitor() {
		mutatedExpr = new LinkedList<Expression>();
	}
	
	public List<Expression> getMutatedExpr() {
		return mutatedExpr;
	}
	
	private static List<Expression> strengthen(Expression exp) {
		StrengthenVisitor vis = new StrengthenVisitor();
		exp.processWithVisitor(vis);
		return vis.getMutatedExpr();
	}
	
	private void defaultStrengthening() {
		// The default strengthening of an expression is setting it to "false"
		Expression mutant = new ExpConstBoolean(false);
		mutatedExpr.add(mutant);
	}
	
	// These expression have already been considered in BooleanVisitor
	// public void visitAllInstances(ExpAllInstances exp) 
	// public void visitBagLiteral(ExpBagLiteral exp) 
	// public void visitCollect(ExpCollect exp) 
	// public void visitCollect(ExpCollect exp) 
	// public void visitCollectNested(ExpCollectNested exp) 
	// public void visitConstEnum(ExpConstEnum exp) 
	// public void visitConstInteger(ExpConstInteger exp) 
	// public void visitConstReal(ExpConstReal exp) 
	// public void visitConstString(ExpConstString exp) 
	// public void visitEmptyCollection(ExpEmptyCollection exp) 
	// public void visitObjAsSet(ExpObjAsSet exp) 
	// public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp)
	// public void visitConstUnlimitedNatural(ExpConstUnlimitedNatural exp) 
	// public void visitIterate(ExpIterate exp) 
	// public void visitReject(ExpReject exp) 
	// public void visitSelect(ExpSelect exp) 
	// public void visitNavigation(ExpNavigation exp) 	
	// public void visitSequenceLiteral(ExpSequenceLiteral exp) 
	// public void visitSetLiteral(ExpSetLiteral exp)
	// public void visitTupleLiteral(ExpTupleLiteral exp) 
	// public void visitVarDeclList(VarDeclList varDeclList) 
	// public void visitVarDecl(VarDecl varDecl)
	// public void visitRange(ExpRange exp) 
	// public void visitUndefined(ExpUndefined exp)

	
	@Override
	public void visitAny(ExpAny exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}	
	}

	@Override
	public void visitAsType(ExpAsType exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}	
	}

	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}	
	}

	@Override
	public void visitConstBoolean(ExpConstBoolean exp) {
		defaultStrengthening();
	}

	@Override
	public void visitExists(ExpExists exp) {
		Expression query = exp.getQueryExpression();
		Expression range = exp.getRangeExpression();
		VarDeclList decl = exp.getVariableDeclarations();
		
		// Generate mutants for the query
		List<Expression> queryMutants = strengthen(query);
		
		// Generate the kernel of the range
		// TODO: Remove comment when KernelVisitor is implemented
		// List<Expression> rangeKernel = KernelVisitor.kernel(query);
		List<Expression> rangeKernel = new LinkedList<Expression>(); 
						
		// Mutation 1: Strengthen the property within the "exists"
		// Generate a new mutant for each mutant of the query expression
		for (Expression mutant: queryMutants) {
			try {
				Expression newMutant = new ExpExists(decl,range,mutant);
				mutatedExpr.add(newMutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: Compute the kernel of the collection
		for(Expression kernel: rangeKernel) {
			try {
				Expression newMutant = new ExpExists(decl,kernel,query);
				mutatedExpr.add(newMutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 3
		// Do both - strengthen the property within the "exists"
		// And compute the kernel of the collection
		for(Expression mutant: queryMutants) {
			for(Expression kernel: rangeKernel) {
				try {
					Expression newMutant = new ExpExists(decl,kernel,mutant);
					mutatedExpr.add(newMutant);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Mutation 4: Replace "c->exists()" with a "c->forAll() and size() >= 1"
		try {	
			Expression aux1    = new ExpForAll(decl, range, query);	
			Expression args1[] = {range};
			Expression aux2    = ExpStdOp.create("size", args1);
			Expression args2[] = {aux2, new ExpConstInteger(1) }; 
			Expression aux3    = ExpStdOp.create(">=", args2);
			Expression args3[] = {aux1, aux3};
			Expression mutant  = ExpStdOp.create("and", args3);		
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}

		// Mutation 5: as (4), but also strengthen the query
		// TODO
		
		// Mutation 6: as (4), but also compute the kernel of the range
		// TODO
		
		// Mutation 7: as (6), but also strengthen the query
		// TODO
		
		// Mutation 8: replace by "false"
		defaultStrengthening();	
	}

	@Override
	public void visitForAll(ExpForAll exp) {
		Expression query = exp.getQueryExpression();
		Expression range = exp.getRangeExpression();
		VarDeclList decl = exp.getVariableDeclarations();		
		
		// Generate mutants for the query
		List<Expression> queryMutants = strengthen(query);

		// Generate the hull of the range
		// TODO: Remove comment when KernelVisitor is implemented
		// List<Expression> rangeHull = HullVisitor.hull(query);
		List<Expression> rangeHull = new LinkedList<Expression>();
		
		// Mutation 1: strengthen the condition within the "forAll"
		// Generate a new mutant for each mutant of the query expression
		for(Expression mutant: queryMutants) {
			try {
				Expression newMutant = new ExpForAll(decl,range,mutant);
				mutatedExpr.add(newMutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
	
		// Mutation 2: compute the hull of the collection
		for(Expression hull: rangeHull) {
			try {
				Expression newMutant = new ExpForAll(decl,hull,query);
				mutatedExpr.add(newMutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
		
		// Mutation 3: do doth
		for(Expression mutant: queryMutants) {
			for(Expression hull: rangeHull) {
				try {
					Expression newMutant = new ExpForAll(decl,hull,mutant);
					mutatedExpr.add(newMutant);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				}	
			}
		}
		
		// Mutation 4: replace by "false"
		defaultStrengthening();
	}

	@Override
	public void visitIf(ExpIf exp) {
		Expression cond = exp.getCondition();
		Expression thenExp = exp.getThenExpression();
		Expression elseExp = exp.getElseExpression();

		// Mutate each of the alternatives of the conditional
		List<Expression> thenMutants = strengthen(thenExp);
		List<Expression> elseMutants = strengthen(elseExp);
		
		// Construct a new expression for each combination of mutants
		// Three potential scenarios
		// (1) we keep the original "then" and mutate "else"		
		for(Expression elseMutant: elseMutants) {
			Expression newMutant;
			try {
				newMutant = new ExpIf(cond, thenExp, elseMutant);
				mutatedExpr.add(newMutant);
			}  catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		// (2) we keep the original  "else" and mutate then
		for(Expression thenMutant: thenMutants) {
			Expression newMutant;
			try {
				newMutant = new ExpIf(cond, thenMutant, elseExp);
				mutatedExpr.add(newMutant);
			}  catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		// (3) we mutate both branches of the conditional
		for(Expression thenMutant: thenMutants) {
			for(Expression elseMutant: elseMutants) {
				Expression newMutant;
				try {
					newMutant = new ExpIf(cond, thenMutant, elseMutant);
					mutatedExpr.add(newMutant);
				}  catch (ExpInvalidException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void visitIsKindOf(ExpIsKindOf exp) {
		defaultStrengthening();
	}

	@Override
	public void visitIsTypeOf(ExpIsTypeOf exp) {
		defaultStrengthening();
	}

	@Override
	public void visitIsUnique(ExpIsUnique exp) {
		Expression query = exp.getQueryExpression();
		Expression range = exp.getRangeExpression();
		VarDeclList decl = exp.getVariableDeclarations();
		
		// Generate the hull of the range
		// TODO: Remove comment when KernelVisitor is implemented
		// List<Expression> rangeHull = HullVisitor.hull(query);
		List<Expression> rangeHull = new LinkedList<Expression>();

		// Mutation 1: compute the hull of the collection
		for(Expression hull: rangeHull) {
			try {
				Expression mutant = new ExpIsUnique(decl.varDecl(0), hull, query);
				mutatedExpr.add(mutant);
			}  catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
		
		// Mutation 2: replace by "range->size() <= 1"
		try {
			Expression args1[] = {range};
			Expression aux1    = ExpStdOp.create("size",  args1);
			Expression args2[] = {aux1, new ExpConstInteger(1)};
			Expression mutant  = ExpStdOp.create("<=", args2);
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}	
		
		// Mutation 3: as (2), but also compute the hull of the collection
		// TODO - Don't think it makes much sense
		
		// Mutation 4: replace by "false"
		defaultStrengthening();
	}

	@Override
	public void visitLet(ExpLet exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitObjOp(ExpObjOp exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitObjRef(ExpObjRef exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitOne(ExpOne exp) {
		// TODO what to do here?
	}

	
	@Override
	public void visitQuery(ExpQuery exp) {
		wrongTypeError("visit query - this node should not be reached");
	}

	@Override
	public void visitWithValue(ExpressionWithValue exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitSortedBy(ExpSortedBy exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	private void mutateOrExp(ExpStdOp exp) {
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "or" is a binary expression
		assert(args.length == 2);
		Expression left  = args[0];
		Expression right = args[1];
		
		List<Expression> leftMutants  = strengthen(left);
		List<Expression> rightMutants = strengthen(right); 
		
		// Mutation 1: Strengthen the left subexpression
		for(Expression mutant: leftMutants) {
			Expression newArgs[] = {mutant, right};
			try {
				Expression mutant1 = ExpStdOp.create("or", newArgs);
				mutatedExpr.add(mutant1);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
		 
		// Mutation 2: Strengthen the right subexpression
		for(Expression mutant: rightMutants) {
			Expression newArgs[] = {left, mutant};
			try {
				Expression mutant2 = ExpStdOp.create("or", newArgs);
				mutatedExpr.add(mutant2);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
			
		// Mutation 3: Strengthen both subexpressions
		for(Expression mutantA: leftMutants) {
			for (Expression mutantB: rightMutants) {
				Expression newArgs[] = {mutantA, mutantB};
				try {
					Expression mutant3 = ExpStdOp.create("or", newArgs);
					mutatedExpr.add(mutant3);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				}
			}
		}
			
		// Mutation 4: Replace the "or" with an "and"
		try {
			Expression mutant4 = ExpStdOp.create("and", args);
			mutatedExpr.add(mutant4);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 5: Strengthen the left subexpression and replace with "and"
		for(Expression mutant: leftMutants) {
			Expression newArgs[] = {mutant, right};
			try {
				Expression mutant5 = ExpStdOp.create("and", newArgs);
				mutatedExpr.add(mutant5);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
				
		// Mutation 6: Strengthen the right subexpression and replace with "and"
		for(Expression mutant: rightMutants) {
			Expression newArgs[] = {left, mutant};
			try {
				Expression mutant6 = ExpStdOp.create("and", newArgs);
				mutatedExpr.add(mutant6);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
					
		// Mutation 7: Strengthen both subexpressions and replace with "and"
		for(Expression mutantA: leftMutants) {
			for (Expression mutantB: rightMutants) {
				Expression newArgs[] = {mutantA, mutantB};
				try {
					Expression mutant3 = ExpStdOp.create("or", newArgs);
					mutatedExpr.add(mutant3);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				}
			}
		}
			
		// Mutation 8: Replace the "or" with an "xor"
		// We should not strengthen the arguments!
		try {
			Expression mutant8 = ExpStdOp.create("xor", args);
			mutatedExpr.add(mutant8);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
				
		// Mutation 9: Replace the "or" expression by "false" 
		defaultStrengthening();
	}
	
	private void mutateAndExp(ExpStdOp exp) {
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "and" is a binary expression
		assert(args.length == 2);
		Expression left  = args[0];
		Expression right = args[1];
		
		List<Expression> leftMutants  = strengthen(left);
		List<Expression> rightMutants = strengthen(right);
		
		// Mutation 1: Strengthen the left subexpression
		for(Expression mutant: leftMutants) {
			Expression newArgs[] = {mutant, right};
			try {
				Expression mutant1 = ExpStdOp.create("and", newArgs);
				mutatedExpr.add(mutant1);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
				 
		// Mutation 2: Strengthen the right subexpression
		for(Expression mutant: rightMutants) {
			Expression newArgs[] = {left, mutant};
			try {
				Expression mutant2 = ExpStdOp.create("and", newArgs);
				mutatedExpr.add(mutant2);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}	
		}
					
		// Mutation 3: Strengthen both subexpressions
		for(Expression mutantA: leftMutants) {
			for (Expression mutantB: rightMutants) {
				Expression newArgs[] = {mutantA, mutantB};
				try {
					Expression mutant3 = ExpStdOp.create("and", newArgs);
					mutatedExpr.add(mutant3);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void mutateXorExp(ExpStdOp exp) {
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "xor" is a binary expression
		assert(args.length == 2);
		Expression left  = args[0];
		Expression right = args[1];
		
		// No need to mutate subexpressions - it would not be a strengthening
		// Mutation 1: replace the expression with (left & ( not right))
		try {
			Expression args1[] = {right};
			Expression aux  = ExpStdOp.create("not", args1);
			Expression args2[] = {left, aux};
			Expression mutant1 = ExpStdOp.create("and", args2); 
			mutatedExpr.add(mutant1);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 2: replace the expression with ((not left) & (right))
		try {
			Expression args1[] = {left};
			Expression aux  = ExpStdOp.create("not", args1);
			Expression args2[] = {aux, right};
			Expression mutant2 = ExpStdOp.create("and", args2); 
			mutatedExpr.add(mutant2);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
	}
	
	private void mutateImpliesExp(ExpStdOp exp) {
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "implies" is a binary expression
		assert(args.length == 2);
		Expression left   = args[0];
		Expression right  = args[1];
		
		// Dirty hack - Rewrite a->b as (!a)||b
		// Then, strengthen that expression as a disjunction
		
		try {
			Expression args1[] = {left};
			ExpStdOp aux1 = ExpStdOp.create("not", args1);
			Expression args2[] = {aux1, right};
			ExpStdOp aux2 = ExpStdOp.create("or", args2);
			mutateOrExp(aux2);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}		
	}
	
	private void mutateNotExp(ExpStdOp exp) {
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "xor" is a binary expression
		assert(args.length == 1);
		Expression subexp  = args[0];
		
		// TODO: Uncomment this line when weakening is implemented
		// List<Expression> mutants  = WeakenVisitor.weaken(subexp);
		List<Expression> mutants = new LinkedList<Expression>();
		
		// Mutation 1: weaken the subexpression
		for(Expression mutantSubExp: mutants) {
			Expression newArgs[] = {mutantSubExp};
			try {
				Expression mutant = ExpStdOp.create("not", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: replace by "false"
		defaultStrengthening();
	}
		
	
	private void mutateIsEmptyExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "isEmpty()" is a unary operation
		assert(args.length == 1);
		Expression col  = args[0];
		
		// TODO: Uncomment his when hull is implemented
		// List<Expression> mutants = HullVisitor.hull(col);
		List<Expression> mutants = new LinkedList<Expression>();
		
		// Mutation 1: Compute the hull of the collection
		// Compute the "isEmpty()" operation on the extended collection
		for(Expression mutantCol: mutants) {
			Expression newArgs[] = {mutantCol};
			try {
				Expression mutant = ExpStdOp.create("isEmpty", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: replace by "false"
		defaultStrengthening();
	}

	private void mutateNotEmptyExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "isEmpty()" is a unary operation
		assert(args.length == 1);
		Expression col  = args[0];
		
		// TODO: Uncomment this when the kernel is implemented
		// List<Expression> mutants = KernelVisitor.kernel(col);
		List<Expression> mutants = new LinkedList<Expression>();
		
		// Mutation 1: Compute the kernel of the collection
		// Compute the "notEmpty()" operation on the reduced collection
		for(Expression mutantCol: mutants) {
			Expression newArgs[] = {mutantCol};
			try {
				Expression mutant = ExpStdOp.create("notEmpty", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: replace by "false"
		defaultStrengthening();
	}
	
	private void mutateIncludesExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "includes()" is a binary operation
		assert(args.length == 2);
		Expression col  = args[0];
		Expression elem = args[1];
		
		// TODO: Uncomment this when the kernel is implemented
		// List<Expression> mutants = KernelVisitor.kernel(col);
		List<Expression> mutants = new LinkedList<Expression>();
		
		// Mutation 1: Compute the kernel of the collection
		// Compute the "includes()" operation on the reduced collection
		for(Expression mutantCol: mutants) {
			Expression newArgs[] = {mutantCol, elem};
			try {
				Expression mutant = ExpStdOp.create("includes", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: replace by "false"
		defaultStrengthening();
	}
	
	private void mutateExcludesExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "excludes()" is a binary operation
		assert(args.length == 2);
		Expression col  = args[0];
		Expression elem = args[1];
		
		// TODO: Uncomment this when the hull is implemented
		// List<Expression> mutants = HullVisitor.hull(col);
		List<Expression> mutants = new LinkedList<Expression>();
		
		// Mutation 1: Compute the kernel of the collection
		// Compute the "includes()" operation on the reduced collection
		for(Expression mutantCol: mutants) {
			Expression newArgs[] = {mutantCol, elem};
			try {
				Expression mutant = ExpStdOp.create("excludes", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: replace by "false"
		defaultStrengthening();
	}	

	private void mutateIncludesAllExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "includesAll()" is a binary operation
		assert(args.length == 2);
		Expression baseCol  = args[0];
		Expression otherCol = args[1];
		
		// TODO: Uncomment this when the kernel/hull is implemented
		// List<Expression> baseMutants  = KernelVisitor.kernel(baseCol);
		// List<Expression> otherMutants = HullVisitor.hull(baseCol);
		List<Expression> baseMutants = new LinkedList<Expression>();
		List<Expression> otherMutants = new LinkedList<Expression>();
		
		// Mutation 1: Compute the kernel of the base collection
		for(Expression mutantCol: baseMutants) {
			Expression newArgs[] = {mutantCol, otherCol};
			try {
				Expression mutant = ExpStdOp.create("includesAll", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: Compute the kernel of the other collection
		for(Expression mutantCol: otherMutants) {
			Expression newArgs[] = {baseCol, mutantCol};
			try {
				Expression mutant = ExpStdOp.create("includesAll", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		
		// Mutation 3: Compute the kernel of the base collection and the hull of the other collection
		for(Expression baseMutant: baseMutants) {
			for(Expression otherMutant: otherMutants) {
				Expression newArgs[] = {baseMutant, otherMutant};
				try {
					Expression mutant = ExpStdOp.create("includesAll", newArgs);
					mutatedExpr.add(mutant);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				
				}
			}
		}
		
		// Mutation 4: replace by "false"
		defaultStrengthening();
	}

	private void mutateExcludesAllExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "includesAll()" is a binary operation
		assert(args.length == 2);
		Expression baseCol  = args[0];
		Expression otherCol = args[1];
		
		// TODO: Uncomment this when the kernel/hull is implemented
		// List<Expression> baseMutants  = HullVisitor.hull(baseCol);
		// List<Expression> otherMutants = HullVisitor.hull(baseCol);
		List<Expression> baseMutants = new LinkedList<Expression>();
		List<Expression> otherMutants = new LinkedList<Expression>();
		
		// Mutation 1: Compute the hull of the base collection
		for(Expression mutantCol: baseMutants) {
			Expression newArgs[] = {mutantCol, otherCol};
			try {
				Expression mutant = ExpStdOp.create("excludesAll", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		// Mutation 2: Compute the hull of the other collection
		for(Expression mutantCol: otherMutants) {
			Expression newArgs[] = {baseCol, mutantCol};
			try {
				Expression mutant = ExpStdOp.create("excludesAll", newArgs);
				mutatedExpr.add(mutant);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}
		}
		
		
		// Mutation 3: Compute the hull of the base collection and the hull of the other collection
		for(Expression baseMutant: baseMutants) {
			for(Expression otherMutant: otherMutants) {
				Expression newArgs[] = {baseMutant, otherMutant};
				try {
					Expression mutant = ExpStdOp.create("excludesAll", newArgs);
					mutatedExpr.add(mutant);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				
				}
			}
		}
		
		// Mutation 4: replace by "false"
		defaultStrengthening();
	}	

	private void mutateLessEqualExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "<=" is a binary operation
		assert(args.length == 2);
		//Expression exp1 = args[0];
		//Expression exp2 = args[1];
		
		// Mutation 1: replace "<=" by "<" 
		try {
			Expression mutant = ExpStdOp.create("<", args);
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 1: replace "<=" by "="
		try {
			Expression mutant = ExpStdOp.create("=", args);
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 3: replace by "false"
		defaultStrengthening();
	}
	
	private void mutateGreaterEqualExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "<=" is a binary operation
		assert(args.length == 2);
		//Expression exp1 = args[0];
		//Expression exp2 = args[1];
		
		// Mutation 1: replace ">=" by ">" 
		try {
			Expression mutant = ExpStdOp.create(">", args);
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 1: replace ">=" by "="
		try {
			Expression mutant = ExpStdOp.create("=", args);
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 3: replace by "false"
		defaultStrengthening();
	}

	private void mutateLessExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "<" is a binary operation
		assert(args.length == 2);
		Expression exp1 = args[0];
		Expression exp2 = args[1];
		
		// Mutation 1: add 1 to exp1 
		try {
			Expression newArgs1[] = {exp1, new ExpConstInteger(1)};
			Expression aux = ExpStdOp.create("+", newArgs1);
			Expression newArgs2[] = {aux, exp2};
			Expression mutant = ExpStdOp.create("<", newArgs2);
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 2: replace by "false"
		defaultStrengthening();
	}

	private void mutateGreaterExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "<" is a binary operation
		assert(args.length == 2);
		Expression exp1 = args[0];
		Expression exp2 = args[1];
		
		// Mutation 1: add 1 to exp2 
		try {
			Expression newArgs1[] = {exp2, new ExpConstInteger(1)};
			Expression aux = ExpStdOp.create("+", newArgs1);
			Expression newArgs2[] = {exp1, aux};
			Expression mutant = ExpStdOp.create(">", newArgs2);
			mutatedExpr.add(mutant);
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
		
		// Mutation 2: replace by "false"
		defaultStrengthening();
	}
	
	private void mutateNotEqualsExp(ExpStdOp exp) {
		Expression[] args = exp.args();

		// Retrieve subexpressions
		// Sanity check: "<>" is a binary operation
		assert(args.length == 2);
		Expression exp1 = args[0];
		Expression exp2 = args[1];
		
		// If this is a numeric comparison, we can mutate it 
		// by replacing it with > or <
		// Otherwise we can only mutate it to false
		if (exp1.type().isKindOfNumber(Type.VoidHandling.EXCLUDE_VOID) && 
			exp2.type().isKindOfNumber(Type.VoidHandling.EXCLUDE_VOID)) {
			try {
				// Mutation 1: replace "<>" by "<"
				Expression mutant1 = ExpStdOp.create("<", args);
				mutatedExpr.add(mutant1);
				// Mutation 2: replace "<>" by ">"
				Expression mutant2 = ExpStdOp.create(">", args);
				mutatedExpr.add(mutant2);
			} catch (ExpInvalidException e) {
				e.printStackTrace();
			}		
		} 
		
		// We can always mutate the expression to "false"
		defaultStrengthening();
	}
	
	private void mutateUndefinedExp(ExpStdOp exp) {
		defaultStrengthening();
	}
		
	@Override
	public void visitStdOp(ExpStdOp exp) {
		// Place-holder for operations returning a boolean value
		// Boolean: or, xor, and, not, implies
		// Collection operations: isEmpty, notEmpty, includes, excludes, includesAll, excludesAll
		// Relational: <=, >=, <, >, =, <>
		String opName = exp.opname();
		switch(opName) {
		case "or":
			mutateOrExp(exp);
			break;	
		case "xor":
			mutateXorExp(exp); 
			break;
		case "and":
			mutateAndExp(exp);
			break;
		case "not":
			mutateNotExp(exp);
			break;	
		case "implies":
			mutateImpliesExp(exp);
			break;	
		case "=":
			defaultStrengthening();
			break;	
		case "<=":
			mutateLessEqualExp(exp); 
			break;	
		case ">=":
			mutateGreaterEqualExp(exp);
			break;	
		case "<":
			mutateLessExp(exp);
			break;	
		case ">":
			mutateGreaterExp(exp);
			break;	
		case "<>":
			mutateNotEqualsExp(exp); 
			break;	
		case "isEmpty":
			mutateIsEmptyExp(exp);
			break;	
		case "notEmpty":
			mutateNotEmptyExp(exp);
			break;	
		case "includes":
			mutateIncludesExp(exp);
			break;	
		case "excludes":
			mutateExcludesExp(exp);
			break;	
		case "includesAll":
			mutateIncludesAllExp(exp);
			break;	
		case "excludesAll":
			mutateExcludesAllExp(exp);
			break;	
		case "isUndefined":
			mutateUndefinedExp(exp);
			break;
		default:
			wrongTypeError("unsupported operation type '" + opName + "'");
		}
	}

	@Override
	public void visitTupleSelectOp(ExpTupleSelectOp exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitVariable(ExpVariable exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitClosure(ExpClosure exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}
	
	@Override
	public void visitOclInState(ExpOclInState exp) {
		defaultStrengthening();
	}
	
	@Override
	public void visitObjectByUseId(ExpObjectByUseId exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}
	
	@Override
	public void visitSelectByKind(ExpSelectByKind exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitExpSelectByType(ExpSelectByType exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

	@Override
	public void visitNavigationClassifierSource(ExpNavigationClassifierSource exp) {
		if (exp.type().isTypeOfBoolean()) {		
			defaultStrengthening();
		} else {
			wrongTypeError("variable of type " + exp.type().toString() );
		}
	}

}
