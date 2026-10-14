// generated with ast extension for cup
// version 0.8
// 8/2/2026 2:49:1


package rs.ac.bg.etf.pp1.ast;

public class Designator_2 extends Designator {

    private DesignatorArrayName DesignatorArrayName;
    private IndexLength IndexLength;

    public Designator_2 (DesignatorArrayName DesignatorArrayName, IndexLength IndexLength) {
        this.DesignatorArrayName=DesignatorArrayName;
        if(DesignatorArrayName!=null) DesignatorArrayName.setParent(this);
        this.IndexLength=IndexLength;
        if(IndexLength!=null) IndexLength.setParent(this);
    }

    public DesignatorArrayName getDesignatorArrayName() {
        return DesignatorArrayName;
    }

    public void setDesignatorArrayName(DesignatorArrayName DesignatorArrayName) {
        this.DesignatorArrayName=DesignatorArrayName;
    }

    public IndexLength getIndexLength() {
        return IndexLength;
    }

    public void setIndexLength(IndexLength IndexLength) {
        this.IndexLength=IndexLength;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArrayName!=null) DesignatorArrayName.accept(visitor);
        if(IndexLength!=null) IndexLength.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArrayName!=null) DesignatorArrayName.traverseTopDown(visitor);
        if(IndexLength!=null) IndexLength.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArrayName!=null) DesignatorArrayName.traverseBottomUp(visitor);
        if(IndexLength!=null) IndexLength.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator_2(\n");

        if(DesignatorArrayName!=null)
            buffer.append(DesignatorArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IndexLength!=null)
            buffer.append(IndexLength.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator_2]");
        return buffer.toString();
    }
}
