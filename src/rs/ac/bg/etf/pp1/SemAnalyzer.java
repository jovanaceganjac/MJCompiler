package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemAnalyzer extends VisitorAdaptor {
	
	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private Obj currentProgram;
	private Struct currentType;
	private int constant;
	private Struct constantType;
	private Struct boolType = Tab.find("bool").getType();
	private Obj currentMethod = null;
	private boolean returnFound = false;
	private Obj mainMethod;
	private int paramPos = 0;
	int nVars;
	private Set<Obj> formalParams = new HashSet<>();

	/* LOG MESSAGES */
	public void report_error(String message, SyntaxNode info) {
		errorDetected  = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	// --- Detekcija upotrebe simbola (Nivo A/B): linija, naziv, ispis Obj (Prilog 2), preko report_info ---
	private static String kindToString(int kind) {
		switch (kind) {
			case Obj.Con: return "Con";
			case Obj.Var: return "Var";
			case Obj.Meth: return "Meth";
			case Obj.Type: return "Type";
			case Obj.Elem: return "Elem";
			case Obj.Prog: return "Prog";
			case Obj.Fld: return "Fld";
			default: return "Kind(" + kind + ")";
		}
	}

	private static String structToString(Struct s) {
		if (s == null) return "null";
		switch (s.getKind()) {
			case Struct.Int: return "int";
			case Struct.Char: return "char";
			case Struct.Bool: return "bool";
			case Struct.Array: return "Arr of " + (s.getElemType() != null ? structToString(s.getElemType()) : "?");
			case Struct.None: return "notype";
			default: return "Struct(" + s.getKind() + ")";
		}
	}

	private String formatObj(Obj obj) {
		if (obj == null || obj == Tab.noObj) return "(nepostojeci)";
		String kind = kindToString(obj.getKind());
		String typeStr = structToString(obj.getType());
		return kind + " " + obj.getName() + ": " + typeStr + ", " + obj.getAdr() + ", " + obj.getLevel();
	}

	private boolean isFormalParam(Obj obj) {
		return obj != null && formalParams.contains(obj);
	}

	public boolean passed() {
		return !errorDetected;
	}
	
	
	/* SEMANTIC PASS CODE */

	@Override
	public void visit(ProgramName progName) {
		currentProgram = Tab.insert(Obj.Prog, progName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		//nVars - prebrojavanje globalnih podataka
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(currentProgram);
		Tab.closeScope();
		currentProgram = null;
		if(mainMethod == null || mainMethod.getLevel() > 0) {
			//ako ne postoji ili ima neki formalni parametar
			report_error("Program nema main metodu!", program);
		}
	}
	
	//CONST DECLARATIONS
	@Override
	public void visit(ConstDecl constDecl) {
		String name = constDecl.getI1();
		Obj constObj = Tab.find(name);
		if(constObj != Tab.noObj) {
			report_error("Dvostruka definicija konstante: " + name, constDecl);
		}
		else if(constantType.assignableTo(currentType)) {
			constObj = Tab.insert(Obj.Con, name, currentType);
			constObj.setAdr(constant);
		}
		else {
			report_error("Neadekvatna dodela konstanti: " + name, constDecl);
		}
	}
	
	
	
	@Override
	public void visit(ConstNumCharBool_num constNum) {
		constant = constNum.getN1();
		constantType = Tab.intType;
	}
	
	@Override
	public void visit(ConstNumCharBool_char constChar) {
		constant = constChar.getC1();
		constantType = Tab.charType;
	}
	
	@Override
	public void visit(ConstNumCharBool_bool constBool) {
		constant = constBool.getB1();
		constantType = boolType;
	}
	
	//VAR DECLARATIONS
	@Override 
	public void visit(VarDeclPom_var varDeclVar) {
		Obj varObj = null;
		if(currentMethod == null) {
			varObj = Tab.find(varDeclVar.getI1());
		}
		else {
			varObj = Tab.currentScope().findSymbol(varDeclVar.getI1());
		}
		
		if(varObj == null || varObj == Tab.noObj) {
			if (currentType != Tab.noType) {
				varObj = Tab.insert(Obj.Var, varDeclVar.getI1(), currentType);
			}
		}
		else {
			report_error("Dvostruka definicija promenljive: " + varDeclVar.getI1(), varDeclVar);
		}
	}
	
	@Override 
	public void visit(VarDeclPom_array varDeclArr) {
		Obj arrayObj = null;
		if(currentMethod == null) {
			arrayObj = Tab.find(varDeclArr.getI1());
		}
		else {
			arrayObj = Tab.currentScope().findSymbol(varDeclArr.getI1());
		}
		if(arrayObj == null || arrayObj == Tab.noObj) {
			if (currentType != Tab.noType) {
				arrayObj = Tab.insert(Obj.Var, varDeclArr.getI1(), new Struct(Struct.Array, currentType));
			}
		}
		else {
			report_error("Dvostruka definicija promenljive: " + varDeclArr.getI1(), varDeclArr);
		}
	}
	
	//METHOD DECLARATIONS
	
	@Override
	public void visit(MethodDecl methodDecl) {
		// Provera: main mora biti void main() bez parametara
		if (currentMethod != null) {

		    MethodDeclType mdt = methodDecl.getMethodDeclType();
		    String methodName = null;

		    if (mdt instanceof MethodDeclType_v) {
		        methodName = ((MethodDeclType_v) mdt).getI1();
		    } 
		    else if (mdt instanceof MethodDeclType_t) {
		        methodName = ((MethodDeclType_t) mdt).getI2();
		    }

		    if (methodName != null && methodName.equalsIgnoreCase("main")) {
		        if (methodDecl.getFormPars01() instanceof FormPars01_p) {
		            report_error("Metoda main ne sme imati parametre.", methodDecl);
		        }
		    }
		}
	    // Poveži lokalne simbole (parametre i lokalne promenljive) sa metodom
	    if (currentMethod != null) {
	        Tab.chainLocalSymbols(currentMethod);
	    }
	    Tab.closeScope();
	    formalParams.clear();
	    currentMethod = null;
	    returnFound = false;
	}

	public void visit(MethodDeclType_v methodVoid) {
		String methodName = methodVoid.getI1();
		methodVoid.obj = currentMethod = Tab.insert(Obj.Meth, methodName, Tab.noType);
		Tab.openScope();
		paramPos = 0;
		formalParams.clear();
		if(methodName.equalsIgnoreCase("main")) {
			mainMethod = currentMethod;
		}
	}
	
	
	@Override
	public void visit(MethodDeclType_t methodType) {
		String methodName = methodType.getI2();
		methodType.obj = currentMethod = Tab.insert(Obj.Meth, methodName, currentType);
		Tab.openScope();
		paramPos = 0;
		formalParams.clear();
	}

	// FORMALNI PARAMETRI (FormPars = Type IDENT Brackets FormPars0n; FormPars0n_p = COMMA Type IDENT Brackets FormPars0n)
	@Override
	public void visit(FormPars_var formParsVar) {
		if (currentMethod == null) {
	        report_error("Semanticka greska: formalni parametri van metode", formParsVar);
	        return;
	    }

	    String name = formParsVar.getI2();
	    Obj param = Tab.currentScope().findSymbol(name);
	    if (param != null && param != Tab.noObj) {
	        report_error("Dvostruka definicija formalnog parametra: " + name, formParsVar);
	    }
	    else if (currentType != Tab.noType) {
	    	paramPos++;
	    	param = Tab.insert(Obj.Var, name, currentType);
	    	param.setFpPos(paramPos);
	    	currentMethod.setLevel(currentMethod.getLevel() + 1);
	    	formalParams.add(param);
	    }
	}

	@Override
	public void visit(FormPars_arr formParsArr) {
		if (currentMethod == null) {
	        report_error("Semanticka greska: formalni parametar van metode", formParsArr);
	        return;
	    }

	    String name = formParsArr.getI2();
	    Obj param = Tab.currentScope().findSymbol(name);
	    if (param != null && param != Tab.noObj) {
	        report_error("Dvostruka definicija formalnog parametra: " + name, formParsArr);
	    }
	    else if (currentType != Tab.noType) {
	    	paramPos++;
	    	param = Tab.insert(Obj.Var, name, new Struct(Struct.Array, currentType));
	    	param.setFpPos(paramPos);
	    	currentMethod.setLevel(currentMethod.getLevel() + 1);
	    	formalParams.add(param);
	    }
	}

	//ENUM DECLARATION
	private void collectEnumItems(EnumDeclPom2 p2, List<String> imena, List<Integer> vrednosti) {
	    if (p2 instanceof EnumDeclPom2_p) {
	        EnumDeclPom2_p p = (EnumDeclPom2_p) p2;

	        // prvo idi levo, da bi redosled bio kao u izvornom kodu
	        collectEnumItems(p.getEnumDeclPom2(), imena, vrednosti);

	        imena.add(p.getI2());
	        if (p.getEnumDeclPom1() instanceof EnumDeclPom1_p) {
	            vrednosti.add(((EnumDeclPom1_p) p.getEnumDeclPom1()).getN1());
	        } else {
	            vrednosti.add(null);
	        }
	    }
	}
	@Override
	public void visit(EnumDecl enumDecl) {
	    String imeEnuma = enumDecl.getI1();

	    if (Tab.currentScope().findSymbol(imeEnuma) != null) {
	        report_error("Ime enuma '" + imeEnuma + "' je vec zauzeto u ovom opsegu.", enumDecl);
	        return;
	    }

	    Obj enumObj = Tab.insert(Obj.Type, imeEnuma, Tab.intType);
	    Tab.openScope();

	    List<String> imena = new ArrayList<>();
	    List<Integer> vrednosti = new ArrayList<>();

	    // prva stavka
	    imena.add(enumDecl.getI2());
	    if (enumDecl.getEnumDeclPom1() instanceof EnumDeclPom1_p) {
	        vrednosti.add(((EnumDeclPom1_p) enumDecl.getEnumDeclPom1()).getN1());
	    } else {
	        vrednosti.add(null);
	    }

	    // ostale stavke u ispravnom redosledu
	    collectEnumItems(enumDecl.getEnumDeclPom2(), imena, vrednosti);

	    int sledeca = 0;
	    Set<Integer> zauzeteVrednosti = new HashSet<>();

	    for (int i = 0; i < imena.size(); i++) {
	        String imeStavke = imena.get(i);
	        Integer vrednost = vrednosti.get(i);

	        if (Tab.currentScope().findSymbol(imeStavke) != null) {
	            report_error("Duplikat imena u enum-u '" + imeEnuma + "': " + imeStavke, enumDecl);
	            continue;
	        }

	        int v = (vrednost != null) ? vrednost : sledeca;

	        if (zauzeteVrednosti.contains(v)) {
	            report_error("Vrednost " + v + " je vec dodeljena u enum-u '" + imeEnuma + "'.", enumDecl);
	            sledeca = v + 1;
	            continue;
	        }

	        zauzeteVrednosti.add(v);

	        Obj c = Tab.insert(Obj.Con, imeStavke, Tab.intType);
	        c.setAdr(v);
	        sledeca = v + 1;

	        report_info("Enum stavka: " + imeStavke + " = " + v, enumDecl);
	    }

	    Tab.chainLocalSymbols(enumObj);
	    Tab.closeScope();
	    report_info("Definisan enum: " + imeEnuma, enumDecl);
	}
	//CONTECT CONDITIONS
	
	//Designator
	@Override
	public void visit(Designator_1 designatorVar) {
		String name = designatorVar.getI1();
		Obj varObj = Tab.find(name);
		if(varObj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivoj: " + name , designatorVar);
			designatorVar.obj = Tab.noObj;
		}
		else if(varObj.getKind() != Obj.Var && varObj.getKind() != Obj.Con && varObj.getKind() != Obj.Meth) {
			report_error("Neadekvatna promenljiva: " + name, designatorVar);
			designatorVar.obj = Tab.noObj;
		}
		else {
			designatorVar.obj = varObj;
			// Nivo A: simbolicka konstanta, globalna/lokalna promenljiva; Nivo B: formalni argument (ime postoji, tip ispravan)
			if (varObj.getKind() == Obj.Con)
				report_info("Simbolicka konstanta " + name + " " + formatObj(varObj), designatorVar);
			else if (varObj.getKind() == Obj.Var) {
				if (isFormalParam(varObj))
					report_info("Koriscenje formalnog argumenta funkcije " + name + " " + formatObj(varObj), designatorVar);
				else if (varObj.getLevel() == 0)
					report_info("Globalna promenljiva " + name + " " + formatObj(varObj), designatorVar);
				else
					report_info("Lokalna promenljiva " + name + " " + formatObj(varObj), designatorVar);
			}
		}
	}

	@Override
	public void visit(DesignatorArrayName designatorArrName) {
		String name = designatorArrName.getI1();
		Obj found = Tab.find(name);
		if (found == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivoj niza: " + name, designatorArrName);
			designatorArrName.obj = Tab.noObj;
		} else if (found.getKind() == Obj.Var && found.getType().getKind() == Struct.Array) {
			designatorArrName.obj = found;
		} else if (found.getKind() == Obj.Type) {
			// enum tip (npr. Color, Broj) za designator tipa Color.RED
			designatorArrName.obj = found;
		} else {
			report_error("Neadekvatna promenljiva niza: " + name, designatorArrName);
			designatorArrName.obj = Tab.noObj;
		}
	}

	@Override
	public void visit(Designator_2 designatorElem) {
		Obj arrObj = designatorElem.getDesignatorArrayName().obj;
		if (arrObj == null || arrObj == Tab.noObj) {
			designatorElem.obj = Tab.noObj;
			return;
		}
		// Designator_2 je samo za niz ([expr] ili .length)
		if (arrObj.getKind() != Obj.Var || arrObj.getType().getKind() != Struct.Array) {
			designatorElem.obj = Tab.noObj;
			return;
		}
		if (designatorElem.getIndexLength() instanceof IndexLength_1) {
			designatorElem.obj = new Obj(Obj.Elem, arrObj.getName() + "[$]", arrObj.getType().getElemType());
			// Nivo B: pristup elementu niza (niz postoji u tabeli, ispravnog tipa)
			report_info("Pristup elementu niza " + arrObj.getName() + " " + formatObj(arrObj), designatorElem);
		} else {
			// IndexLength_2 = .length, rezultat je int
			designatorElem.obj = new Obj(Obj.Var, arrObj.getName() + ".length", Tab.intType);
		}
	}

	/** Designator tipa "ident . ident" — npr. Color.RED (enum konstanta) ili arr.length */
	@Override
	public void visit(Designator_3 designatorDot) {
		Obj leftObj = designatorDot.getDesignatorArrayName().obj;
		if (leftObj == null || leftObj == Tab.noObj) {
			designatorDot.obj = Tab.noObj;
			return;
		}
		String ident = designatorDot.getI2();
		if (leftObj.getKind() == Obj.Type) {
			// enum tip: tražimo konstantu u lokalnim simbolima (chainLocalSymbols)
			java.util.Collection<Obj> locals = leftObj.getLocalSymbols();
			if (locals != null) {
				for (Obj con : locals) {
					if (ident.equals(con.getName()) && con.getKind() == Obj.Con) {
						designatorDot.obj = con;
						report_info("Simbolicka konstanta " + leftObj.getName() + "." + ident + " " + formatObj(con), designatorDot);
						return;
					}
				}
			}
			report_error("Enum konstanta '" + leftObj.getName() + "." + ident + "' ne postoji.", designatorDot);
			designatorDot.obj = Tab.noObj;
		} else if (leftObj.getKind() == Obj.Var && leftObj.getType().getKind() == Struct.Array && "length".equals(ident)) {
			designatorDot.obj = new Obj(Obj.Var, leftObj.getName() + ".length", Tab.intType);
		} else {
			report_error("Neadekvatan designator: " + leftObj.getName() + "." + ident, designatorDot);
			designatorDot.obj = Tab.noObj;
		}
	}

	@Override
	public void visit(IndexLength_1 indexLength) {
		Expr indexExpr = indexLength.getExpr();
		if (indexExpr.struct == null || !indexExpr.struct.assignableTo(Tab.intType)) {
			report_error("Indeks niza mora biti tipa int.", indexLength);
		}
	}
	
	
	//FACTOR
	@Override
	public void visit(FactorPom_num factorNum) {
		factorNum.struct = Tab.intType;
	}
	
	@Override
	public void visit(FactorPom_char factorChar) {
		factorChar.struct = Tab.charType;
	}
	
	@Override
	public void visit(FactorPom_bool factorBool) {
		factorBool.struct = boolType;
	}
	
	@Override
	public void visit(FactorPom_0 factorDesignator) {
		Obj obj = factorDesignator.getDesignator().obj;
		if (obj == null || obj == Tab.noObj) {
			factorDesignator.struct = Tab.noType;
		} else {
			factorDesignator.struct = obj.getType();
		}
	}
	
	private int getActParsCount(ActPars01 actPars01) {
	    return collectActParExprs(actPars01).size();
	}
	
	@Override
	public void visit(FactorPom_meth factorDesignatorMethod) {
		Obj obj = factorDesignatorMethod.getDesignator().obj;
		String name = obj != null ? obj.getName() : "";
		if (obj == null || obj == Tab.noObj || obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + name, factorDesignatorMethod);
			factorDesignatorMethod.struct = Tab.noType;
			return;
		}
		if (obj.getType() == null || obj.getType() == Tab.noType) {
			report_error("Void metoda '" + name + "' ne moze da se koristi u izrazu.", factorDesignatorMethod);
			factorDesignatorMethod.struct = Tab.noType;
			return;
		}
		report_info("Globalna funkcija (poziv) " + name + " " + formatObj(obj), factorDesignatorMethod);
		// Kontekstni uslovi za chr, ord, len (PDF)
		if ("chr".equals(name)) {
		    int cnt = getActParsCount(factorDesignatorMethod.getActPars01());
		    Struct argType = getFirstActParStruct(factorDesignatorMethod.getActPars01());
		    if (cnt != 1 || argType == null || !argType.assignableTo(Tab.intType)) {
		        report_error("chr(e): mora imati tacno jedan argument tipa int.", factorDesignatorMethod);
		    }
		} else if ("ord".equals(name)) {
		    int cnt = getActParsCount(factorDesignatorMethod.getActPars01());
		    Struct argType = getFirstActParStruct(factorDesignatorMethod.getActPars01());
		    if (cnt != 1 || argType == null || !argType.assignableTo(Tab.charType)) {
		        report_error("ord(c): mora imati tacno jedan argument tipa char.", factorDesignatorMethod);
		    }
		} else if ("len".equals(name)) {
		    int cnt = getActParsCount(factorDesignatorMethod.getActPars01());
		    Struct argType = getFirstActParStruct(factorDesignatorMethod.getActPars01());
		    if (cnt != 1 || argType == null || argType.getKind() != Struct.Array) {
		        report_error("len(a): mora imati tacno jedan argument koji je niz.", factorDesignatorMethod);
		    }
		} else {
		    checkActPars(factorDesignatorMethod.getActPars01(), obj, factorDesignatorMethod);
		}
		factorDesignatorMethod.struct = obj.getType();
	}

	/** Vraca tip prvog stvarnog argumenta ili null ako nema. */
	private Struct getFirstActParStruct(ActPars01 actPars01) {
		List<Expr> act = collectActParExprs(actPars01);
		return (act.isEmpty() || act.get(0).struct == null) ? null : act.get(0).struct;
	}

	/** Prikuplja listu izraza stvarnih argumenata u redosledu (prvi argument prvi u listi). */
	private List<Expr> collectActParExprs(ActPars01 actPars01) {
		List<Expr> list = new ArrayList<>();
		if (actPars01 instanceof ActPars_p) {
			ActPars a = ((ActPars_p) actPars01).getActPars();
			collectActParExprsRec(a, list);
		}
		return list;
	}

	private void collectActParExprsRec(ActPars a, List<Expr> list) {
		if (a instanceof ActPars_n) {
			collectActParExprsRec(((ActPars_n) a).getActPars(), list);
			list.add(((ActPars_n) a).getExpr());
		} else if (a instanceof ActPars_1) {
			list.add(((ActPars_1) a).getExpr());
		}
	}

	/** Broj i tipovi stvarnih argumenata vs formalni parametri (PDF). */
	private void checkActPars(ActPars01 actPars01, Obj methodObj, SyntaxNode node) {
		List<Expr> actuals = collectActParExprs(actPars01);
		int nFormals = methodObj.getLevel(); // broj formalnih parametara (setLevel u FormPars)
		List<Obj> formals = new ArrayList<>();
		for (int p = 1; p <= nFormals; p++) {
		    for (Obj sym : methodObj.getLocalSymbols()) {
		        if (sym.getFpPos() == p) {
		            formals.add(sym);
		            break;
		        }
		    }
		}
		if (actuals.size() != formals.size()) {
			report_error("Broj stvarnih argumenata (" + actuals.size() + ") mora biti jednak broju formalnih (" + formals.size() + ").", node);
			return;
		}
		for (int i = 0; i < actuals.size(); i++) {
			Struct actualType = actuals.get(i).struct;
			Struct formalType = formals.get(i).getType();
			if (actualType == null || !actualType.assignableTo(formalType)) {
				report_error("Tip stvarnog argumenta na poziciji " + (i + 1) + " nije kompatibilan sa formalnim parametrom.", node);
			}
		}
	}
	
	@Override
	public void visit(FactorPom_new factorNew) {
		Expr sizeExpr = factorNew.getExpr();
		if (sizeExpr == null || sizeExpr.struct == null || !sizeExpr.struct.assignableTo(Tab.intType)) {
			report_error("Velicina niza mora biti tipa int!", factorNew);
			factorNew.struct = Tab.noType;
			return;
		}
		Obj typeObj = Tab.find(factorNew.getType().getI1());
		if (typeObj == Tab.noObj || typeObj.getKind() != Obj.Type) {
			report_error("Nepostojeci tip u new: " + factorNew.getType().getI1(), factorNew);
			factorNew.struct = Tab.noType;
			return;
		}
		factorNew.struct = new Struct(Struct.Array, typeObj.getType());
	}
	
	@Override
	public void visit(FactorPom_expr factorExpr) {
		Expr e = factorExpr.getExpr();
		factorExpr.struct = (e != null && e.struct != null) ? e.struct : Tab.noType;
	}
	
	// EXPR 
	@Override
	public void visit(Term_2 termFactor) {
		termFactor.struct = termFactor.getFactor().struct;
	}

	@Override
	public void visit(Term_1 termMul) {
		Struct left = termMul.getTerm().struct;
		Struct right = termMul.getFactor().struct;
		if (left != null && right != null && left.assignableTo(Tab.intType) && right.assignableTo(Tab.intType)) {
			termMul.struct = Tab.intType;
		} else {
			report_error("Mulop operacija dozvoljena samo nad tipom int!", termMul);
			termMul.struct = Tab.noType;
		}
	}

	@Override
	public void visit(ExprCF_2 exprCFTerm) {
		exprCFTerm.struct = exprCFTerm.getTerm().struct;
	}

	@Override
	public void visit(ExprCF_1 exprCFAdd) {
		Struct left = exprCFAdd.getExprCF().struct;
		Struct right = exprCFAdd.getTerm().struct;
		if (left != null && right != null && left.assignableTo(Tab.intType) && right.assignableTo(Tab.intType)) {
			exprCFAdd.struct = Tab.intType;
		} else {
			report_error("Addop operacija dozvoljena samo nad tipom int!", exprCFAdd);
			exprCFAdd.struct = Tab.noType;
		}
	}

	@Override
	public void visit(ExprCF_3 exprCFMinus) {
		Struct termStruct = exprCFMinus.getTerm().struct;
		if (termStruct == null || !termStruct.assignableTo(Tab.intType)) {
			report_error("Unarni minus dozvoljen samo nad tipom int!", exprCFMinus);
			exprCFMinus.struct = Tab.noType;
		} else {
			exprCFMinus.struct = Tab.intType;
		}
	}

	@Override
	public void visit(Expr_1 exprSingle) {
		exprSingle.struct = exprSingle.getExprCF().struct;
	}

	@Override
	public void visit(Expr_question exprTernary) {
		Condition cond = exprTernary.getCondition();
		if (cond != null && (cond.struct == null || !cond.struct.equals(boolType))) {
			report_error("Uslov ternarnog izraza mora biti tipa bool.", exprTernary);
		}
		Struct thenType = exprTernary.getExpr().struct;
		Struct elseType = exprTernary.getExpr1().struct;
		if (thenType != null && elseType != null && thenType.assignableTo(elseType)) {
			exprTernary.struct = elseType;
		} else if (thenType != null && elseType != null && elseType.assignableTo(thenType)) {
			exprTernary.struct = thenType;
		} else if (thenType != null && elseType != null) {
			report_error("Obe grane ternarnog izraza moraju biti kompatibilnog tipa!", exprTernary);
			exprTernary.struct = Tab.noType;
		} else {
			exprTernary.struct = Tab.noType;
		}
	}

	//DESIGNATOR STATEMENTS
	@Override
	public void visit(DesignatorStatement_1 desStmtAssign) {
		Obj obj = desStmtAssign.getDesignator().obj;
		if (obj == null || obj == Tab.noObj) return;
		String name = obj.getName();
		if(obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Dodela u neadekvatnu promenljivu: " + name, desStmtAssign);
		}
		else {
			Struct exprStruct = desStmtAssign.getExpr().struct;
			if (exprStruct != null && !exprStruct.assignableTo(obj.getType())) {
				report_error("Neadekvatna dodela vrednosti u promenljivu: " + name, desStmtAssign);
			}
		}
	}
	
	@Override 
	public void visit(DesignatorStatement_3 desStmtINC) {
		Obj obj = desStmtINC.getDesignator().obj;
		if (obj == null || obj == Tab.noObj) return;
		String name = obj.getName();
		if(obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Inkrement nad neadekvatnom promenljivom: " + name, desStmtINC);
		}
		else if(!obj.getType().equals(Tab.intType)) {
			report_error("Inkrement nad promenljivom koja nije int: " + name, desStmtINC);
		}
	}
	
	@Override 
	public void visit(DesignatorStatement_4 desStmtDEC) {
		Obj obj = desStmtDEC.getDesignator().obj;
		if (obj == null || obj == Tab.noObj) return;
		String name = obj.getName();
		if(obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Dekrement nad neadekvatnom promenljivom: " + name, desStmtDEC);
		}
		else if(!obj.getType().equals(Tab.intType)) {
			report_error("Dekrement nad promenljivom koja nije int: " + name, desStmtDEC);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_2 desStmtMethod) {
		Obj obj = desStmtMethod.getDesignator().obj;
		String name = obj != null ? obj.getName() : "";
		
		if (obj == null || obj == Tab.noObj || obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + name, desStmtMethod);
			return;
		}
		
		report_info("Globalna funkcija (poziv) " + name + " " + formatObj(obj), desStmtMethod);

		if ("chr".equals(name)) {
			int cnt = getActParsCount(desStmtMethod.getActPars01());
			Struct argType = getFirstActParStruct(desStmtMethod.getActPars01());
			if (cnt != 1 || argType == null || !argType.assignableTo(Tab.intType)) {
				report_error("chr(e): mora imati tacno jedan argument tipa int.", desStmtMethod);
			}
		}
		else if ("ord".equals(name)) {
			int cnt = getActParsCount(desStmtMethod.getActPars01());
			Struct argType = getFirstActParStruct(desStmtMethod.getActPars01());
			if (cnt != 1 || argType == null || !argType.assignableTo(Tab.charType)) {
				report_error("ord(c): mora imati tacno jedan argument tipa char.", desStmtMethod);
			}
		}
		else if ("len".equals(name)) {
			int cnt = getActParsCount(desStmtMethod.getActPars01());
			Struct argType = getFirstActParStruct(desStmtMethod.getActPars01());
			if (cnt != 1 || argType == null || argType.getKind() != Struct.Array) {
				report_error("len(a): mora imati tacno jedan argument koji je niz.", desStmtMethod);
			}
		}
		else {
			checkActPars(desStmtMethod.getActPars01(), obj, desStmtMethod);
		}
	}
	 
	//STATEMENTS
	@Override 
	public void visit(Statement_6 stmtRead) {
		Obj obj = stmtRead.getDesignator().obj;
		if (obj == null || obj == Tab.noObj) {
			report_error("Read nad neadekvatnom promenljivom: " + (obj != null ? obj.getName() : "?"), stmtRead);
			return;
		}
		String name = obj.getName();
		Struct type = obj.getType();
		// Designator mora označavati promenljivu, element niza ili polje; .length nije dodeljivo
		if (name != null && name.endsWith(".length")) {
			report_error("Read ne može na .length (designator mora biti promenljiva, element niza ili polje): " + name, stmtRead);
			return;
		}
		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Read nad neadekvatnom promenljivom: " + name, stmtRead);
		}
		else if (type == null || (!type.equals(Tab.charType) && !type.equals(Tab.intType) && !type.equals(boolType))) {
			report_error("Read nad promenljivom koja nije char, int ili bool: " + name, stmtRead);
		}
	}
	
	@Override
	public void visit(Statement_7 stmtPrint) {
		Struct type = stmtPrint.getExpr().struct;
		if(type == null || (!type.equals(Tab.charType) && !type.equals(Tab.intType) && !type.equals(boolType))) {
			report_error("Print operacija nad neadekvatnim izrazom! ", stmtPrint);
		}
	}
	
	// --- Condition / CondTerm / CondFact (PDF: tip uslova mora biti bool) ---
	// --- Condition / CondTerm / CondFact ---

	@Override
	public void visit(CondFact_1 condFact1) {
		condFact1.struct = condFact1.getExprCF().struct;
		if (condFact1.struct == null || !condFact1.struct.equals(boolType)) {
			report_error("Uslovni izraz mora biti tipa bool.", condFact1);
			condFact1.struct = Tab.noType;
		}
	}
	
	private boolean isReferenceType(Struct t) {
	    return t != null && (t.getKind() == Struct.Array || t.getKind() == Struct.Class);
	}
	@Override
	public void visit(CondFact_relop condFactRelop) {
	    Struct left = condFactRelop.getExprCF().struct;
	    Struct right = condFactRelop.getExprCF1().struct;

	    if (left == null || right == null) {
	        report_error("Neispravni tipovi u relacionom izrazu.", condFactRelop);
	        condFactRelop.struct = Tab.noType;
	        return;
	    }

	    if (!left.compatibleWith(right)) {
	        report_error("Tipovi oba izraza u relaciji moraju biti kompatibilni.", condFactRelop);
	        condFactRelop.struct = Tab.noType;
	        return;
	    }

	    if (isReferenceType(left) || isReferenceType(right)) {
	        Relop op = condFactRelop.getRelop();
	        if (!(op instanceof Relop_1 || op instanceof Relop_2)) {
	            report_error("Uz referencijalne tipove dozvoljeni su samo == i !=.", condFactRelop);
	            condFactRelop.struct = Tab.noType;
	            return;
	        }
	    }

	    condFactRelop.struct = boolType;
	}

	@Override
	public void visit(CondFactList_1 condFactList1) {
		condFactList1.struct = condFactList1.getCondFact().struct;
	}

	@Override
	public void visit(CondFactList_2 condFactList2) {
		Struct left = condFactList2.getCondFactList().struct;
		Struct right = condFactList2.getCondFact().struct;

		if (left != null && right != null && left.equals(boolType) && right.equals(boolType)) {
			condFactList2.struct = boolType;
		} else {
			report_error("Operator && zahteva bool izraze sa obe strane.", condFactList2);
			condFactList2.struct = Tab.noType;
		}
	}

	@Override
	public void visit(CondTerm condTerm) {
		condTerm.struct = condTerm.getCondFactList().struct;
	}

	@Override
	public void visit(CondTermList_1 condTermList1) {
		condTermList1.struct = condTermList1.getCondTerm().struct;
	}

	@Override
	public void visit(CondTermList_2 condTermList2) {
		Struct left = condTermList2.getCondTermList().struct;
		Struct right = condTermList2.getCondTerm().struct;

		if (left != null && right != null && left.equals(boolType) && right.equals(boolType)) {
			condTermList2.struct = boolType;
		} else {
			report_error("Operator || zahteva bool izraze sa obe strane.", condTermList2);
			condTermList2.struct = Tab.noType;
		}
	}

	@Override
	public void visit(Condition condition) {
		condition.struct = condition.getCondTermList().struct;
	}

	

	// --- if: uslov mora biti bool (PDF) ---
	@Override
	public void visit(Statement_2 stmtIf) {
		Struct condType = stmtIf.getCondition().struct;
		if (condType == null || !condType.equals(boolType)) {
			report_error("Uslov u naredbi if mora biti tipa bool.", stmtIf);
		}
	}

	// --- for: uslov (ako postoji) mora biti bool (PDF) ---
	@Override
	public void visit(Statement_9 stmtFor) {
		if (stmtFor.getCondition01() instanceof Condition01_p) {
			Condition c = ((Condition01_p) stmtFor.getCondition01()).getCondition();
			if (c.struct == null || !c.struct.equals(boolType)) {
				report_error("Uslov u naredbi for mora biti tipa bool.", stmtFor);
			}
		}
	}

	// --- return (PDF): tip Expr ekvivalentan povratnom tipu; ako nema Expr, metoda mora biti void ---
	@Override
	public void visit(Statement_5 stmtReturn) {
		returnFound = true;
		if (currentMethod == null) {
			report_error("Return ne sme biti izvan tela metode.", stmtReturn);
			return;
		}
		Expr01 ex = stmtReturn.getExpr01();
		if (ex instanceof Expr01_p) {
			Expr e = ((Expr01_p) ex).getExpr();
			Struct retType = currentMethod.getType();
			if (retType == null || retType == Tab.noType) {
				report_error("Return sa vrednoscu nije dozvoljen u void metodi.", stmtReturn);
			} else if (e.struct == null || !e.struct.assignableTo(retType)) {
				report_error("Tip izraza u return mora biti kompatibilan sa povratnim tipom metode.", stmtReturn);
			}
		} else {
			if (currentMethod.getType() != Tab.noType) {
				report_error("Metoda koja vraća vrednost mora imati return sa izrazom.", stmtReturn);
			}
		}
	}

	// --- switch (PDF): Expr celobrojnog tipa; ne sme biti duplikata case konstanti ---
	@Override
	public void visit(Statement_8 stmtSwitch) {
		Struct exprType = stmtSwitch.getExpr().struct;
		if (exprType == null || !exprType.assignableTo(Tab.intType)) {
			report_error("Izraz u switch mora biti celobrojnog tipa.", stmtSwitch);
		}

		Set<Integer> caseValues = new HashSet<>();
		checkCaseValues(stmtSwitch.getCasePom(), caseValues, stmtSwitch);
	}

	private void checkCaseValues(CasePom cp, Set<Integer> caseValues, SyntaxNode info) {
		if (cp instanceof CasePom_p) {
			CasePom_p cpp = (CasePom_p) cp;

			// zbog leve rekurzije prvo obradi prethodne case-ove
			checkCaseValues(cpp.getCasePom(), caseValues, info);

			int val = cpp.getCaseNumber().getN1();
			if (caseValues.contains(val)) {
				report_error("Duplikat case vrednosti u switch: " + val, info);
			} else {
				caseValues.add(val);
			}
		}
	}

	// --- break (PDF): samo unutar for ili switch ---
	@Override
	public void visit(Statement_3 stmtBreak) {
		if (!isInsideForOrSwitch(stmtBreak)) {
			report_error("Break se moze koristiti samo unutar for petlje ili switch.", stmtBreak);
		}
	}

	// --- continue (PDF): samo unutar for ---
	@Override
	public void visit(Statement_4 stmtContinue) {
		if (!isInsideFor(stmtContinue)) {
			report_error("Continue se moze koristiti samo unutar for petlje.", stmtContinue);
		}
	}

	private boolean isInsideForOrSwitch(SyntaxNode node) {
		SyntaxNode p = node.getParent();
		while (p != null) {
			if (p instanceof Statement_9 || p instanceof Statement_8) return true;
			p = p.getParent();
		}
		return false;
	}

	private boolean isInsideFor(SyntaxNode node) {
	    SyntaxNode p = node.getParent();
	    while (p != null) {
	        if (p instanceof Statement_9) return true;
	        p = p.getParent();
	    }
	    return false;
	}
	@Override
	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getI1());
		if(typeObj == Tab.noObj) {
			report_error("Nepostojeci tip podatka: " + type.getI1(), type);
			type.struct = currentType = Tab.noType;
		}
		else if(typeObj.getKind() != Obj.Type) {
			report_error("Neadekvatan tip podatka: " + type.getI1(), type);
			type.struct = currentType = Tab.noType;
		}
		else
			type.struct = currentType = typeObj.getType();
	}


}