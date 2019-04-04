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

public class OptimizationVisitor extends BooleanVisitor {

	private Expression optExp;
	
	public OptimizationVisitor() {
		optExp = null; 
	}
	
	public Expression getOptimizedExpr() {
		return optExp;
	}
	
	public static Expression optimize(Expression exp) {
		OptimizationVisitor vis = new OptimizationVisitor();
		exp.processWithVisitor(vis);
		return vis.getOptimizedExpr();
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
		// No optimization so far
		optExp = exp;
	}

	@Override
	public void visitAsType(ExpAsType exp) {
		// No optimization so far
		optExp = exp;
	}

	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		// No optimization so far
		optExp = exp;
	}

	@Override
	public void visitConstBoolean(ExpConstBoolean exp) {
		// No optimization so far
		optExp = exp;
	}

	@Override
	public void visitExists(ExpExists exp) {
		Expression query = exp.getQueryExpression();
		Expression range = exp.getRangeExpression();
		
		Expression queryOpt = optimize(query);
		
		// Optimization 1: query is a boolean constant 
		if (queryOpt instanceof ExpConstBoolean) {
			boolean queryValue = ((ExpConstBoolean)queryOpt).value();
			if (queryValue) {
				// If the query is true, it is the same as asking that the range is not empty
				try {
					Expression args[] = {range};
					optExp = ExpStdOp.create("notEmpty", args);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				}
				return;
			} else {
				// If the query is false, the answer is false
				optExp = new ExpConstBoolean(false);
				return;
			}
		}
		
		// Optimization 2: query is undefined
		
		// Optimization 3: query is invalid
		
		// Optimization 4: source collection is known to be empty
		
		// Optimization 5: source collection is undefined
		
		// Optimization 6: source collection is invalid
		
		// Otherwise - no optimization is possible
		optExp = exp;
	}

	@Override
	public void visitForAll(ExpForAll exp) {
		Expression query = exp.getQueryExpression();
		Expression range = exp.getRangeExpression();
		VarDeclList decl = exp.getVariableDeclarations();		
		
		
Expression queryOpt = optimize(query);
		
		// Optimization 1: query is a boolean constant 
		if (queryOpt instanceof ExpConstBoolean) {
			boolean queryValue = ((ExpConstBoolean)queryOpt).value();
			if (queryValue) {
				// If the query is false, it is the same as asking that the range is empty
				try {
					Expression args[] = {range};
					optExp = ExpStdOp.create("isEmpty", args);
				} catch (ExpInvalidException e) {
					e.printStackTrace();
				}
				return;
			} else {
				// If the query is false, the answer is true
				optExp = new ExpConstBoolean(true);
				return;
			}
		}
		
		// Optimization 2: query is undefined
		
		// Optimization 3: query is invalid
		
		// Optimization 4: source collection is known to be empty
		
		// Optimization 5: source collection is undefined
		
		// Optimization 6: source collection is invalid
		
		// Otherwise - no optimization is possible
		optExp = exp;
	}

	@Override
	public void visitIf(ExpIf exp) {
		optExp = exp;
		/* Expression cond = exp.getCondition();
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
		} */
	}

	@Override
	public void visitIsKindOf(ExpIsKindOf exp) {
		optExp = exp;
	}

	@Override
	public void visitIsTypeOf(ExpIsTypeOf exp) {
		optExp = exp;
	}

	@Override
	public void visitIsUnique(ExpIsUnique exp) {
		optExp = exp;
		/* Expression query = exp.getQueryExpression();
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
		defaultStrengthening();*/
	}

	@Override
	public void visitLet(ExpLet exp) {
		optExp = exp;
	}

	@Override
	public void visitObjOp(ExpObjOp exp) {
		optExp = exp;
	}

	@Override
	public void visitObjRef(ExpObjRef exp) {
		optExp = exp;
	}

	@Override
	public void visitOne(ExpOne exp) {
		optExp = exp;
	}

	
	@Override
	public void visitQuery(ExpQuery exp) {
		wrongTypeError("visit query - this node should not be reached");
	}

	@Override
	public void visitWithValue(ExpressionWithValue exp) {
		optExp = exp;
	}

	@Override
	public void visitSortedBy(ExpSortedBy exp) {
		optExp = exp;
	}

	private void optimizeOrExp(ExpStdOp exp) {
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "or" is a binary expression
		assert(args.length == 2);
		Expression left  = args[0];
		Expression right = args[1];
		
		Expression optLeft  = optimize(left);
		Expression optRight = optimize(right);
		
		boolean leftIsConstant  = optLeft instanceof ExpConstBoolean;
		boolean rightIsConstant = optRight instanceof ExpConstBoolean;
		// Optimization 1: left and right are boolean constants
		if (leftIsConstant && rightIsConstant) {
			boolean leftValue = ((ExpConstBoolean)optLeft).value();
			boolean rightValue = ((ExpConstBoolean)optRight).value();
			optExp = new ExpConstBoolean(leftValue || rightValue);
			return;
		}
		
		// Optimization 2: only one operator is a boolean constant
		if (leftIsConstant || rightIsConstant) {
			// Assume the constant is the leftmost subexpression
			// Otherwise swap them
			if (rightIsConstant) { 
				Expression aux = optLeft;
				optLeft = optRight;
				optRight = aux;
			}
			boolean leftValue = ((ExpConstBoolean)optLeft).value();
			if (leftValue) {
				// Result is always true
				optExp = optLeft;
				return;
			} else {
				// Result is always the rightmost operator
				optExp = optRight;
				return;
			}
		} 
		
		
		// Optimization 3: some value is undefined / invalid	
			
		// Otherwise: no boolean constants, no optimization possible
		optExp = exp;
	}
	
	private void optimizeAndExp(ExpStdOp exp) {
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "and" is a binary expression
		assert(args.length == 2);
		Expression left  = args[0];
		Expression right = args[1];
		
		Expression optLeft  = optimize(left);
		Expression optRight = optimize(right);
		
		boolean leftIsConstant  = optLeft instanceof ExpConstBoolean;
		boolean rightIsConstant = optRight instanceof ExpConstBoolean;
		// Optimization 1: left and right are boolean constants
		if (leftIsConstant && rightIsConstant) {
			boolean leftValue = ((ExpConstBoolean)optLeft).value();
			boolean rightValue = ((ExpConstBoolean)optRight).value();
			optExp = new ExpConstBoolean(leftValue && rightValue);
			return;
		}
		
		// Optimization 2: only one operator is a boolean constant
		if (leftIsConstant || rightIsConstant) {
			// Assume the constant is the leftmost subexpression
			// Otherwise swap them
			if (rightIsConstant) { 
				Expression aux = optLeft;
				optLeft = optRight;
				optRight = aux;
			}
			boolean leftValue = ((ExpConstBoolean)optLeft).value();
			if (leftValue) {
				// Result is always the rightmost operator
				optExp = optRight;
			} else {
				// Result is always false
				optExp = optLeft;
			}
		} 
		
		
		// Optimization 3: some value is undefined / invalid	
			
		// Otherwise: no boolean constants, no optimization possible
		optExp = exp;
	}
	
	private void optimizeXorExp(ExpStdOp exp) {
		optExp = exp;
		/*Expression[] args = exp.args();
		
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
		}*/
	}
	
	private void optimizeImpliesExp(ExpStdOp exp) {
		optExp = exp;
		/* Expression[] args = exp.args();
		
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
		} */
	}
	
	private void optimizeNotExp(ExpStdOp exp) {
		optExp = exp;
		Expression[] args = exp.args();
		
		// Retrieve subexpressions
		// Sanity check: "xor" is a binary expression
		assert(args.length == 1);
		Expression subexp  = args[0];
	
		Expression optSubExp = optimize(subexp);
		
		// Optimization 1: constant value
		if (optSubExp instanceof ExpConstBoolean) {
			boolean value = ((ExpConstBoolean)optSubExp).value();
			optExp = new ExpConstBoolean(!value);
		}
		
		// Optimization 2: undefined or invalid value
		
		// Otherwise, no optimization is possible
		Expression newArgs[] = {optSubExp};
		try{ 
			Expression opt = ExpStdOp.create("not", newArgs);
			optExp = opt;
		} catch (ExpInvalidException e) {
			e.printStackTrace();
		}
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
			optimizeOrExp(exp);
			break;	
		case "xor":
			optimizeXorExp(exp); 
			break;
		case "and":
			optimizeAndExp(exp);
			break;
		case "not":
			optimizeNotExp(exp);
			break;	
		case "implies":
			optimizeImpliesExp(exp);
			break;	
		case "=":
			optExp = exp;
			break;	
		case "<=":
			optExp = exp; 
			break;	
		case ">=":
			optExp = exp;
			break;	
		case "<":
			optExp = exp;
			break;	
		case ">":
			optExp = exp;
			break;	
		case "<>":
			optExp = exp; 
			break;	
		case "isEmpty":
			optExp = exp;
			break;	
		case "notEmpty":
			optExp = exp;
			break;	
		case "includes":
			optExp = exp;
			break;	
		case "excludes":
			optExp = exp;
			break;	
		case "includesAll":
			optExp = exp;
			break;	
		case "excludesAll":
			optExp = exp;
			break;	
		default:
			wrongTypeError("unsupported operation type '" + opName + "'");
		}
	}

	@Override
	public void visitTupleSelectOp(ExpTupleSelectOp exp) {
		// If the tuple is defined as a constant value, we can replace the selection with the value
		optExp = exp;
	}

	@Override
	public void visitVariable(ExpVariable exp) {
		optExp = exp;
	}

	@Override
	public void visitClosure(ExpClosure exp) {
		optExp = exp;
	}
	
	@Override
	public void visitOclInState(ExpOclInState exp) {
		optExp = exp;
	}
	
	@Override
	public void visitObjectByUseId(ExpObjectByUseId exp) {
		optExp = exp;
	}
	
	@Override
	public void visitSelectByKind(ExpSelectByKind exp) {
		optExp = exp;
	}

	@Override
	public void visitExpSelectByType(ExpSelectByType exp) {
		optExp = exp;
	}

	@Override
	public void visitNavigationClassifierSource(ExpNavigationClassifierSource exp) {
		optExp = exp;
	}

}
