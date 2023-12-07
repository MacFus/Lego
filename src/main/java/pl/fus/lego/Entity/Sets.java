package pl.fus.lego.Entity;

import jakarta.persistence.*;

@Entity
public class Sets {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "set_num", nullable = false, length = 20)
    private String setNum;
    @Basic
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic
    @Column(name = "year", nullable = false, length = 20)
    private String year;
    @Basic
    @Column(name = "theme_id", nullable = false, length = 20)
    private String themeId;
    @Basic
    @Column(name = "num_parts", nullable = false, length = 10)
    private String numParts;
    @Basic
    @Column(name = "img_url", nullable = true, length = 255)
    private String imgUrl;

    public String getSetNum() {
        return setNum;
    }

    public void setSetNum(String setNum) {
        this.setNum = setNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getNumParts() {
        return numParts;
    }

    public void setNumParts(String numParts) {
        this.numParts = numParts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sets sets = (Sets) o;

        if (setNum != null ? !setNum.equals(sets.setNum) : sets.setNum != null) return false;
        if (name != null ? !name.equals(sets.name) : sets.name != null) return false;
        if (year != null ? !year.equals(sets.year) : sets.year != null) return false;
        if (themeId != null ? !themeId.equals(sets.themeId) : sets.themeId != null) return false;
        if (numParts != null ? !numParts.equals(sets.numParts) : sets.numParts != null) return false;
        if (imgUrl != null ? !imgUrl.equals(sets.imgUrl) : sets.imgUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = setNum != null ? setNum.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (themeId != null ? themeId.hashCode() : 0);
        result = 31 * result + (numParts != null ? numParts.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        return result;
    }
}
