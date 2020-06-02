import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox?useUnicode=true&serverTimezone=UTC";
        String user = "root";
        String pass = "testtest";

        try {
            Connection connect = DriverManager.getConnection(url, user, pass);

            Statement statement = connect.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT course_name, COUNT(MONTH(subscription_date)) / " +
                    "COUNT(DISTINCT MONTH(subscription_date)) AS average_purchases_count\n" +
                    "FROM PurchaseList\n" +
                    "GROUP BY course_name;");

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String purchaseCount = resultSet.getString("average_purchases_count");
                System.out.printf("Название курса: %s -> среднее количество покупок в месяц: %s%n", courseName, purchaseCount);
            }

            resultSet.close();
            statement.close();
            connect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
