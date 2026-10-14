// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class CondFact_1 extends CondFact {

    private ExprCF ExprCF;

    public CondFact_1 (ExprCF ExprCF) {
        this.ExprCF=ExprCF;
        if(ExprCF!=null) ExprCF.setParent(this);
    }

    public ExprCF getExprCF() {
        return ExprCF;
    }

    public void setExprCF(ExprCF ExprCF) {
        this.ExprCF=ExprCF;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprCF!=null) ExprCF.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprCF!=null) ExprCF.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprCF!=null) ExprCF.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact_1(\n");

        if(ExprCF!=null)
            buffer.append(ExprCF.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact_1]");
        return buffer.toString();
    }
}
