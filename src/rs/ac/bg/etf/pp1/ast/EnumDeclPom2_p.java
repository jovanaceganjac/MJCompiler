// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclPom2_p extends EnumDeclPom2 {

    private EnumDeclPom2 EnumDeclPom2;
    private String I2;
    private EnumDeclPom1 EnumDeclPom1;

    public EnumDeclPom2_p (EnumDeclPom2 EnumDeclPom2, String I2, EnumDeclPom1 EnumDeclPom1) {
        this.EnumDeclPom2=EnumDeclPom2;
        if(EnumDeclPom2!=null) EnumDeclPom2.setParent(this);
        this.I2=I2;
        this.EnumDeclPom1=EnumDeclPom1;
        if(EnumDeclPom1!=null) EnumDeclPom1.setParent(this);
    }

    public EnumDeclPom2 getEnumDeclPom2() {
        return EnumDeclPom2;
    }

    public void setEnumDeclPom2(EnumDeclPom2 EnumDeclPom2) {
        this.EnumDeclPom2=EnumDeclPom2;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public EnumDeclPom1 getEnumDeclPom1() {
        return EnumDeclPom1;
    }

    public void setEnumDeclPom1(EnumDeclPom1 EnumDeclPom1) {
        this.EnumDeclPom1=EnumDeclPom1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumDeclPom2!=null) EnumDeclPom2.accept(visitor);
        if(EnumDeclPom1!=null) EnumDeclPom1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumDeclPom2!=null) EnumDeclPom2.traverseTopDown(visitor);
        if(EnumDeclPom1!=null) EnumDeclPom1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumDeclPom2!=null) EnumDeclPom2.traverseBottomUp(visitor);
        if(EnumDeclPom1!=null) EnumDeclPom1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclPom2_p(\n");

        if(EnumDeclPom2!=null)
            buffer.append(EnumDeclPom2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(EnumDeclPom1!=null)
            buffer.append(EnumDeclPom1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDeclPom2_p]");
        return buffer.toString();
    }
}
