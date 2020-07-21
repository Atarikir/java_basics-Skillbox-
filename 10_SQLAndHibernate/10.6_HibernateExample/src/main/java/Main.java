import entity.Course;
import entity.LinkedPurchaseList;
import entity.PurchaseList;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (SessionFactory factory = new Configuration().
                configure("hibernate.cfg.xml")
                .buildSessionFactory();
             Session session = factory.getCurrentSession()) {

            session.beginTransaction();

            fillTable(session);

            session.getTransaction().commit();
        }

//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure("hibernate.cfg.xml").build();
//        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
//        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        //Session session = sessionFactory.openSession();

//        String hql = "From " + Course.class.getSimpleName() + " Where price > 120000 Order by price desc";
//        List<Course> courseList = session.createQuery(hql).getResultList();
//
//        for(Course course : courseList) {
//            System.out.println(course.getName() + " - " + course.getPrice());
//
//        }

//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery query = builder.createQuery(Course.class);
//        Root<Course> root = query.from(Course.class);
//        query.select(root).where(builder.greaterThan(root.get("price"), 100000))
//                .orderBy(builder.desc(root.get("price")));
//        List<Course> courseList = session.createQuery(query).setMaxResults(5).getResultList();
//
//        for(Course course : courseList) {
//            System.out.println(course.getName() + " - " + course.getPrice());
//        }

//        Transaction transaction = session.beginTransaction();
//
//        Course course = session.get(Course.class, 1);
//        List<Student> studentList = course.getStudents();
//        studentList.stream().map(Student::getName).forEach(System.out::println);
//        System.out.println();
//
//        Teacher teacher = session.get(Teacher.class, 3);
//        System.out.printf("Teacher name: %s\n\n", teacher.getName());
//
//        Student student = session.get(Student.class, 2);
//        System.out.printf("Student name: %s -> date registration: %tF\n\n",
//                student.getName(), student.getRegistrationDate());
//
//        course = session.get(Course.class, 3);
//        System.out.printf("Course name: %s\n", course.getName());
//
//        transaction.commit();
        //sessionFactory.close();
    }

    public static void fillTable(Session session) {
        List<PurchaseList> purchaseLists = session.createQuery("from entity.PurchaseList").getResultList();

        for (PurchaseList var : purchaseLists) {
            DetachedCriteria studentCriteria = DetachedCriteria.forClass(Student.class)
                    .add(Restrictions.eq("name", var.getStudentName()));
            Student student = (Student) studentCriteria.getExecutableCriteria(session).list().stream().findFirst().get();

            DetachedCriteria coursesCriteria = DetachedCriteria.forClass(Course.class)
                    .add(Restrictions.eq("name", var.getCourseName()));
            Course course = (Course) coursesCriteria.getExecutableCriteria(session).list().stream()
                    .findFirst().get();
            System.out.println(course.getName());

            LinkedPurchaseList sc = new LinkedPurchaseList(
                    new LinkedPurchaseList.Key(student.getId(), course.getId()), student, course);
            session.save(sc);

        }
    }
}
