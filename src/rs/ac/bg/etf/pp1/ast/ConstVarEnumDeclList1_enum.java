// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class ConstVarEnumDeclList1_enum extends ConstVarEnumDeclList {

    private ConstVarEnumDeclList ConstVarEnumDeclList;
    private EnumDecl EnumDecl;

    public ConstVarEnumDeclList1_enum (ConstVarEnumDeclList ConstVarEnumDeclList, EnumDecl EnumDecl) {
        this.ConstVarEnumDeclList=ConstVarEnumDeclList;
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.setParent(this);
        this.EnumDecl=EnumDecl;
        if(EnumDecl!=null) EnumDecl.setParent(this);
    }

    public ConstVarEnumDeclList getConstVarEnumDeclList() {
        return ConstVarEnumDeclList;
    }

    public void setConstVarEnumDeclList(ConstVarEnumDeclList ConstVarEnumDeclList) {
        this.ConstVarEnumDeclList=ConstVarEnumDeclList;
    }

    public EnumDecl getEnumDecl() {
        return EnumDecl;
    }

    public void setEnumDecl(EnumDecl EnumDecl) {
        this.EnumDecl=EnumDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.accept(visitor);
        if(EnumDecl!=null) EnumDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.traverseTopDown(visitor);
        if(EnumDecl!=null) EnumDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarEnumDeclList!=null) ConstVarEnumDeclList.traverseBottomUp(visitor);
        if(EnumDecl!=null) EnumDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarEnumDeclList1_enum(\n");

        if(ConstVarEnumDeclList!=null)
            buffer.append(ConstVarEnumDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumDecl!=null)
            buffer.append(EnumDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarEnumDeclList1_enum]");
        return buffer.toString();
    }
}
