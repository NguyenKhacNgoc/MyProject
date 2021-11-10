package com.khacngoc.myproject;

public class mActivity {
    private int id;
    private String tittle;
    private String text;

    public mActivity(int id,String tittle, String text) {
        this.tittle = tittle;
        this.text = text;
        this.id = id;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
