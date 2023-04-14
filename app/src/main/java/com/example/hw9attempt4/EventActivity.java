package com.example.hw9attempt4;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;

public class EventActivity extends AppCompatActivity {

    private ViewPager mViewPager;

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

        adapter.addFragment(new detailTab(), "Details");
        adapter.addFragment(new artistTab(), "Artist(s)");
        adapter.addFragment(new venueTab(), "Venue");
        viewPager.setAdapter(adapter);
    }
}