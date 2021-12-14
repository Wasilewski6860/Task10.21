package ru.vsu.baryshev;

import ru.vsu.baryshev.util.JTableUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class FrameMain extends  JFrame{
    private JTable MainTable;
    private JTable TableForResults;
    private JButton min;
    private JButton plus;
    private JTextField fieldForReading;
    private JButton manualReadingButton;
    private JButton readingWithFileChooserButton;
    private JButton manualWritingButton;
    private JButton writingWithFileChooserButton;
    private JTextField fieldForWriting;
    private JButton fileProcessingButton;
    private JTextField Errors;
    private JPanel panelMain;

    private String[][] array = new String[1][4]; // Вспомогательный массив, по которому( в случае ручного изменения) будет создаваться JTable

    public FrameMain() {
        this.setTitle("Table");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        manualReadingButton.addActionListener(new ActionListener() {  // Считывание данных из файла по пути в текстовом поле
            @Override
            public void actionPerformed(ActionEvent e) {

                String path = fieldForReading.getText(); // Из соотв.поля получается путь к файлу
                File file = new File(path);
                if (file.length() == 0) { // Проверка на пустоту файла
                    Errors.setText("File is empty");
                    return;
                } else {

                try {

                    String[] FullPCT = InputArgs.fileToStringArray(path); // Создается строковый массив(массив планшетов) по пути к файлу
                    String[][] finalPCTArray = new String[FullPCT.length][4];   // Создается двумерный массив, который в будущем будет переноситься в JTable

                    for (int i = 0; i < FullPCT.length; i++) {

                        String[] paramsOFPCT = FullPCT[i].split(","); // Из строки-планшета создается строковый массив с отдельно записанными в каждую ячейку полями планшета

                        for (int j = 0; j < paramsOFPCT.length; j++) {   // Значения paramsOFPCT переносятся  в конечный двумерный массив
                            finalPCTArray[i][j] = paramsOFPCT[j];
                        }
                    }
                    JTableUtils.writeArrayToJTable(MainTable, finalPCTArray);   // Массив отображается в MainTable - таблице, содержащей список планшетов(условие задачи),
                    // Решение будет заноситься в TableForResults

                } catch (IOException f) {
                    Errors.setText("Error with reading, please, write you path like .\\\\yourPath.txt ");
                }
            }
            }
        });
        readingWithFileChooserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser file1open = new JFileChooser(); // Создается JFileChooser

                int ret = file1open.showDialog(null, "Открыть файл");

                if (ret == JFileChooser.APPROVE_OPTION) {

                    File file = file1open.getSelectedFile();  // file получает выбранный в JFileChooser файл
                    String path = file.toString();    // Получается путь к этому файлу

                    if (file.length() == 0) { // Проверка на пустоту файла
                        Errors.setText("File is empty");
                        return;
                    } else {

                    // Далее аналогично предыдущей кнопке
                    try {
                        String[] FullPCT = InputArgs.fileToStringArray(path);
                        String[][] finalPCTArray = new String[FullPCT.length][4];

                        for (int i = 0; i < FullPCT.length; i++) {
                            String[] paramsOFPCT = FullPCT[i].split(",");
                            for (int j = 0; j < paramsOFPCT.length; j++) {
                                finalPCTArray[i][j] = paramsOFPCT[j];
                            }
                        }
                        JTableUtils.writeArrayToJTable(MainTable, finalPCTArray);
                    } catch (IOException f) {
                        Errors.setText("Error with reading, please, write you path like .\\\\yourPath.txt ");
                    }
                }
                }

            }
        });

        manualWritingButton.addActionListener(new ActionListener() {  // Запись файла вручную
            @Override
            public void actionPerformed(ActionEvent e) {

                String path = fieldForWriting.getText(); // Из поля получается путь к месту записи

                PCTablet pc = WorkWithJT.JTToPCTablet(TableForResults,0); // Считывается первая строка TableForResults -- именно туда будет записываться решение

                try {
                    PrintStream ps = new PrintStream(path); // По пути из строки создается PrintStream
                    InputArgs.saving(path,pc); // Список сохраняется

                } catch (FileNotFoundException ex) {
                    Errors.setText("Wrong path of writing ");  // Вывод сообщения об ошибке
                    return;
                }

            }
        });

        writingWithFileChooserButton.addActionListener(new ActionListener() {     // Запись данных с использованием FileChooser

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileopen = new JFileChooser(); // Создается FileChooser

                int ret = fileopen.showDialog(null, "Открыть файл");

                if (ret == JFileChooser.APPROVE_OPTION) {

                    File file = fileopen.getSelectedFile(); // file получает выбранный в FileChooser файл

                    // Далее аналогично manualWritingButton
                    String path = file.toString();
                    PCTablet pc = WorkWithJT.JTToPCTablet(TableForResults,0);

                    try {

                        PrintStream ps = new PrintStream(path);

                        if (pc!=null) {

                            InputArgs.saving(path,pc) ;
                            ps.close();
                        }else Errors.setText("Table is empty");

                    } catch (FileNotFoundException ex) {  // Сообщение об ошибке
                        Errors.setText("Wrong path of writing ");

                        return;
                    }
                }
            }
        });

        fileProcessingButton.addActionListener(new ActionListener() {  // Обработка и вывод решения

            @Override
            public void actionPerformed(ActionEvent e) {

                List<PCTablet> listOFPCTablets = new ArrayList<>();  // Создается список планшетов

                for (int i =0; i< MainTable.getRowCount(); i++){     // Список заполняется данными из MainTable

                    PCTablet pc = WorkWithJT.JTToPCTablet(MainTable,i);
                    listOFPCTablets.add(pc);
                }


                try {

                    int price = Integer.parseInt(fieldForReading.getText());  // Считывается из fieldForReading максимальная цена для выбора планшета
                    PCTablet pc = logic.processing(listOFPCTablets, price);    // Выбирается решение-планшет
                    if (pc != null){
                        String[][] array = new String[1][4];                      // Создается вспомогательный массив,который будет записан в таблицу

                    array[0][0] = pc.nameOfModel;                               //\
                    array[0][1] = String.valueOf(pc.storage);                   //  \
                    array[0][2] = String.valueOf(pc.rating);                    //    В массив заносятся значения полей планшета
                    array[0][3] = String.valueOf(pc.price);                     //  /
                    JTableUtils.writeArrayToJTable(TableForResults, array);    // /
                }else {
                        Errors.setText("You havent enough money");
                        return;
                    }
                } catch(NumberFormatException n){

                    Errors.setText("Wrong params of processing ");
                    return;

                }


            }
        });
        plus.addActionListener(new ActionListener() {  // Расширение таблицы
            @Override

            public void actionPerformed(ActionEvent e) {

                ru.vsu.baryshev.util.JTableUtils.writeArrayToJTable(MainTable, array); // JTable перезаписывается
                if (array.length!=0){String[][] arr = new String[array.length+1][array[0].length ]; // Массив, на основе которого расширяется JTable, изменяется в размерах

                array = arr;}else{
                    String[][] helpArray = new String[1][4];
                    array=helpArray;
                }
            }
        });

        min.addActionListener(new ActionListener() { // Сжатие таблицы
            @Override

            public void actionPerformed(ActionEvent e) {

                ru.vsu.baryshev.util.JTableUtils.writeArrayToJTable(MainTable, array); // JTable перезаписывается
                if(array.length-1>=0){ String[][] arr = new String[array.length-1][array[0].length ]; // Массив, на основе которого расширяется JTable, изменяется в размерах

                array = arr;}
            }
        });




    }
}
