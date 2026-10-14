// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class VarDecl0n_p extends VarDecl0n {

    private VarDecl0n VarDecl0n;
    private VarDecl VarDecl;

    public VarDecl0n_p (VarDecl0n VarDecl0n, VarDecl VarDecl) {
        this.VarDecl0n=VarDecl0n;
        if(VarDecl0n!=null) VarDecl0n.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public VarDecl0n getVarDecl0n() {
        return VarDecl0n;
    }

    public void setVarDecl0n(VarDecl0n VarDecl0n) {
        this.VarDecl0n=VarDecl0n;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDecl0n!=null) VarDecl0n.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDecl0n!=null) VarDecl0n.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDecl0n!=null) VarDecl0n.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl0n_p(\n");

        if(VarDecl0n!=null)
            buffer.append(VarDecl0n.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl0n_p]");
        return buffer.toString();
    }
}
