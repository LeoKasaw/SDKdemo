package com.example.mylibrary.yealin;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhn on 2017/10/19.
 */

public class ItemData {

    private static int mStaticIndex = 0;

    private String mName;
    private int mIndex;
    private ItemData mParent;
    private Class<?> mTargetFragment;
    private List<ItemData> mChildren = new ArrayList<>();

    private ItemData() {
        mIndex = mStaticIndex;
        mStaticIndex++;
    }

    public static synchronized ItemData create(String name){
        ItemData itemData = new ItemData();
        itemData.setName(name);
        mStaticIndex++;
        return itemData;
    }

    public String getName() {
        return mName;
    }

    public ItemData setName(String mName) {
        this.mName = mName;
        return this;
    }

    public int getIndex() {
        return mIndex;
    }

    public ItemData addChildItem(String child){
        ItemData item = create(child);
        item.setParent(this);
        mChildren.add(item);
        return this;
    }

    public<T extends Activity> ItemData addChildItem(String child, Class<T> targetFragment){
        ItemData itemData = create(child);
        itemData.setTargetActivity(targetFragment);
        itemData.setParent(this);
        mChildren.add(itemData);
        return this;
    }

    public ItemData addChildItem(ItemData child){
        child.setParent(this);
        mChildren.add(child);
        return this;
    }

    public ItemData getParent() {
        return mParent;
    }

    public void setParent(ItemData mParent) {
        this.mParent = mParent;
    }

    public<T extends Activity> ItemData setTargetActivity(Class<T> targetFragment) {
        this.mTargetFragment = targetFragment;
        return this;
    }

    public Class<?> getTargetActivity() {
        return mTargetFragment;
    }

    public List<ItemData> getChildren() {
        return mChildren;
    }
}
