// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodDeclType MethodDeclType;
    private FormPars01 FormPars01;
    private VarDecl0n VarDecl0n;
    private Statement0n Statement0n;

    public MethodDecl (MethodDeclType MethodDeclType, FormPars01 FormPars01, VarDecl0n VarDecl0n, Statement0n Statement0n) {
        this.MethodDeclType=MethodDeclType;
        if(MethodDeclType!=null) MethodDeclType.setParent(this);
        this.FormPars01=FormPars01;
        if(FormPars01!=null) FormPars01.setParent(this);
        this.VarDecl0n=VarDecl0n;
        if(VarDecl0n!=null) VarDecl0n.setParent(this);
        this.Statement0n=Statement0n;
        if(Statement0n!=null) Statement0n.setParent(this);
    }

    public MethodDeclType getMethodDeclType() {
        return MethodDeclType;
    }

    public void setMethodDeclType(MethodDeclType MethodDeclType) {
        this.MethodDeclType=MethodDeclType;
    }

    public FormPars01 getFormPars01() {
        return FormPars01;
    }

    public void setFormPars01(FormPars01 FormPars01) {
        this.FormPars01=FormPars01;
    }

    public VarDecl0n getVarDecl0n() {
        return VarDecl0n;
    }

    public void setVarDecl0n(VarDecl0n VarDecl0n) {
        this.VarDecl0n=VarDecl0n;
    }

    public Statement0n getStatement0n() {
        return Statement0n;
    }

    public void setStatement0n(Statement0n Statement0n) {
        this.Statement0n=Statement0n;
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
        if(MethodDeclType!=null) MethodDeclType.accept(visitor);
        if(FormPars01!=null) FormPars01.accept(visitor);
        if(VarDecl0n!=null) VarDecl0n.accept(visitor);
        if(Statement0n!=null) Statement0n.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclType!=null) MethodDeclType.traverseTopDown(visitor);
        if(FormPars01!=null) FormPars01.traverseTopDown(visitor);
        if(VarDecl0n!=null) VarDecl0n.traverseTopDown(visitor);
        if(Statement0n!=null) Statement0n.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclType!=null) MethodDeclType.traverseBottomUp(visitor);
        if(FormPars01!=null) FormPars01.traverseBottomUp(visitor);
        if(VarDecl0n!=null) VarDecl0n.traverseBottomUp(visitor);
        if(Statement0n!=null) Statement0n.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodDeclType!=null)
            buffer.append(MethodDeclType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars01!=null)
            buffer.append(FormPars01.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl0n!=null)
            buffer.append(VarDecl0n.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement0n!=null)
            buffer.append(Statement0n.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
