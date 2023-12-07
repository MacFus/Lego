package pl.fus.lego.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class UserSetsPK implements Serializable {
    @Column(name = "user_id", nullable = false)
    @Id
    private int userId;
    @Column(name = "set_id", nullable = false)
    @Id
    private int setId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSetsPK that = (UserSetsPK) o;

        if (userId != that.userId) return false;
        if (setId != that.setId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + setId;
        return result;
    }
}
