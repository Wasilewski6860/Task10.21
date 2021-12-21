package ru.vsu.baryshev;

import java.util.List;

public class logic {

    
    public static PCTablet newProcessing(PCTablet pc1, PCTablet pc2){
        if (pc1.storage != pc2.storage)
        {
            return pc1.storage > pc2.storage ? pc1 : pc2;
        } else
        if (pc1.rating != pc2.rating)
        {
            return pc1.rating > pc2.rating ? pc1 : pc2;
        }else if(pc1.nameOfModel.equals("Asus") || pc1.nameOfModel.equals("Samsung")){
            return  pc1;
        }else if ((pc2.nameOfModel.equals("Asus") || pc2.nameOfModel.equals("Samsung"))){
            return pc2;
        }else return pc1;
    }

    public static PCTablet processing(List<PCTablet> list, int price) {  // Основной метод, возвращающий конечный результат
        PCTablet pc1 = list.get(0);

        for (int i=0; i< list.size()-1;i++){
           PCTablet helpPCT = newProcessing(pc1,list.get(i+1));
           if (helpPCT.price<=price){ pc1 = helpPCT;} else pc1 = null;


        }
        return pc1;
    }

}
