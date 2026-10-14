// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class CasePom_p extends CasePom {

    private CasePom CasePom;
    private CaseNumber CaseNumber;
    private Colon2 Colon2;
    private Statement0n Statement0n;

    public CasePom_p (CasePom CasePom, CaseNumber CaseNumber, Colon2 Colon2, Statement0n Statement0n) {
        this.CasePom=CasePom;
        if(CasePom!=null) CasePom.setParent(this);
        this.CaseNumber=CaseNumber;
        if(CaseNumber!=null) CaseNumber.setParent(this);
        this.Colon2=Colon2;
        if(Colon2!=null) Colon2.setParent(this);
        this.Statement0n=Statement0n;
        if(Statement0n!=null) Statement0n.setParent(this);
    }

    public CasePom getCasePom() {
        return CasePom;
    }

    public void setCasePom(CasePom CasePom) {
        this.CasePom=CasePom;
    }

    public CaseNumber getCaseNumber() {
        return CaseNumber;
    }

    public void setCaseNumber(CaseNumber CaseNumber) {
        this.CaseNumber=CaseNumber;
    }

    public Colon2 getColon2() {
        return Colon2;
    }

    public void setColon2(Colon2 Colon2) {
        this.Colon2=Colon2;
    }

    public Statement0n getStatement0n() {
        return Statement0n;
    }

    public void setStatement0n(Statement0n Statement0n) {
        this.Statement0n=Statement0n;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CasePom!=null) CasePom.accept(visitor);
        if(CaseNumber!=null) CaseNumber.accept(visitor);
        if(Colon2!=null) Colon2.accept(visitor);
        if(Statement0n!=null) Statement0n.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CasePom!=null) CasePom.traverseTopDown(visitor);
        if(CaseNumber!=null) CaseNumber.traverseTopDown(visitor);
        if(Colon2!=null) Colon2.traverseTopDown(visitor);
        if(Statement0n!=null) Statement0n.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CasePom!=null) CasePom.traverseBottomUp(visitor);
        if(CaseNumber!=null) CaseNumber.traverseBottomUp(visitor);
        if(Colon2!=null) Colon2.traverseBottomUp(visitor);
        if(Statement0n!=null) Statement0n.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CasePom_p(\n");

        if(CasePom!=null)
            buffer.append(CasePom.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseNumber!=null)
            buffer.append(CaseNumber.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Colon2!=null)
            buffer.append(Colon2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement0n!=null)
            buffer.append(Statement0n.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CasePom_p]");
        return buffer.toString();
    }
}
