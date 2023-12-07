package pl.fus.lego.Entity;

import jakarta.persistence.*;

@Entity
public class Elements {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "element_id", nullable = false, length = 20)
    private String elementId;
    @Basic
    @Column(name = "part_num", nullable = false, length = 20, insertable=false, updatable=false)
    private String partNum;
    @Basic
    @Column(name = "color_id", nullable = false, length = 20, insertable=false, updatable=false)
    private String colorId;
    @Basic
    @Column(name = "design_id", nullable = true, length = 20)
    private String designId;
    @ManyToOne
    @JoinColumn(name = "part_num", referencedColumnName = "part_num", nullable = false)
    private Parts partsByPartNum;
    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "id", nullable = false)
    private Colors colorsByColorId;

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getPartNum() {
        return partNum;
    }

    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getDesignId() {
        return designId;
    }

    public void setDesignId(String designId) {
        this.designId = designId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Elements elements = (Elements) o;

        if (elementId != null ? !elementId.equals(elements.elementId) : elements.elementId != null) return false;
        if (partNum != null ? !partNum.equals(elements.partNum) : elements.partNum != null) return false;
        if (colorId != null ? !colorId.equals(elements.colorId) : elements.colorId != null) return false;
        if (designId != null ? !designId.equals(elements.designId) : elements.designId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = elementId != null ? elementId.hashCode() : 0;
        result = 31 * result + (partNum != null ? partNum.hashCode() : 0);
        result = 31 * result + (colorId != null ? colorId.hashCode() : 0);
        result = 31 * result + (designId != null ? designId.hashCode() : 0);
        return result;
    }

    public Parts getPartsByPartNum() {
        return partsByPartNum;
    }

    public void setPartsByPartNum(Parts partsByPartNum) {
        this.partsByPartNum = partsByPartNum;
    }

    public Colors getColorsByColorId() {
        return colorsByColorId;
    }

    public void setColorsByColorId(Colors colorsByColorId) {
        this.colorsByColorId = colorsByColorId;
    }
}
