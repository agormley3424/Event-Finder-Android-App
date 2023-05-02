package com.example.hw9attempt4;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
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
//        mFragmentList.remove(i);
//        mFragmentList.add(i, fragment);
//        int integer = 1;

        //mFragmentList.get(i);

        //FM.beginTransaction().remove(mFragmentList.get(i)).commit();


        final FragmentTransaction ft = mFragmentManager.beginTransaction();

        ft.replace(R.id.fragment_container_view, newFragment, null);
        //ft.remove(oldFragment);
        //delete oldFragment;
        ft.commit();

        //mFragmentManager.popBackStack();
        //mFragmentManager.getFragments();
//        Fragment toDie = mFragmentList.get(i);
//        mFragmentList.remove(i);
//        mFragmentManager.beginTransaction().remove(toDie).commit();
//        mFragmentList.add(i, newFragment);
        int whatever = 2;
    }

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);

        //mFragmentList

//        if (i == 0)
//        {
//            if (mFragmentList.get(i) == null)
//            {
//                mFragmentAtPos0 = FirstPageFragment.newInstance(new FirstPageFragmentListener()
//                {
//                    public void onSwitchToNextFragment()
//                    {
//                        mFragmentManager.beginTransaction().remove(mFragmentAtPos0).commit();
//                        mFragmentAtPos0 = NextFragment.newInstance();
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//            return mFragmentAtPos0;
//        }
//        else
//            return SecondPageFragment.newInstance();
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