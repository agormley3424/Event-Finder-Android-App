package com.example.hw9attempt4;
//import android.support.design.widget.TabLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v4.view.ViewPager;
//import android.support.v4.app.Fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;

    // Sourced from 'https://medium.com/@royanimesh2211/swipeable-tab-layout-using-view-pager-and-fragment-in-android-ea62f839502b'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the view pager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    // Sourced from 'https://medium.com/@royanimesh2211/swipeable-tab-layout-using-view-pager-and-fragment-in-android-ea62f839502b'
    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        Fragment searchTab = new searchTab();
        Fragment favoriteTab = new favoriteTab();

        adapter.addFragment(searchTab, getString(R.string.searchText));
        adapter.addFragment(new favoriteTab(), getString(R.string.favoriteText));
        viewPager.setAdapter(adapter);
    }

}