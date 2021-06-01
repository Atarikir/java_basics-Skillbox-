import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("studentsDb");

        MongoCollection<Document> collection = database.getCollection("students");
        collection.drop();

        String csvFile = "15_NoSQL/Students/src/main/resources/mongo.csv";
        collection.insertMany(parseMongoCsv(csvFile));
        System.out.println("Общее количество студентов: " + collection.countDocuments());

        BsonDocument query = BsonDocument.parse("{age: {$gt: 40}}");
        System.out.println("Количество студентов старше 40 лет: " + collection.countDocuments(query));

        query = BsonDocument.parse("{age: 1}");
        System.out.println("Имя самого молодого студента: " + Objects.requireNonNull(collection.find().sort(query)
                .limit(1).first()).get("name")
        );

        query = BsonDocument.parse("{age: -1}");
        Document oldestStudent = collection.find().sort(query).first();
        assert oldestStudent != null;
        System.out.println("Список курсов самого старого студента: " + oldestStudent.get("name") + "\nКурсы: ");
        oldestStudent.values().stream().skip(3).forEach(System.out::println);
    }

    private static List<Document> parseMongoCsv(String csvFile) {

        List<Document> listStudents = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            String defaultSeparator = ",";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(defaultSeparator, 3);
                String[] courses = columns[2].split(defaultSeparator);
                listStudents.add(new Document()
                        .append("name", columns[0])
                        .append("age", Integer.valueOf(columns[1]))
                        .append("courses", Arrays.asList(courses))
                );
            }
        } catch (FileNotFoundException e) {
            System.out.println("Wrong path to file or folder!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listStudents;
    }
}
