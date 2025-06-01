package com.example.myapplication;



public class Playrs
{
    private String name;
    private String icon;

    private int level;

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Playrs(String name, String icon,int level) {
        this.name = name;
        this.icon = icon;
        this.level = level;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}

