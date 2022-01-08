package com.wht.janatatraspo.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wht.janatatraspo.Fragment.MyEarthMoverFragment;
import com.wht.janatatraspo.Fragment.MyLoadFragment;


public class HomeTabAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public HomeTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {

            case 0:
                MyLoadFragment tab1 = new MyLoadFragment();
                return tab1;
            case 1:
                MyEarthMoverFragment tab2 = new MyEarthMoverFragment();
                return tab2;


            default:
                return null;
        }
    }


    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}