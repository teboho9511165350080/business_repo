package com.example.teboho;

import android.graphics.Color;

import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Colors {

    private String Flower_girl = "#f498ad";
    private String Red_plum = "#db5a6b";
    private String Moonstone = "#3aa5c1";
    private String International_orange = "#ba160c";
    private String Crystal_blue = "#68a0b0";
    private String Dark_lemon_lime = "#8bbe1b";

    Colors() {}

    String getColor(String color)
    {
        if (color.equals("Flower girl"))
            return Flower_girl;
        if (color.equals("Red plum"))
            return Red_plum;
        if (color.equals("Moonstone"))
            return Moonstone;
        if (color.equals("International orange"))
            return International_orange;
        if (color.equals("Crystal blue"))
            return Crystal_blue;
        if (color.equals("Dark lemon lime"))
            return Dark_lemon_lime;
        else return "0";
    }

    ArrayList<ILineDataSet> ServiceDataSets (ArrayList<ILineDataSet> lineDataSets)
    {
        ArrayList<ILineDataSet> serviceDataSet = new ArrayList<>();

        for (int counter = 0; counter < lineDataSets.size(); counter++)
        {
            LineDataSet copy = (LineDataSet) lineDataSets.get(counter);

            if (counter == 0)                       //2019
                copy.setColor(Color.RED);
            else if (counter == 1)                  //2020
                copy.setColor(Color.GREEN);
            else if (counter == 2)                  //2021
                copy.setColor(Color.YELLOW);
            else if (counter == 3)
                copy.setColor(Color.BLUE);
            else if (counter == 4)
                copy.setColor(Color.BLACK);
            else if (counter == 5)
                copy.setColor(Color.MAGENTA);
            else if (counter == 6)
                copy.setColor(Color.LTGRAY);
            else if (counter == 7)
                copy.setColor(Color.WHITE);
            else if (counter == 8){}
            else if (counter == 9){}
            else if (counter == 10){}               //2030

            serviceDataSet.add(copy);
        }

        return serviceDataSet;
    }
}
