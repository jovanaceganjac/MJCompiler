// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class CondFact_relop extends CondFact {

    private ExprCF ExprCF;
    private Relop Relop;
    private ExprCF ExprCF1;

    public CondFact_relop (ExprCF ExprCF, Relop Relop, ExprCF ExprCF1) {
        this.ExprCF=ExprCF;
        if(ExprCF!=null) ExprCF.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.ExprCF1=ExprCF1;
        if(ExprCF1!=null) ExprCF1.setParent(this);
    }

    public ExprCF getExprCF() {
        return ExprCF;
    }

    public void setExprCF(ExprCF ExprCF) {
        this.ExprCF=ExprCF;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public ExprCF getExprCF1() {
        return ExprCF1;
    }

    public void setExprCF1(ExprCF ExprCF1) {
        this.ExprCF1=ExprCF1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprCF!=null) ExprCF.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(ExprCF1!=null) ExprCF1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprCF!=null) ExprCF.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(ExprCF1!=null) ExprCF1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprCF!=null) ExprCF.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(ExprCF1!=null) ExprCF1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact_relop(\n");

        if(ExprCF!=null)
            buffer.append(ExprCF.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprCF1!=null)
            buffer.append(ExprCF1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact_relop]");
        return buffer.toString();
    }
}
