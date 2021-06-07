import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Concatenation {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 1 ; i < 5; i++) {

            System.out.println("start iteration " + i);
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("res/numbers_" + i + ".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

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
            }

            writer.flush();
            writer.close();

            System.out.println("finish iteration " + i);
        }
        System.out.println((System.currentTimeMillis()- start) / 1000 + " s");
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

