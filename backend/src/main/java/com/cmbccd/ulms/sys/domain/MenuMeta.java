package com.cmbccd.ulms.sys.domain;

public class MenuMeta {

    private String title;
    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public MenuMeta(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MenuMeta{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
