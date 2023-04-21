package com.example.hw9attempt4;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import androidx.core.splashscreen.SplashScreen;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private SectionPageAdapter adapter;

//    public class MyAdapter extends SectionPageAdapter
//    {
//        static final int NUM_ITEMS = 2;
//        private final FragmentManager mFragmentManager;
//        private Fragment mFragmentAtPos0;
//
//        public MyAdapter(FragmentManager fm)
//        {
//            super(fm);
//            mFragmentManager = fm;
//        }
//
//        @Override
//        public Fragment getItem(int position)
//        {
//            if (position == 0)
//            {
//                if (mFragmentAtPos0 == null)
//                {
//                    mFragmentAtPos0 = new searchTab(new FirstPageFragmentListener()
//                    {
//                        public void onSwitchToNextFragment()
//                        {
//                            mFragmentManager.beginTransaction().remove(mFragmentAtPos0).commit();
//                            mFragmentAtPos0 = new SearchResults();
//                            notifyDataSetChanged();
//                        }
//                    });
//                }
//                return mFragmentAtPos0;
//            }
//            else
//                return new favoriteTab();
//        }
//
//        @Override
//        public int getCount()
//        {
//            return NUM_ITEMS;
//        }
//
//        @Override
//        public int getItemPosition(Object object)
//        {
//            if (object instanceof searchTab && mFragmentAtPos0 instanceof SearchResults)
//                return POSITION_NONE;
//            return POSITION_UNCHANGED;
//        }
//    }
//
//    public interface FirstPageFragmentListener
//    {
//        void onSwitchToNextFragment();
//    }

        public class MyAdapter extends SectionPageAdapter
    {
        static final int NUM_ITEMS = 2;
        private final FragmentManager mFragmentManager;
        private Fragment mFragmentAtPos0;

        public MyAdapter(FragmentManager fm)
        {
            super(fm);
            mFragmentManager = fm;
        }

        @Override
        public int getCount()
        {
            return NUM_ITEMS;
        }

        @Override
        public int getItemPosition(Object object)
        {
//            if (object instanceof searchTab && mFragmentAtPos0 instanceof SearchResults)
//                return 0;
//            return POSITION_UNCHANGED;

            return POSITION_NONE;
        }
    }

    // Sourced from 'https://medium.com/@royanimesh2211/swipeable-tab-layout-using-view-pager-and-fragment-in-android-ea62f839502b'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setTheme(R.style.Theme_HW9Attempt4);

        setContentView(R.layout.activity_main);

        // set up the view pager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    // Sourced from 'https://medium.com/@royanimesh2211/swipeable-tab-layout-using-view-pager-and-fragment-in-android-ea62f839502b'
    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionPageAdapter(getSupportFragmentManager());
//        Fragment searchTab = new searchTab();
//        Fragment favoriteTab = new favoriteTab();

        adapter.addFragment(new SearchParentFragment(), getString(R.string.searchText));
        adapter.addFragment(new favoriteTab(), getString(R.string.favoriteText));
        //adapter.addFragment(new detailTab(), "Details");
        viewPager.setAdapter(adapter);
    }

    // Sourced from 'https://learntodroid.com/how-to-switch-between-activities-in-android/'
    public void viewDetails()
    {
        Intent switchActivityIntent = new Intent(this, EventActivity.class);
        startActivity(switchActivityIntent);
    }

    // Replaces the search fragment with the results fragment
    public void showSearchResults()
    {
        //R.id.searchTab
        adapter.replaceFragment(0, new SearchResults());
        //mViewPager.getAdapter().notifyDataSetChanged();

        //adapter.addFragment(new searchTab(), "Search 2");
//        adapter.removeFragment(0);
//        mViewPager.getAdapter().notifyDataSetChanged();
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.removeAllTabs();
//        tabLayout.

        //adapter.addFragment(new SearchResults(), "Results");
        //adapter.replaceFragment(1, new SearchResults());
        //mViewPager.getAdapter().notifyDataSetChanged();

        //mViewPager.getAdapter().replaceFragment(0, new SearchResults());
//        adapter.replaceFragment(0, new SearchResults());
//        adapter.notifyDataSetChanged();
//        mViewPager.setAdapter(adapter);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);

//        mViewPager.getCurrentItem();
//        Fragment results = new SearchResults();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(mViewPager.getId(), results);
//        transaction.setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.commit();
//
//        mViewPager.getAdapter().notifyDataSetChanged();

//        transaction
//
//// Replace whatever is in the fragment_container view with this fragment
//        transaction.replace(adapter.getItem(1).getId(), SearchResults.class, null);
//
//// Commit the transaction
//        transaction.commit();
//
        //adapter.notifyDataSetChanged();
//        adapter.addFragment(new FavoritesPage(), "Search");
//        adapter.getItem(0).getId()

    }

    // Replaces the search results fragment with the search box fragment
    public void showSearchBox() {
        adapter.replaceFragment(0, new searchTab());
    }


}