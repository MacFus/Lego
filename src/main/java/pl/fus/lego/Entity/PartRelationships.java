package pl.fus.lego.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "part_relationships", schema = "lego", catalog = "")
public class PartRelationships {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "my_row_id", nullable = false)
    private Long myRowId;
    @Basic
    @Column(name = "rel_type", nullable = false, length = 20)
    private String relType;
    @Basic
    @Column(name = "child_part_num", nullable = false, length = 20, insertable=false, updatable=false)
    private String childPartNum;
    @Basic
    @Column(name = "parent_part_num", nullable = false, length = 20, insertable=false, updatable=false)
    private String parentPartNum;
    @ManyToOne
    @JoinColumn(name = "child_part_num", referencedColumnName = "part_num", nullable = false)
    private Parts partsByChildPartNum;
    @ManyToOne
    @JoinColumn(name = "parent_part_num", referencedColumnName = "part_num", nullable = false)
    private Parts partsByParentPartNum;

    public Object getMyRowId() {
        return myRowId;
    }

    public void setMyRowId(Long myRowId) {
        this.myRowId = myRowId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getChildPartNum() {
        return childPartNum;
    }

    public void setChildPartNum(String childPartNum) {
        this.childPartNum = childPartNum;
    }

    public String getParentPartNum() {
        return parentPartNum;
    }

    public void setParentPartNum(String parentPartNum) {
        this.parentPartNum = parentPartNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartRelationships that = (PartRelationships) o;

        if (myRowId != null ? !myRowId.equals(that.myRowId) : that.myRowId != null) return false;
        if (relType != null ? !relType.equals(that.relType) : that.relType != null) return false;
        if (childPartNum != null ? !childPartNum.equals(that.childPartNum) : that.childPartNum != null) return false;
        if (parentPartNum != null ? !parentPartNum.equals(that.parentPartNum) : that.parentPartNum != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = myRowId != null ? myRowId.hashCode() : 0;
        result = 31 * result + (relType != null ? relType.hashCode() : 0);
        result = 31 * result + (childPartNum != null ? childPartNum.hashCode() : 0);
        result = 31 * result + (parentPartNum != null ? parentPartNum.hashCode() : 0);
        return result;
    }

    public Parts getPartsByChildPartNum() {
        return partsByChildPartNum;
    }

    public void setPartsByChildPartNum(Parts partsByChildPartNum) {
        this.partsByChildPartNum = partsByChildPartNum;
    }

    public Parts getPartsByParentPartNum() {
        return partsByParentPartNum;
    }

    public void setPartsByParentPartNum(Parts partsByParentPartNum) {
        this.partsByParentPartNum = partsByParentPartNum;
    }
}
