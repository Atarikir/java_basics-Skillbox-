import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String format = "C:/folder/text.txt";

        for (; ; ) {
            System.out.println("Enter the path to the folder or file you want to copy in the format\n" + format);
            Path originalFolder = Paths.get(reader.readLine());
            System.out.println("Enter the path to the folder or file to which you want to copy in the format\n" + format);
            Path copyFolder = Paths.get(reader.readLine());


            try {
                Files.walk(originalFolder)
                        .forEach(source -> copy(source, copyFolder.resolve(originalFolder.relativize(source))));
            }catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    static void copy(Path source, Path destination) {
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
