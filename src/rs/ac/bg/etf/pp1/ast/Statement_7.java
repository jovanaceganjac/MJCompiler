// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class Statement_7 extends Statement {

    private Expr Expr;
    private NumConstPom NumConstPom;

    public Statement_7 (Expr Expr, NumConstPom NumConstPom) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.NumConstPom=NumConstPom;
        if(NumConstPom!=null) NumConstPom.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public NumConstPom getNumConstPom() {
        return NumConstPom;
    }

    public void setNumConstPom(NumConstPom NumConstPom) {
        this.NumConstPom=NumConstPom;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(NumConstPom!=null) NumConstPom.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(NumConstPom!=null) NumConstPom.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(NumConstPom!=null) NumConstPom.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_7(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NumConstPom!=null)
            buffer.append(NumConstPom.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_7]");
        return buffer.toString();
    }
}
