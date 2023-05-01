package com.example.hw9attempt4;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class EventActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    public JSONObject eventJSON;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();

        // set up the view pager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



        actionBar.setDisplayHomeAsUpEnabled(true);




    }

    // Sourced from 'https://medium.com/@royanimesh2211/swipeable-tab-layout-using-view-pager-and-fragment-in-android-ea62f839502b'
    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new detailTab(), "Details");
        adapter.addFragment(new artistTab(), "Artist(s)");
        adapter.addFragment(new venueTab(), "Venue");
        viewPager.setAdapter(adapter);

        Intent i = getIntent();
        int position = i.getExtras().getInt("position");

        eventJSON = null;

        try {
            eventJSON = new JSONObject(i.getExtras().getString("JSON"));
            eventJSON = eventJSON.getJSONObject("_embedded").getJSONArray("events").getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            actionBar.setTitle(eventJSON.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        try {
//            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
//            f.setAccessible(true);
//            TextView barTitle = (TextView) f.get(toolbar);
//
//            barTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            barTitle.setFocusable(true);
//            barTitle.setFocusableInTouchMode(true);
//            barTitle.requestFocus();
//            barTitle.setSingleLine(true);
//            barTitle.setSelected(true);
//            barTitle.setMarqueeRepeatLimit(-1);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }

//        TextView title = (TextView) actionBar.getCustomView();
//
//// set the ellipsize mode to MARQUEE and make it scroll only once
//        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        title.setMarqueeRepeatLimit(1);
//// in order to start strolling, it has to be focusable and focused
//        title.setFocusable(true);
//        title.setFocusableInTouchMode(true);
//        title.requestFocus();
//
//        title.setText("TEST");

//        View v = getWindow().getDecorView();
//        int resId = getResources().getIdentifier("barID", "id", "android");
//        TextView title = (TextView) v.findViewById(resId);
//
//        // Set the ellipsize mode to MARQUEE and make it scroll only once
//        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        title.setMarqueeRepeatLimit(-1);
//
//        // In order to start strolling, it has to be focusable and focused
//        title.setFocusable(true);
//        title.setFocusableInTouchMode(true);
//        title.requestFocus();

        //View v = findViewById(R.id.twitterIcon);



        int x = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_icons, menu);

//        View twitterButton = findViewById(R.id.twitterIcon);
//        View facebookButton = findViewById(R.id.facebookIcon);
//
//        twitterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
//                startActivity(browserIntent);
//            }
//        });
        View v = findViewById(R.id.twitterIcon);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.twitterIcon)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
            startActivity(browserIntent);
        } else if (item.getItemId() == R.id.facebookIcon) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
            startActivity(browserIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        View v = findViewById(R.id.twitterIcon);

        return super.onPrepareOptionsMenu(menu);
    }



    //    void clickTwitter(View v)
//    {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
//        startActivity(browserIntent);
//    }
//
//    void clickFacebook(View v)
//    {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
//        startActivity(browserIntent);
//    }
}