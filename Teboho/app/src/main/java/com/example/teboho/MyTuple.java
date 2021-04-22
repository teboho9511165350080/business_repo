package com.example.teboho;

public class MyTuple {

    String item1, item2, item3, item4;

    public MyTuple (String item1, String item2)
    {
        this.item1 = item1;
        this.item2 = item2;
    }

    public MyTuple (String item1, String item2, String item3, String item4)
    {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    String getItem1() {return item1;}
    String getItem2() {return item2;}
    String getItem3() {return item3;}
    String getItem4() {return item4;}
}
