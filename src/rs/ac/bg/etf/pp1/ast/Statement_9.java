// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class Statement_9 extends Statement {

    private ForNonterm ForNonterm;
    private DesignatorStatement01 DesignatorStatement01;
    private Semicolon1 Semicolon1;
    private Condition01 Condition01;
    private DesignatorStatement01 DesignatorStatement011;
    private Rparen Rparen;
    private Statement Statement;

    public Statement_9 (ForNonterm ForNonterm, DesignatorStatement01 DesignatorStatement01, Semicolon1 Semicolon1, Condition01 Condition01, DesignatorStatement01 DesignatorStatement011, Rparen Rparen, Statement Statement) {
        this.ForNonterm=ForNonterm;
        if(ForNonterm!=null) ForNonterm.setParent(this);
        this.DesignatorStatement01=DesignatorStatement01;
        if(DesignatorStatement01!=null) DesignatorStatement01.setParent(this);
        this.Semicolon1=Semicolon1;
        if(Semicolon1!=null) Semicolon1.setParent(this);
        this.Condition01=Condition01;
        if(Condition01!=null) Condition01.setParent(this);
        this.DesignatorStatement011=DesignatorStatement011;
        if(DesignatorStatement011!=null) DesignatorStatement011.setParent(this);
        this.Rparen=Rparen;
        if(Rparen!=null) Rparen.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForNonterm getForNonterm() {
        return ForNonterm;
    }

    public void setForNonterm(ForNonterm ForNonterm) {
        this.ForNonterm=ForNonterm;
    }

    public DesignatorStatement01 getDesignatorStatement01() {
        return DesignatorStatement01;
    }

    public void setDesignatorStatement01(DesignatorStatement01 DesignatorStatement01) {
        this.DesignatorStatement01=DesignatorStatement01;
    }

    public Semicolon1 getSemicolon1() {
        return Semicolon1;
    }

    public void setSemicolon1(Semicolon1 Semicolon1) {
        this.Semicolon1=Semicolon1;
    }

    public Condition01 getCondition01() {
        return Condition01;
    }

    public void setCondition01(Condition01 Condition01) {
        this.Condition01=Condition01;
    }

    public DesignatorStatement01 getDesignatorStatement011() {
        return DesignatorStatement011;
    }

    public void setDesignatorStatement011(DesignatorStatement01 DesignatorStatement011) {
        this.DesignatorStatement011=DesignatorStatement011;
    }

    public Rparen getRparen() {
        return Rparen;
    }

    public void setRparen(Rparen Rparen) {
        this.Rparen=Rparen;
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
        if(ForNonterm!=null) ForNonterm.accept(visitor);
        if(DesignatorStatement01!=null) DesignatorStatement01.accept(visitor);
        if(Semicolon1!=null) Semicolon1.accept(visitor);
        if(Condition01!=null) Condition01.accept(visitor);
        if(DesignatorStatement011!=null) DesignatorStatement011.accept(visitor);
        if(Rparen!=null) Rparen.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForNonterm!=null) ForNonterm.traverseTopDown(visitor);
        if(DesignatorStatement01!=null) DesignatorStatement01.traverseTopDown(visitor);
        if(Semicolon1!=null) Semicolon1.traverseTopDown(visitor);
        if(Condition01!=null) Condition01.traverseTopDown(visitor);
        if(DesignatorStatement011!=null) DesignatorStatement011.traverseTopDown(visitor);
        if(Rparen!=null) Rparen.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForNonterm!=null) ForNonterm.traverseBottomUp(visitor);
        if(DesignatorStatement01!=null) DesignatorStatement01.traverseBottomUp(visitor);
        if(Semicolon1!=null) Semicolon1.traverseBottomUp(visitor);
        if(Condition01!=null) Condition01.traverseBottomUp(visitor);
        if(DesignatorStatement011!=null) DesignatorStatement011.traverseBottomUp(visitor);
        if(Rparen!=null) Rparen.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_9(\n");

        if(ForNonterm!=null)
            buffer.append(ForNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement01!=null)
            buffer.append(DesignatorStatement01.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Semicolon1!=null)
            buffer.append(Semicolon1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition01!=null)
            buffer.append(Condition01.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement011!=null)
            buffer.append(DesignatorStatement011.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Rparen!=null)
            buffer.append(Rparen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_9]");
        return buffer.toString();
    }
}
