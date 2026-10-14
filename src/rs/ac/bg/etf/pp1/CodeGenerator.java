package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;

	public int getMainPc() {
		return this.mainPc;
	}
	
	//METHOD DECLARATIONS
	@Override
	public void visit(MethodDeclType_t methodType) {
		methodType.obj.setAdr(Code.pc); //pocetna adresa metode
		Code.put(Code.enter); //prva instrukcija metode
		Code.put(methodType.obj.getLevel()); //br formalnih par.(b1) koje treba da skine 
		//sa Expr steka i da ih upise na Proc stek
		Code.put(methodType.obj.getLocalSymbols().size()); //ukupan br formalnih i lokalnih 
		
	}
	
	@Override
	public void visit(MethodDeclType_v methodVoid) {
		methodVoid.obj.setAdr(Code.pc); //pocetna adresa metode
		if(methodVoid.getI1().equalsIgnoreCase("main")) {
			this.mainPc = Code.pc;
		}
		Code.put(Code.enter); //sa steka skida formalne par.
		Code.put(methodVoid.obj.getLevel());
		Code.put(methodVoid.obj.getLocalSymbols().size());
		
	}
	
	@Override 
	public void visit(MethodDecl methodDecl) {
		 Obj meth = methodDecl.getMethodDeclType().obj;
		 if (meth.getType() == Tab.noType) {
			 Code.put(Code.exit); // cisti b2
			 Code.put(Code.return_);
	     }
		 else {
			 Code.put(Code.trap);
			 Code.put(1);
		 }
	}
	
	//STATEMENT DECLARATIONS
	@Override 
	public void visit(Statement_6 stmtREAD) {
		if(stmtREAD.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		}
		else {
			Code.put(Code.read);
		}
		//na Expr steku ucitana vrednost
		Code.store(stmtREAD.getDesignator().obj);
	}
	
	@Override
	public void visit(Statement_7 stmtPRINT) {
	    if (stmtPRINT.getNumConstPom() instanceof NumConstPom_p) {
	        Code.loadConst(((NumConstPom_p) stmtPRINT.getNumConstPom()).getN1());
	    } else {
	        Code.loadConst(0);
	    }

	    Struct exprType = stmtPRINT.getExpr() != null ? stmtPRINT.getExpr().struct : null;
	    if (exprType != null && exprType.equals(Tab.charType)) {
	        Code.put(Code.bprint);
	    } else {
	        Code.put(Code.print);
	    }
	}
	
	@Override
	public void visit(Statement_5 stmtRETURN) {
		 Code.put(Code.exit);
		 Code.put(Code.return_);
	}
	
	
	
	//FACTOR
	@Override
	public void visit(FactorPom_num number) {
		Code.loadConst(number.getN1());
	}
	
	@Override
	public void visit(FactorPom_char character) {
		Code.loadConst(character.getC1());
	}
	
	@Override
	public void visit(FactorPom_bool bool) {
		Code.loadConst(bool.getB1());
	}
	
	@Override
	public void visit(FactorPom_0 designatorFactor) {
		if (designatorFactor.getDesignator() instanceof Designator_1) {
			Code.load(designatorFactor.getDesignator().obj);
		}
	}
	
	@Override
	public void visit(FactorPom_new newArrayFactor) {
		Code.put(Code.newarray);
		Struct elemType = newArrayFactor.getType() != null ? newArrayFactor.getType().struct : null;
		if (elemType != null && elemType.equals(Tab.charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	
	//a = zbir()
	@Override
	public void visit(FactorPom_meth methodFactor) {
	    Obj obj = methodFactor.getDesignator().obj;
	    String name = obj.getName();

	    if ("len".equals(name)) {
	        Code.put(Code.arraylength);
	    }
	    else if ("chr".equals(name) || "ord".equals(name)) {
	        // ne radi se nista, argument je vec izracunat i na steku
	    }
	    else {
	        int offset = obj.getAdr() - Code.pc;
	        Code.put(Code.call);
	        Code.put2(offset);
	    }
	}
	
	//DESIGNATOR — baza (ident) u lancu; pushuje adresu/vrednost na stek
	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
	    Obj obj = designatorArrayName.obj;
	    // Enum tip (Obj.Type) se ne ucitava ovde — Designator_3 ucitava konstantu (Con)
	    if (obj.getKind() == Obj.Type)
	        return;
	    Code.load(obj);
	}
	
	@Override
	public void visit(Designator_2 designatorElem) {
	    if (designatorElem.getIndexLength() instanceof IndexLength_2) {
	        Code.put(Code.arraylength);
	    }
	    else {
	        SyntaxNode parent = designatorElem.getParent();

	        if (!(parent instanceof DesignatorStatement_1) &&
	            !(parent instanceof DesignatorStatement_3) &&
	            !(parent instanceof DesignatorStatement_4) &&
	            !(parent instanceof Statement_6)) {
	            Code.load(designatorElem.obj);
	        }
	    }
	}

	@Override
	public void visit(Designator_3 designatorDotIdent) {
		Obj obj = designatorDotIdent.obj;
		//enum
		if (obj.getKind() == Obj.Con) {
			Code.load(obj);
		} 
		//niz.length
		else {
			Code.load(designatorDotIdent.getDesignatorArrayName().obj);
			Code.put(Code.arraylength);
		}
	}

	//dodela vrednosti
	@Override
	public void visit(DesignatorStatement_1 designatorStmtAssign) {
		Code.store(designatorStmtAssign.getDesignator().obj);
	}
	
	//skok na metodu
	@Override
	public void visit(DesignatorStatement_2 designatorStmtMethod) {
		 Obj obj = designatorStmtMethod.getDesignator().obj;
		 String name = obj.getName();
		 if ("len".equals(name)) {
			 Code.put(Code.arraylength);
		     Code.put(Code.pop);
		 }
		 else if ("chr".equals(name) || "ord".equals(name)) {
		     Code.put(Code.pop);
		 }
		 else {
			int offset = designatorStmtMethod.getDesignator().obj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
			
			if(designatorStmtMethod.getDesignator().obj.getType() != Tab.noType) {
				Code.put(Code.pop);
				//ako metoda nije void mora sve sto je 
				//stavila na stek da se skine kada se ona zavrsi
			}
		 }
		
	}
	
	//inkrementiranje
	@Override
	public void visit(DesignatorStatement_3 designatorStmtINC) {
		//zbog inc nad elementima niza (adresa i indeks niza)
		if(designatorStmtINC.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStmtINC.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStmtINC.getDesignator().obj);
	}
	
	//dekrementiranje
		@Override
		public void visit(DesignatorStatement_4 designatorStmtDEC) {
			if(designatorStmtDEC.getDesignator().obj.getKind() == Obj.Elem) {
				Code.put(Code.dup2);
			}
			Code.load(designatorStmtDEC.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(designatorStmtDEC.getDesignator().obj);
		}
	
	
	//EXPR
	@Override
	public void visit(ExprCF_1 addOpTermList) {
		if(addOpTermList.getAddop() instanceof Addop_1) {
			Code.put(Code.add);
		}
		else if(addOpTermList.getAddop() instanceof Addop_2) {
			Code.put(Code.sub);
		}
	}
	
	//za a = -5 npr
	@Override
	public void visit(ExprCF_3 minusTerm) {
		Code.put(Code.neg);
	}

	//TERM
	@Override
	public void visit(Term_1 mulOpTermList) {
		if(mulOpTermList.getMulop() instanceof Mulop_1) {
			Code.put(Code.mul);
		}
		else if(mulOpTermList.getMulop() instanceof Mulop_2) {
			Code.put(Code.div);
		}
		else if(mulOpTermList.getMulop() instanceof Mulop_3) {
			Code.put(Code.rem); //mod operacija
		}
	}
	
	//Conditions
	
	private int relOp(Relop relOp) {
		if(relOp instanceof Relop_1) {
			return Code.eq;
		}
		else if(relOp instanceof Relop_2) {
			return Code.ne;
		}
		else if(relOp instanceof Relop_3) {
			return Code.gt;
		}
		else if(relOp instanceof Relop_4) {
			return Code.ge;
		}
		else if(relOp instanceof Relop_5) {
			return Code.lt;
		}
		else if(relOp instanceof Relop_6) {
			return Code.le;
		}
		return 0;
		
	}
	
	private Stack<Integer> condFactSkipList = new Stack<>();
	private Stack<Integer> conditionSkipList = new Stack<>();//oni koji su ispunili ceo OR
	private Stack<Integer> thenSkip = new Stack<>();//zbog ugnjezdenih ifova
	private Stack<Integer> elseSkip = new Stack<>();
	
	@Override
	public void visit(CondFact_1 condExpr) {
		//expr je 1 -> tacan (ne skace se), expr je 0 -> netacan
		//poredimo sa 0
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0); //if(a == 0); netacna
		condFactSkipList.push(Code.pc - 2); //pamtimo adr na kojoj pocinje skok
		//tacno -> ne skacemo nego nastavljamo
	}
	
	@Override
	public void visit(CondFact_relop condExprRelopExpr) {
		Code.putFalseJump(relOp(condExprRelopExpr.getRelop()), 0); //if(a == 0)
		condFactSkipList.push(Code.pc - 2); //pamtimo adr na kojoj pocinje skok
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		Code.putJump(0); //ispunjen ceo jedan OR, izbacujemo tacne
		conditionSkipList.push(Code.pc - 2);
		//vracamo netacne
		while(!condFactSkipList.empty()) {
			Code.fixup(condFactSkipList.pop());
		}
	}
	
	@Override
	public void visit(Condition condition) {
	    if (inForCondition) {
	        ForContext ctx = forStack.peek();

	        // false izlaz iz celog uslova -> izlaz iz petlje
	        Code.putJump(0);
	        ctx.falseExitAdr = Code.pc - 2;

	        // true izlazi NE fixuju se ovde, nego kasnije na bodyPc
	        while (!conditionSkipList.empty()) {
	            ctx.trueJumpAddrs.add(conditionSkipList.pop());
	        }
	    }
	    else if (condition.getParent() instanceof Expr_question) {
	        Expr_question tern = (Expr_question) condition.getParent();

	        // false -> else grana
	        Code.putJump(0);
	        int falseJumpAdr = Code.pc - 2;

	        // true -> odmah na pocetak prve grane
	        while (!conditionSkipList.empty()) {
	            Code.fixup(conditionSkipList.pop());
	        }

	        ternaryStack.push(new TernaryContext(tern, falseJumpAdr));
	    }
	    else {
	        // netacne
	        Code.putJump(0);
	        thenSkip.push(Code.pc - 2);

	        // tacne
	        while(!conditionSkipList.empty()) {
	            Code.fixup(conditionSkipList.pop());
	        }
	    }
	}
	
	//nema ELSE
	@Override
	public void visit(Else01_e elseNo) {
		Code.fixup(thenSkip.pop());
	}
	
	@Override
	public void visit(Else else_) {
		Code.putJump(0); //tacne na kraj else a
		elseSkip.push(Code.pc - 2);
		Code.fixup(thenSkip.pop());	
	}
	
	@Override
	public void visit(Else01_p elseYes) {
		//netacne
		Code.fixup(elseSkip.pop());
		//tacne + netacne
	}
	
	
	//continue and break
	private Stack<List<Integer>> continueJump = new Stack();
	private Stack<List<Integer>> breakJump = new Stack();
	
	@Override
	public void visit(Statement_3 breakStmt) {
		Code.putJump(0);
		breakJump.peek().add(Code.pc - 2);
	}
	
	@Override
	public void visit(Statement_4 continueStmt) {
		Code.putJump(0);
		continueJump.peek().add(Code.pc - 2);
	}
	
	
	
	//For and Switch 
	private static class ForContext {
	    int condPc;                 // gde počinje provera uslova
	    int stepPc;                 // gde počinje 2. designator statement
	    int bodyPc;                 // gde počinje telo
	    int jumpOverStepAdr;        // skok koji prvi put preskače step i ide u telo
	    int falseExitAdr = -1;      // izlaz iz petlje kada je uslov false
	    boolean hasCondition = false;
	    List<Integer> trueJumpAddrs = new ArrayList<>();
	}

	private Stack<ForContext> forStack = new Stack<>();
	private boolean inForCondition = false;
	
	@Override
	public void visit(ForNonterm forNonterm) {
	    forStack.push(new ForContext());
	    breakJump.push(new ArrayList<>());
	    continueJump.push(new ArrayList<>());
	}
	
	@Override
	public void visit(Semicolon1 semicolon1) {
	    ForContext ctx = forStack.peek();
	    ctx.condPc = Code.pc;
	    inForCondition = true;
	}
	
	@Override
	public void visit(Condition01_p cond) {
	    ForContext ctx = forStack.peek();
	    ctx.hasCondition = true;
	    inForCondition = false;

	    Code.putJump(0);                 // prvi prolaz: idi u body, ne u step
	    ctx.jumpOverStepAdr = Code.pc - 2;
	    ctx.stepPc = Code.pc;            // odmah posle ovog skoka počinje step deo
	}

	@Override
	public void visit(Condition01_e cond) {
	    ForContext ctx = forStack.peek();
	    ctx.hasCondition = false;
	    inForCondition = false;

	    Code.putJump(0);                 // i bez uslova prvi put se preskače step
	    ctx.jumpOverStepAdr = Code.pc - 2;
	    ctx.stepPc = Code.pc;
	}
	
	@Override
	public void visit(Rparen rparen) {
	    ForContext ctx = forStack.peek();

	    Code.putJump(ctx.condPc);   // posle step dela ponovo proveri uslov

	    ctx.bodyPc = Code.pc;

	    // prvi put preskoči step i idi direktno u telo
	    Code.fixup(ctx.jumpOverStepAdr);

	    // svi "true" izlazi iz uslova treba da idu na telo
	    for (int adr : ctx.trueJumpAddrs) {
	        Code.fixup(adr);
	    }
	}
	@Override
	public void visit(Statement_9 stmtFor) {
	    ForContext ctx = forStack.pop();

	    // continue treba da dodje NA instrukciju koja skace na step
	    for (int adr : continueJump.pop()) {
	        Code.fixup(adr);
	    }

	    // kraj tela -> step
	    Code.putJump(ctx.stepPc);

	    // false uslova -> izlaz iz petlje
	    if (ctx.hasCondition && ctx.falseExitAdr != -1) {
	        Code.fixup(ctx.falseExitAdr);
	    }

	    // break -> izlaz iz petlje
	    for (int adr : breakJump.pop()) {
	        Code.fixup(adr);
	    }
	}
	
	private static class SwitchCaseInfo {
	    int value;
	    int bodyPc;

	    SwitchCaseInfo(int value, int bodyPc) {
	        this.value = value;
	        this.bodyPc = bodyPc;
	    }
	}

	private static class SwitchContext {
	    int jumpToCompareAdr;
	    int pendingCaseValue;
	    List<SwitchCaseInfo> cases = new ArrayList<>();
	}

	private Stack<SwitchContext> switchStack = new Stack<>();
	
	@Override
	public void visit(SwitchNonterm switchNonterm) {
	    switchStack.push(new SwitchContext());
	    breakJump.push(new ArrayList<>());
	}
	
	@Override
	public void visit(CasePom_e casePomE) {
	    SwitchContext ctx = switchStack.peek();
	    Code.putJump(0);
	    ctx.jumpToCompareAdr = Code.pc - 2;
	}
	
	@Override
	public void visit(CaseNumber caseNumber) {
	    switchStack.peek().pendingCaseValue = caseNumber.getN1();
	}
	
	@Override
	public void visit(Colon2 colon2) {
	    SwitchContext ctx = switchStack.peek();
	    ctx.cases.add(new SwitchCaseInfo(ctx.pendingCaseValue, Code.pc));
	}
	
	@Override
	public void visit(Statement_8 stmtSwitch) {
	    SwitchContext ctx = switchStack.pop();

	    // Skok na kraj switch-a: kada poslednji case nema break, fall-through
	    // dolazi ovde i mora preskočiti compare sekciju (dup očekuje vrednost na steku).
	    Code.putJump(0);
	    int switchEndJumpAdr = Code.pc - 2;

	    // ovde počinje sekcija za poređenja (nakon expr skok sa vrednošću na steku)
	    Code.fixup(ctx.jumpToCompareAdr);

	    List<Integer> falseCaseJumps = new ArrayList<>();

	    for (SwitchCaseInfo c : ctx.cases) {
	        Code.put(Code.dup);          // zadrži originalnu switch vrednost
	        Code.loadConst(c.value);

	        Code.putFalseJump(Code.eq, 0);
	        int adrFalse = Code.pc - 2;

	        // pogodjen case
	        Code.put(Code.pop);          // skloni originalni expr sa steka
	        Code.putJump(c.bodyPc);

	        falseCaseJumps.add(adrFalse);
	        Code.fixup(adrFalse);
	    }

	    // nijedan case nije pogodjen
	    Code.put(Code.pop);

	    // fall-through sa poslednjeg case-a skače ovde
	    Code.fixup(switchEndJumpAdr);

	    // break -> kraj switch-a
	    for (int adr : breakJump.pop()) {
	        Code.fixup(adr);
	    }
	}
	
	
	private static class TernaryContext {
	    Expr_question owner;
	    int falseJumpAdr;   // skok na else granu
	    int endJumpAdr = -1; // skok preko else grane

	    TernaryContext(Expr_question owner, int falseJumpAdr) {
	        this.owner = owner;
	        this.falseJumpAdr = falseJumpAdr;
	    }
	}

	private Stack<TernaryContext> ternaryStack = new Stack<>();
	
	private void handleTernaryExprEnd(Expr expr) {
	    if (!(expr.getParent() instanceof Expr_question)) {
	        return;
	    }

	    if (ternaryStack.isEmpty()) {
	        return;
	    }

	    Expr_question parent = (Expr_question) expr.getParent();
	    TernaryContext ctx = ternaryStack.peek();

	    if (ctx.owner != parent) {
	        return;
	    }

	    // zavrsena prva grana: expr1
	    if (expr == parent.getExpr()) {
	        Code.putJump(0);                // preskoci else granu
	        ctx.endJumpAdr = Code.pc - 2;

	        Code.fixup(ctx.falseJumpAdr);   // else pocinje ovde
	    }
	    // zavrsena druga grana: expr2
	    else if (expr == parent.getExpr1()) {
	        Code.fixup(ctx.endJumpAdr);     // kraj ternarnog izraza
	        ternaryStack.pop();
	    }
	}
	
	@Override
	public void visit(Expr_1 exprSingle) {
	    handleTernaryExprEnd(exprSingle);
	}
	
	@Override
	public void visit(Expr_question exprQuestion) {
	    handleTernaryExprEnd(exprQuestion);
	}
}