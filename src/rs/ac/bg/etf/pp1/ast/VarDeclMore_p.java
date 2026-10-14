// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class VarDeclMore_p extends VarDeclMore {

    private VarDeclPom VarDeclPom;
    private VarDeclMore VarDeclMore;

    public VarDeclMore_p (VarDeclPom VarDeclPom, VarDeclMore VarDeclMore) {
        this.VarDeclPom=VarDeclPom;
        if(VarDeclPom!=null) VarDeclPom.setParent(this);
        this.VarDeclMore=VarDeclMore;
        if(VarDeclMore!=null) VarDeclMore.setParent(this);
    }

    public VarDeclPom getVarDeclPom() {
        return VarDeclPom;
    }

    public void setVarDeclPom(VarDeclPom VarDeclPom) {
        this.VarDeclPom=VarDeclPom;
    }

    public VarDeclMore getVarDeclMore() {
        return VarDeclMore;
    }

    public void setVarDeclMore(VarDeclMore VarDeclMore) {
        this.VarDeclMore=VarDeclMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclPom!=null) VarDeclPom.accept(visitor);
        if(VarDeclMore!=null) VarDeclMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclPom!=null) VarDeclPom.traverseTopDown(visitor);
        if(VarDeclMore!=null) VarDeclMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclPom!=null) VarDeclPom.traverseBottomUp(visitor);
        if(VarDeclMore!=null) VarDeclMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclMore_p(\n");

        if(VarDeclPom!=null)
            buffer.append(VarDeclPom.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclMore!=null)
            buffer.append(VarDeclMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclMore_p]");
        return buffer.toString();
    }
}
