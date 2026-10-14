// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class Statement0n_p extends Statement0n {

    private Statement0n Statement0n;
    private Statement Statement;

    public Statement0n_p (Statement0n Statement0n, Statement Statement) {
        this.Statement0n=Statement0n;
        if(Statement0n!=null) Statement0n.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public Statement0n getStatement0n() {
        return Statement0n;
    }

    public void setStatement0n(Statement0n Statement0n) {
        this.Statement0n=Statement0n;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Statement0n!=null) Statement0n.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Statement0n!=null) Statement0n.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Statement0n!=null) Statement0n.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement0n_p(\n");

        if(Statement0n!=null)
            buffer.append(Statement0n.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement0n_p]");
        return buffer.toString();
    }
}
