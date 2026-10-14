// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private ConstNumCharBool ConstNumCharBool;

    public ConstDecl (String I1, ConstNumCharBool ConstNumCharBool) {
        this.I1=I1;
        this.ConstNumCharBool=ConstNumCharBool;
        if(ConstNumCharBool!=null) ConstNumCharBool.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ConstNumCharBool getConstNumCharBool() {
        return ConstNumCharBool;
    }

    public void setConstNumCharBool(ConstNumCharBool ConstNumCharBool) {
        this.ConstNumCharBool=ConstNumCharBool;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstNumCharBool!=null) ConstNumCharBool.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstNumCharBool!=null) ConstNumCharBool.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstNumCharBool!=null) ConstNumCharBool.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ConstNumCharBool!=null)
            buffer.append(ConstNumCharBool.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
