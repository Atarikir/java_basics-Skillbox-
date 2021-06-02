import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader {

    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Shops shops = new Shops();

        for(;;) {

            System.out.println("\nВведите команду из списка:" +
                    "\nДОБАВИТЬ_МАГАЗИН Название магазина" +
                    "\nДОБАВИТЬ_ТОВАР Название товара Цена товара цифрами" +
                    "\nВЫСТАВИТЬ_ТОВАР Название товара Название магазина" +
                    "\nСТАТИСТИКА_ТОВАРОВ" +
                    "\nВЫХОД"
            );

            try {
                String[] commandInput = bufferedReader.readLine().split(" ");
                String parseString = parseCommand(commandInput);
                String firstString = commandInput[0];

                if (firstString.equalsIgnoreCase("ДОБАВИТЬ_МАГАЗИН")) {
                    shops.addShop(parseString);
                }else if (firstString.equalsIgnoreCase("ДОБАВИТЬ_ТОВАР")) {
                    shops.addProduct(parseString);
                }else if (firstString.equalsIgnoreCase("ВЫСТАВИТЬ_ТОВАР")) {
                    shops.exhibitGoods(parseString);
                }else if (firstString.equalsIgnoreCase("СТАТИСТИКА_ТОВАРОВ")) {
                    shops.productStatistics(shops.getProducts());
                }else if (firstString.equalsIgnoreCase("ВЫХОД")) {
                    shops.exit();
                    break;
                }else System.out.println("Неверная команда!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String parseCommand(String[] strings) {
        String formatString = "";

        if (strings.length == 3) {
            formatString = strings[1] + " " + strings[2];
        }else if (strings.length == 2) {
            formatString = strings[1];
        }else formatString = "";

        return formatString;
    }
}
