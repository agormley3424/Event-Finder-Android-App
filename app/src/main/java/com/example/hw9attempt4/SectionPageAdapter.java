package com.example.hw9attempt4;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;


import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {

    // This array list will gonna add the fragment one after another
    private final List<Fragment> mFragmentList = new ArrayList<>();
    // This list of type string is for the title of the tab
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private final FragmentManager mFragmentManager;

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void removeFragment(int i)
    {
        mFragmentList.remove(i);
        mFragmentTitleList.remove(i);
    }

    public void replaceFragment(int i , Fragment newFragment)
    {


        Fragment searchParent = mFragmentManager.getFragments().get(0);

        FragmentManager childManager = searchParent.getChildFragmentManager();


        final FragmentTransaction ft = childManager.beginTransaction();

        ft.replace(R.id.fragment_container_view, newFragment, null);
        ft.commit();


        int whatever = 2;
    }

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public int getItemPosition(Object obj)
    {
        return POSITION_NONE;
    }

}