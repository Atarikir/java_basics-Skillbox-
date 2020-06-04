import entity.Course;
import entity.Student;
import entity.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        Teacher teacher = session.get(Teacher.class, 3);
        System.out.printf("Teacher name: %s\n", teacher.getName());

        Student student = session.get(Student.class, 2);
        System.out.printf("Student name: %s -> date registration: %tF\n",
                student.getName(), student.getRegistrationDate().getTime());

        Course course = session.get(Course.class, 3);
        System.out.printf("Course name: %s\n", course.getName());

        sessionFactory.close();
    }

}
