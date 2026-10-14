// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class Statement_8 extends Statement {

    private SwitchNonterm SwitchNonterm;
    private Expr Expr;
    private CasePom CasePom;

    public Statement_8 (SwitchNonterm SwitchNonterm, Expr Expr, CasePom CasePom) {
        this.SwitchNonterm=SwitchNonterm;
        if(SwitchNonterm!=null) SwitchNonterm.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.CasePom=CasePom;
        if(CasePom!=null) CasePom.setParent(this);
    }

    public SwitchNonterm getSwitchNonterm() {
        return SwitchNonterm;
    }

    public void setSwitchNonterm(SwitchNonterm SwitchNonterm) {
        this.SwitchNonterm=SwitchNonterm;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public CasePom getCasePom() {
        return CasePom;
    }

    public void setCasePom(CasePom CasePom) {
        this.CasePom=CasePom;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchNonterm!=null) SwitchNonterm.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(CasePom!=null) CasePom.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchNonterm!=null) SwitchNonterm.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(CasePom!=null) CasePom.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchNonterm!=null) SwitchNonterm.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(CasePom!=null) CasePom.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_8(\n");

        if(SwitchNonterm!=null)
            buffer.append(SwitchNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CasePom!=null)
            buffer.append(CasePom.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_8]");
        return buffer.toString();
    }
}
