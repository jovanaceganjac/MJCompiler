// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class ConstVarEnumDeclList1_con extends ConstVarEnumDeclList {

    private ConstVarEnumDeclList ConstVarEnumDeclList;
    private ConstDeclPom ConstDeclPom;

    public ConstVarEnumDeclList1_con (ConstVarEnumDeclList ConstVarEnumDeclList, ConstDeclPom ConstDeclPom) {
        this.ConstVarEnumDeclList=ConstVarEnumDeclList;
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.setParent(this);
        this.ConstDeclPom=ConstDeclPom;
        if(ConstDeclPom!=null) ConstDeclPom.setParent(this);
    }

    public ConstVarEnumDeclList getConstVarEnumDeclList() {
        return ConstVarEnumDeclList;
    }

    public void setConstVarEnumDeclList(ConstVarEnumDeclList ConstVarEnumDeclList) {
        this.ConstVarEnumDeclList=ConstVarEnumDeclList;
    }

    public ConstDeclPom getConstDeclPom() {
        return ConstDeclPom;
    }

    public void setConstDeclPom(ConstDeclPom ConstDeclPom) {
        this.ConstDeclPom=ConstDeclPom;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.accept(visitor);
        if(ConstDeclPom!=null) ConstDeclPom.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.traverseTopDown(visitor);
        if(ConstDeclPom!=null) ConstDeclPom.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.traverseBottomUp(visitor);
        if(ConstDeclPom!=null) ConstDeclPom.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarEnumDeclList1_con(\n");

        if(ConstVarEnumDeclList!=null)
            buffer.append(ConstVarEnumDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclPom!=null)
            buffer.append(ConstDeclPom.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarEnumDeclList1_con]");
        return buffer.toString();
    }
}
