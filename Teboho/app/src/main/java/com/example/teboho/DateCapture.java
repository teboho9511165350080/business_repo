package com.example.teboho;

import java.util.Calendar;
import java.util.Vector;

public class
 DateCapture {

    private int currentDay, currentMonth, currentYear;
    private int payDay, payMonth, payYear;
    private int bridgedMonths;
    private int startMonth, endMonth, startYear, endYear;

    public DateCapture() {
        Calendar today = Calendar.getInstance();
        currentDay = today.get(Calendar.DATE);
        currentMonth = today.get(Calendar.MONTH)+1;
        currentYear = today.get(Calendar.YEAR);
        payDate();
    }

    public DateCapture (int startMonth, int startYear, int endMonth, int endYear)
    {
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }

    public int getPayDay() {return payDay;}
    public int getPayMonth() {return payMonth;}
    public int getPayYear() {return payYear;}
    public int getCurrentDay() {return currentDay;}
    public int getCurrentMonth() {return currentMonth;}
    public int getCurrentYear() {return currentYear;}
    public int getBridgedMonths() {return bridgedMonths;}
    public int getStartMonth() {return startMonth;}
    public int getStartYear() {return startYear;}
    public int getEndMonth() {return endMonth;}
    public int getEndYear() {return endYear;}

    String stringed_month(int month)
    {
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month];
    }

    public boolean moveToNextMonth()
    {
        if ((startMonth + 1) == 13)
        {
            if ((startYear+1) <= endYear)
            {
                startMonth = 1;
                startYear++;
                return true;
            }
            else return false;
        }
        else if (((startMonth + 1) <= endMonth))
        {
            if (startYear <= endYear)
            {
                startMonth++;
                return true;
            }
            else return false;
        }
        else if (((startMonth + 1) > endMonth))
        {
            if (startYear+1 <= endYear)
            {
                startMonth++;
                return true;
            }
            else return false;
        }
        else return false;
    }


    void payDate()
    {
        payDay = currentDay;

        if (currentMonth == 12)
        {
            payMonth = 1;
            payYear = currentYear+1;
        }
        else
        {
            payMonth = currentMonth+1;
            payYear = currentYear;
        }
    }

    boolean isPayDayBridged(int d, int m, int y)
    {
        bridgedMonths = 12*(getCurrentYear()-y)-m+getCurrentMonth();

        if (getCurrentYear() > y)
        {
            if (getCurrentDay() > d)
                return true;
            else
            {
                if ((12*(getCurrentYear()-y)-m+getCurrentMonth())>=2)
                    return true;
                else
                    return false;
            }
        }
        else if (getCurrentYear() == y)
        {
            if (getCurrentMonth() > m)
            {
                if (getCurrentDay() > d)
                    return true;
                else
                {
                    if (getCurrentMonth()-m>=2)
                        return true;
                    else
                        return false;
                }
            }
            else
                return false;
        }
        else
            return false;
    }
}



