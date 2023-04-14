package com.example.hw9attempt4;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import androidx.core.splashscreen.SplashScreen;


public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;

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
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
//        Fragment searchTab = new searchTab();
//        Fragment favoriteTab = new favoriteTab();

        adapter.addFragment(new searchTab(), getString(R.string.searchText));
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

}