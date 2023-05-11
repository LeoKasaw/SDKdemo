package com.example.mylibrary.yealin.meeting;

public class Item<Data> {
    private Data value;
    private String displayName;

    public static <Data> Item<Data> create(Data value, String displayName) {
        Item result = new Item();
        result.setDisplayName(displayName);
        result.setValue(value);
        return result;
    }

    public Data getValue() {
        return value;
    }

    public void setValue(Data value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
