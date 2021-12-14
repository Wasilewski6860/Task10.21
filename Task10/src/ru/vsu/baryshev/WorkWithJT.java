package ru.vsu.baryshev;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithJT {

    public static PCTablet JTToPCTablet(JTable table, int row) { // Создание PCTablet по данным с JTable

        List<String> list = new ArrayList<>(); // Создается вспомогательный список, хранящий данные из JTable
                                                // Его создание имеет смысл, поскольку в цикле, перебирающем JTable, записывать значения  в PCTablet нельзя из-за разного формата полей
        for (int i = 0; i < table.getColumnCount(); i++) {
            list.add((String) table.getValueAt(row, i));     // Список заполняется
        }
        PCTablet pc = new PCTablet(list.get(0), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));  // Значениями из списка заполняется PCTablet
        return pc;
    }
}
