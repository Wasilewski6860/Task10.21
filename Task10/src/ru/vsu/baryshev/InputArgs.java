package ru.vsu.baryshev;


import java.io.*;
import java.util.*;

import static java.lang.System.out;

public class InputArgs {


    public static List<PCTablet> stringArrayToListOfPCT(String[] args,int indent){  // Создание списка планшетов(по которому будет выдаваться само решение) по строковому массиву(параметрам командной строки)
                                                   // indent - отступление сзади в строковом массиве, поскольку существует два варианта применения данного метода
                                                   // В первом случае , когда в командную строку передается сам список планшетов, а в конце параметры, по которым ищется решение
                                                   // Тогда последний элемент строкового массива -- параметры -- следует исключить из перебора строкового массива, и indent=1
                                                   // Во втором случае, при передаче путей к файлам чтения/записи, indent =0, поскольку мы работаем не со всеми String[] args, а с конкретным его элементом(куда записан путь)
                                                   // Поэтому замена indent на константу =1 в этом методе теряла бы в случае input-output последний элемент при переборе, что ведет к неверному решению
        List<PCTablet> list = new ArrayList<>();

        for (int i =0; i< args.length-indent;i++){  // Сам перебор с indent

            String[] paramsOfPCT = args[i].split(","); // Строка из входного строкового элемента(один планшет(все его параметры в одну строку))
                                                             // делится на строковый массив, содержащий каждый параметр планшета отдельно
            PCTablet pc = new PCTablet(paramsOfPCT[0],Integer.parseInt(paramsOfPCT[1]),Integer.parseInt(paramsOfPCT[2]),Integer.parseInt(paramsOfPCT[3])); // По этим параметрам создается PCTablet
            list.add(pc); // планшет добавляется в список планшетов
        }

        return list;
    }

    public static String[] fileToStringArray(String path) throws IOException {  // Считывание данных из файла в строковый массив(построчно)
        // Каждая строка массива = строке файла, и содержит информацию о отельном планшете

        File file = new File(path); // Создается File, после по нему создается сканнер
        Scanner scn = new Scanner(file);
        String[] array = new String[1];
        if (file.length() == 0) { // Проверка на пустоту файла
            out.println("File if empty");
            return null;
        } else {

            String line = scn.nextLine(); // Сканнер считывает первую строку
            List<String> list = new ArrayList<>(); // Создается список строк(это удобно , поскольку:
            // а) данные нужно занести в массив, длину которого мы не знаем, а список такую проблему не ставит
            // б) получив список с нужными значениями, не будет необходимости создавать новый сканнер и проходиться по файлу вновь(если бы мы в одном прогоне посчитали бы число строк, по нему создали бы массив и еще раз прошлись по файлу)
            list.add(line);  // Строка заносится в список

            while (scn.hasNextLine()) {    // Первый прогон, занесение данных в список
                line = scn.nextLine();
                list.add(line);
            }

            String[] helpArray = new String[list.size()]; // Создается строковый массив по длине списка

            for (int i = 0; i < list.size(); i++) {   // Из списка в массив передаются данные
                helpArray[i] = list.get(i);
            }
            array=helpArray;
        }
           return array;
    }

    public static void saving(String path, PCTablet pcTablet) throws FileNotFoundException {   // Метод для сохранения данных о планшете в файл по пути к нему

        PrintStream ps = new PrintStream(path);

        String str= pcTablet.nameOfModel+" "+ pcTablet.storage+" "+pcTablet.rating+" "+pcTablet.price;
        ps.print(str);

        out.flush();
        out.close();
    }




}
