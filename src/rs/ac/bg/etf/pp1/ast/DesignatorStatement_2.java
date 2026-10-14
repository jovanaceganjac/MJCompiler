// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_2 extends DesignatorStatement {

    private Designator Designator;
    private ActPars01 ActPars01;

    public DesignatorStatement_2 (Designator Designator, ActPars01 ActPars01) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActPars01=ActPars01;
        if(ActPars01!=null) ActPars01.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActPars01 getActPars01() {
        return ActPars01;
    }

    public void setActPars01(ActPars01 ActPars01) {
        this.ActPars01=ActPars01;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ActPars01!=null) ActPars01.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActPars01!=null) ActPars01.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActPars01!=null) ActPars01.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement_2(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars01!=null)
            buffer.append(ActPars01.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_2]");
        return buffer.toString();
    }
}
