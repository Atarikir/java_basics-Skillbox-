import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for (; ; ) {
            System.out.println("Enter the path to the folder whose size you want to receive in the format:\n" +
                    "\"C:/doc/file.txt\"");

            try {
                String input = reader.readLine();

                Path folder = Paths.get(input);
                long size = Files.walk(folder)
                        .filter(p -> p.toFile().isFile())
                        .mapToLong(p -> p.toFile().length())
                        .sum();

                String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
                int unitIndex = (int) (Math.log10(size) / 3);
                double unitValue = 1 << (unitIndex * 10);

                String readableSize = new DecimalFormat("#,##0.#")
                        .format(size / unitValue) + " " + units[unitIndex];
                System.out.println(readableSize);
            } catch (Exception ex) {
                System.out.println("Wrong path to file or folder!");
            }
        }
    }
}
