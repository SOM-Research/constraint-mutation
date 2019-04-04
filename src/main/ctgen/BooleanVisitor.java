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


// Super class of StrengthenVisitor and WeakenVisitor
//
// Controls all expressions that should not be reached during the traversal
public abstract class BooleanVisitor implements ExpressionVisitor {
	
	public void wrongTypeError() throws RuntimeException {
		wrongTypeError("");	
	}
	
	public void wrongTypeError(String type) throws RuntimeException {
		throw new RuntimeException("Visitor reached node with incorrect operation: " + type);	
	}
	
	@Override
	public void visitAllInstances(ExpAllInstances exp) {
		wrongTypeError("allInstances()");
	}

	@Override
	public void visitAny(ExpAny exp) {
		// Potentially boolean expression
	}

	@Override
	public void visitAsType(ExpAsType exp) {
		// Potentially boolean expression
	}

	@Override
	public void visitAttrOp(ExpAttrOp exp) {
		// Potentially boolean expression		
	}

	@Override
	public void visitBagLiteral(ExpBagLiteral exp) {
		wrongTypeError("bag literal");
	}

	@Override
	public void visitCollect(ExpCollect exp) {
		wrongTypeError("collect");
	}

	@Override
	public void visitCollectNested(ExpCollectNested exp) {
		wrongTypeError("collect nested");
	}

	@Override
	public void visitConstBoolean(ExpConstBoolean exp) {
		// Boolean expression
	}

	@Override
	public void visitConstEnum(ExpConstEnum exp) {
		wrongTypeError("enum constant");
	}

	@Override
	public void visitConstInteger(ExpConstInteger exp) {
		wrongTypeError("integer constant");
	}

	@Override
	public void visitConstReal(ExpConstReal exp) {
		wrongTypeError("real constant");
	}

	@Override
	public void visitConstString(ExpConstString exp) {
		wrongTypeError("string constant");
	}

	@Override
	public void visitEmptyCollection(ExpEmptyCollection exp) {
		wrongTypeError("empty collection");
	}

	
	
	@Override
	public void visitExists(ExpExists exp) {
		// Boolean expression
	}
	
	@Override
	public void visitForAll(ExpForAll exp) {
		// Boolean expression
	}
	

	@Override
	public void visitIf(ExpIf exp) {
		// Potentially boolean expression
	}

	@Override
	public void visitIsKindOf(ExpIsKindOf exp) {
		// Boolean expression
	}

	@Override
	public void visitIsTypeOf(ExpIsTypeOf exp) {
		// Boolean expression
	}

	@Override
	public void visitIsUnique(ExpIsUnique exp) {
		// Boolean expression
	}

	@Override
	public void visitIterate(ExpIterate exp) {
		wrongTypeError("iterate");		
	}

	@Override
	public void visitLet(ExpLet exp) {
		// Potentially boolean expression
	}

	@Override
	public void visitNavigation(ExpNavigation exp) {
		wrongTypeError("navigation");
	}

	@Override
	public void visitObjAsSet(ExpObjAsSet exp) {
		wrongTypeError();
	}

	@Override
	public void visitObjOp(ExpObjOp exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitObjRef(ExpObjRef exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitOne(ExpOne exp) {
		// Boolean expression
	}

	@Override
	public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp) {
		wrongTypeError("ordered set literal");
	}

	@Override
	public void visitQuery(ExpQuery exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitReject(ExpReject exp) {
		wrongTypeError("reject");
	}

	@Override
	public void visitWithValue(ExpressionWithValue exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitSelect(ExpSelect exp) {
		wrongTypeError("select");
	}

	@Override
	public void visitSequenceLiteral(ExpSequenceLiteral exp) {
		wrongTypeError("sequence literal");
	}

	@Override
	public void visitSetLiteral(ExpSetLiteral exp) {
		wrongTypeError("set literal");
	}

	@Override
	public void visitSortedBy(ExpSortedBy exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitStdOp(ExpStdOp exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitTupleLiteral(ExpTupleLiteral exp) {
		wrongTypeError("tuple literal");
	}

	@Override
	public void visitTupleSelectOp(ExpTupleSelectOp exp) {
		// Potentially boolean expression
	}

	@Override
	public void visitUndefined(ExpUndefined exp) {
		wrongTypeError("undefined");
	}

	@Override
	public void visitVariable(ExpVariable exp) {
		// Potentially boolean expression
	}

	@Override
	public void visitClosure(ExpClosure exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitOclInState(ExpOclInState exp) {
		// Boolean expression
	}

	@Override
	public void visitVarDeclList(VarDeclList varDeclList) {
		wrongTypeError("variable declaration list");
	}

	@Override
	public void visitVarDecl(VarDecl varDecl) {
		wrongTypeError("variable declaration");
	}

	@Override
	public void visitObjectByUseId(ExpObjectByUseId exp) {
		// Potentially boolean expression ?
	}

	@Override
	public void visitConstUnlimitedNatural(ExpConstUnlimitedNatural exp) {
		wrongTypeError("unlimited natural constant");
	}

	@Override
	public void visitSelectByKind(ExpSelectByKind exp) {
		wrongTypeError("select by kind");
	}

	@Override
	public void visitExpSelectByType(ExpSelectByType exp) {
		wrongTypeError("select by type");
	}

	@Override
	public void visitRange(ExpRange exp) {
		wrongTypeError("range");
	}

	@Override
	public void visitNavigationClassifierSource(ExpNavigationClassifierSource exp) {
		// Potentially boolean expression ?
	}

}
