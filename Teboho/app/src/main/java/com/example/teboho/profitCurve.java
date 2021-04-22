package com.example.teboho;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class profitCurve extends AppCompatActivity {

    private LineChart lineChart;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_curve);

        createProfitCurve();
    }

    private void createProfitCurve()
    {
        FinanceHelper financeHelper = new FinanceHelper(profitCurve.this);

         DateCapture dateCapture = new DateCapture(Integer.parseInt(getStartEndDate().getItem1()),
                 Integer.parseInt(getStartEndDate().getItem2()), Integer.parseInt(getStartEndDate().getItem3()),
                 Integer.parseInt(getStartEndDate().getItem4()));

         lineChart = findViewById(R.id.lineChart);
         lineChart.setDragEnabled(true);
         lineChart.setScaleEnabled(false);

        ArrayList<Entry> coordinates2020 = new ArrayList<>(), coordinates2021 = new ArrayList<>();
        ArrayList<Entry> coordinates2022 = new ArrayList<>(), coordinates2023 = new ArrayList<>();
        ArrayList<Entry> coordinates2024 = new ArrayList<>(), coordinates2025 = new ArrayList<>();
        ArrayList<Entry> coordinates2026 = new ArrayList<>(), coordinates2027 = new ArrayList<>();
        ArrayList<Entry> coordinates2028 = new ArrayList<>(), coordinates2029 = new ArrayList<>();
        ArrayList<Entry> coordinates2030 = new ArrayList<>(), coordinates2019 = new ArrayList<>();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

         do {
             int month = dateCapture.getStartMonth();
             int year = dateCapture.getStartYear();

             double paidDebits = Double.parseDouble(financeHelper.paidAndTotalDebits(month, year).getItem1());
             double totalDebits = Double.parseDouble(financeHelper.paidAndTotalDebits(month, year).getItem2());
             double profit = paidDebits*(1+financeHelper.getInterest()) - totalDebits;

             if (year == 2019) coordinates2019.add(new Entry(month, (float) profit));
             else if (year == 2020) coordinates2020.add(new Entry(month, (float) profit));
             else if (year == 2021) coordinates2021.add(new Entry(month, (float) profit));
             else if (year == 2022) coordinates2022.add(new Entry(month, (float) profit));
             else if (year == 2023) coordinates2023.add(new Entry(month, (float) profit));
             else if (year == 2024) coordinates2024.add(new Entry(month, (float) profit));
             else if (year == 2025) coordinates2025.add(new Entry(month, (float) profit));
             else if (year == 2026) coordinates2026.add(new Entry(month, (float) profit));
             else if (year == 2027) coordinates2027.add(new Entry(month, (float) profit));
             else if (year == 2028) coordinates2028.add(new Entry(month, (float) profit));
             else if (year == 2029) coordinates2029.add(new Entry(month, (float) profit));
             else if (year == 2030) coordinates2030.add(new Entry(month, (float) profit));

         }while (dateCapture.moveToNextMonth());

        dataSets.add(new LineDataSet(coordinates2019, "2019"));
        dataSets.add(new LineDataSet(coordinates2020, "2020"));
        dataSets.add(new LineDataSet(coordinates2021, "2021"));
        dataSets.add(new LineDataSet(coordinates2022, "2022"));
        dataSets.add(new LineDataSet(coordinates2023, "2023"));
        dataSets.add(new LineDataSet(coordinates2024, "2024"));
        dataSets.add(new LineDataSet(coordinates2025, "2025"));
        dataSets.add(new LineDataSet(coordinates2026, "2026"));
        dataSets.add(new LineDataSet(coordinates2027, "2027"));
        dataSets.add(new LineDataSet(coordinates2028, "2028"));
        dataSets.add(new LineDataSet(coordinates2029, "2029"));
        dataSets.add(new LineDataSet(coordinates2030, "2030"));

        Colors colors = new Colors();
        ArrayList<ILineDataSet> dataSetsFinal = colors.ServiceDataSets(dataSets);
        LineData data = new LineData(dataSetsFinal);
        lineChart.setData(data);
    }

    MyTuple getStartEndDate()
    {
        MyDBHandler myDBHandler = new MyDBHandler(profitCurve.this);
        Cursor cursor = myDBHandler.readAllDataDebitorRecord();
        MyTuple myTuple = null;

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String startMonth, startYear, endMonth, endYear;

            if (cursor.moveToFirst())
            {
                startMonth = cursor.getString(5);
                startYear = cursor.getString(6);

                if (cursor.moveToLast())
                {
                    endMonth = cursor.getString(5);
                    endYear = cursor.getString(6);

                    myTuple = new MyTuple(startMonth, startYear, endMonth, endYear);
                }
            }
        }
        cursor.close();
        //myDBHandler.close();

        return myTuple;
    }
}