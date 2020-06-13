package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "Subscriptions")
public class Subscription {

    @EmbeddedId
    private Key key;

    @Column(name = "subscription_date")
    private Calendar subscriptionDate;

    public Subscription(Key key) {
        this.key = key;
    }

    public Calendar getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Calendar subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Embeddable
    public class Key implements Serializable {

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "student_id")
        private Student student;

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "course_id")
        private Course course;

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }
    }
}
