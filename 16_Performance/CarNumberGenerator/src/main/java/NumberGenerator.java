import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class NumberGenerator {

    public static String generateNumbers() throws FileNotFoundException {

        //FileOutputStream writer = new FileOutputStream("res/numbers.txt");
        PrintWriter writer = new PrintWriter("res/numbers_" + Thread.currentThread().getId() + ".txt");

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

        String list = null;

        for (int regionCode = 1; regionCode < 100; regionCode++) {
            StringBuilder builder = new StringBuilder();
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append("\n");
                        }
                    }
                }
            }
            writer.write(builder.toString());
            list = builder.toString();
        }

        writer.flush();
        writer.close();


        return list;
    }

    private static String padNumber(int number, int numberLength) {

        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;

//        StringBuilder numberStr = new StringBuilder(Integer.toString(number));
//        int padSize = numberLength - numberStr.length();
//        for (int i = 0; i < padSize; i++) {
//            numberStr.insert(0, '0');
//        }
//        return numberStr.toString();
    }
}
