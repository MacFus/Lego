package pl.fus.lego.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_sets", schema = "lego", catalog = "")
@IdClass(UserSetsPK.class)
public class UserSets {
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Id
    @Column(name = "set_id", nullable = false)
    private int setId;
    @Basic
    @Column(name = "set_count", nullable = true)
    private Integer setCount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public Integer getSetCount() {
        return setCount;
    }

    public void setSetCount(Integer setCount) {
        this.setCount = setCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSets userSets = (UserSets) o;

        if (userId != userSets.userId) return false;
        if (setId != userSets.setId) return false;
        if (setCount != null ? !setCount.equals(userSets.setCount) : userSets.setCount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + setId;
        result = 31 * result + (setCount != null ? setCount.hashCode() : 0);
        return result;
    }
}
