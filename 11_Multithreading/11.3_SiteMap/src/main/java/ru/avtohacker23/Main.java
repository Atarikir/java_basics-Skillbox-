package ru.avtohacker23;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите адрес сайта в формате: https://lenta.ru ");
        String url = sc.nextLine();

        System.out
                .println("Введите количество создаваемых потоков.");
        int numbersOfThreads = sc.nextInt();

        System.out.println("Идет парсинг сайта");
        long start = System.currentTimeMillis();
        Links links = new Links(url, url);
        String siteMap = numbersOfThreads == 0 ? new ForkJoinPool().invoke(links)
                : new ForkJoinPool(numbersOfThreads).invoke(links);

        System.out.println("Парсинг закончен");
        System.out
                .println("Время работы программы " + ((System.currentTimeMillis() - start) / 1000) + " сек.");
        writingOfFile(siteMap);
    }

    private static void writingOfFile(String map) {
        System.out.println("Записываем карту сайта в файл");
        String filePath = "siteMap.txt";

        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершена.");
    }
}