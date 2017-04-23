package com.celt.translate.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> items = new ArrayList<>();
    private List<String> itemsTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addFragment(T fragment) {
        items.add(fragment);
        itemsTitle.add(null);
    }

    public void addFragment(T fragment, String title) {
        items.add(fragment);
        itemsTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return itemsTitle.get(position);
    }

    public List<T> getItems() {
        return items;
    }
}
