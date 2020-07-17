package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "PurchaseList")
public class PurchaseList {

    @EmbeddedId
    Key id;

    @Column(name = "student_name", updatable = false, insertable = false)
    private String studentName;

    @Column(name = "course_name", updatable = false, insertable = false)
    private String courseName;

    @Column(name = "price")
    private int price;

    @Column(name = "subscription_date")
    private Calendar subscriptionDate;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Calendar getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Calendar subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Embeddable
    public static class Key implements Serializable {

        @Column(name = "student_name")
        private String studentName;

        @Column(name = "course_name")
        private String courseName;

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equals(studentName, key.studentName) &&
                    Objects.equals(courseName, key.courseName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentName, courseName);
        }
    }
}
