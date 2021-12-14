package ru.vsu.baryshev;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static String solution(String[] args) throws IOException {
        String str = " JT opened";
        if (args.length == 0) {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new FrameMain().setVisible(true);
                }
            });

        } else if (args[0].equals("-i") || args[2].equals("-o")) {    // Пути к файлам
            if (args.length != 5){
                System.out.println(" Wrong price");
                return null;
            }else try {
                PCTablet pc = logic.processing(InputArgs.stringArrayToListOfPCT(InputArgs.fileToStringArray(args[1]), 0), Integer.parseInt(args[args.length - 1]));
                if (pc == null) {
                    System.out.println("There isn't answer");
                } else {
                    str = pc.nameOfModel + " " + String.valueOf(pc.storage) + " " + String.valueOf(pc.rating) + " " + String.valueOf(pc.price);
                    System.out.println(str);
                    try {
                        InputArgs.saving(args[3], pc);
                    } catch (FileNotFoundException f1) {
                        System.out.println("Wrong path of writing");
                    }


                    return str;
                }
            } catch (FileNotFoundException f) {
                System.out.println("Wrong path of reading");
            }

        } else {    // Сами планшеты

            PCTablet pc = logic.processing(InputArgs.stringArrayToListOfPCT(args, 1), Integer.parseInt(args[args.length - 1]));
            if (pc != null) {
                str = pc.nameOfModel + " " + pc.storage + " " + pc.rating + " " + pc.price;
                System.out.println(str);
                return str;
            } else {
                System.out.println("There isn't answer");
            }

        }
        return str;
    }

    public static void finalTest(String[] lists, String[] io, int number, String rightAnswer) throws IOException {
        System.out.println("Тест " + number);
        System.out.print("Ответ ");
        solution(lists);
        System.out.println(" Верный ответ: " + rightAnswer);
        solution(io);
        System.out.println("Проверьте .\\\\writing" + number + ".txt");
        return;
    }

    public static void main(String[] args) throws IOException {
        String[] lists1 = {"Asus,150,3,130000", "Samsung,151,3,130000", "Nasha,150,3,130000", "Wedro,122,2,120000", "130000"};
        String[] io1 = {"-i", ".\\\\reading1.txt", "-o", ".\\\\writing1.txt", "130000"};
        String[] jt = {};
        String[] lists2 = {"Apple,150,3,130000", "Samsung,151,3,130000", "Asus,150,3,130000", "BQ,122,2,85000", "Teclast,155,3,70000", "90000"};
        String[] io2 = {"-i", ".\\reading2.txt", "-o", ".\\writing2.txt", "90000"};
        String[] lists3 = {"Asus,148,5,145000", "Samsung,148,5,145000", "150000"};
        String[] io3 = {"-i", ".\\\\reading3.txt", "-o", ".\\\\writing3.txt", "150000"};
        String[] lists4 = {"Asus,150,3,130000", "Asus,151,3,130000", "130000"};
        String[] io4 = {"-i", ".\\\\reading4.txt", "-o", ".\\\\writing4.txt", "130000"};
        String[] lists5 = {"Asus,150,3,130000", "Asus,150,3,130000", "Asus,150,3,130000", "130000"};
        String[] io5 = {"-i", ".\\\\reading5.txt", "-o", ".\\\\writing5.txt", "130000"};
//        finalTest(lists1, io1, 1, "Samsung 151 3 130000");
//        finalTest(lists2, io2, 2, "Teclast 155 3 70000");
//        finalTest(lists3, io3, 3, "Asus 148 5 145000");
//        finalTest(lists4, io4, 4, "Asus 151 3 130000");
//        finalTest(lists5, io5, 5, "3");
//        finalTest(jt, io3, 6, "Запущен JTable");


        solution(args);

    }
}
