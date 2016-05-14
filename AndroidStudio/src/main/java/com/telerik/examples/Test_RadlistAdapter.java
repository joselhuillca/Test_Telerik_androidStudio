package com.telerik.examples;

/**
 * Created by macmini on 12/05/2016.
 */
public class Test_RadlistAdapter {
    private String title;
    private String Suubtitle;

    public Test_RadlistAdapter(String name, String country) {
        this.title = name;
        this.Suubtitle = country;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getCountry() {
        return Suubtitle;
    }

    public void setCountry(String country) {
        this.Suubtitle = country;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", title, Suubtitle);
    }
}