package com.example.hw9attempt4;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class EventActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    public JSONObject eventJSON;

    private ActionBar actionBar;

    public boolean isFavorited;

    private int myPos;

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


        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);

        TabLayout.Tab detailTab = tabLayout.getTabAt(0);
        TabLayout.Tab artistTab = tabLayout.getTabAt(1);
        TabLayout.Tab venueTab = tabLayout.getTabAt(2);

        int greenColor = ContextCompat.getColor(getApplicationContext(), R.color.green);
        int whiteColor = ContextCompat.getColor(getApplicationContext(), R.color.white);

        detailTab.setIcon(R.drawable.info_icon);
        detailTab.getIcon().setColorFilter(greenColor, PorterDuff.Mode.SRC_IN);
        artistTab.setIcon(R.drawable.artist_icon);
        artistTab.getIcon().setColorFilter(whiteColor, PorterDuff.Mode.SRC_IN);
        venueTab.setIcon(R.drawable.venue_icon);
        venueTab.getIcon().setColorFilter(whiteColor, PorterDuff.Mode.SRC_IN);

        /* Sourced from https://stackoverflow.com/questions/26819235/change-android-viewpager-tab-title-programmatically */

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {


                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.green);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.green);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }
                }
        );


    }

    // Sourced from 'https://medium.com/@royanimesh2211/swipeable-tab-layout-using-view-pager-and-fragment-in-android-ea62f839502b'
    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new detailTab(), "Details");
        adapter.addFragment(new artistTab(), "Artist(s)");
        adapter.addFragment(new venueTab(), "Venue");
        viewPager.setAdapter(adapter);

        Intent i = getIntent();
        myPos = i.getExtras().getInt("position");
        isFavorited = i.getExtras().getBoolean("isFavorited");

        eventJSON = null;

        try {
            eventJSON = new JSONObject(i.getExtras().getString("JSON"));
            eventJSON = eventJSON.getJSONObject("_embedded").getJSONArray("events").getJSONObject(myPos);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            //actionBar.setTitle(eventJSON.getString("name"));
            String title = eventJSON.getString("name");
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#50C31B'>" + title + "EventFinder</font>"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        int x = 1;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.action_bar_icons, menu);

        MenuItem favoriteButton = menu.findItem(R.id.favoriteButton);



        if (isFavorited)
        {
            favoriteButton.setIcon(R.drawable.heart_filled);
        }
        else
        {
            favoriteButton.setIcon(R.drawable.heart_outline);
        }


        int i = 1;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.twitterIcon)
        {
            String eventName = null;
            String eventURL = null;
            try {
                eventName = eventJSON.getString("name");
                eventURL = eventJSON.getString("url");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "https://twitter.com/intent/tweet?text=Check%20" + eventName + "%20on%20Ticketmaster," + eventURL));
            startActivity(browserIntent);
        } else if (item.getItemId() == R.id.facebookIcon) {
            String eventURL = null;
            try {
                eventURL = eventJSON.getString("url");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" + eventURL + "&amp;src=sdkpreparse"));
            startActivity(browserIntent);
        }
        else if (item.getItemId() == R.id.favoriteButton)
        {
            isFavorited = !isFavorited;

            int id;
            View layout = findViewById(android.R.id.content);

            String eventName = null;
            try {
                eventName = eventJSON.getString("name");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            if (isFavorited)
            {
                id = R.drawable.heart_filled;
                Snackbar snackBar = Snackbar.make(layout, eventName + " added to favorites", Snackbar.LENGTH_LONG);
                View sv = snackBar.getView();
                sv.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
                snackBar.setTextColor(ContextCompat.getColor(this, R.color.darkGrey));
                snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.darkGrey));
                snackBar.show();
            }
            else
            {
                id = R.drawable.heart_outline;
                Snackbar snackBar = Snackbar.make(layout, eventName + " removed from favorites", Snackbar.LENGTH_LONG);
                View sv = snackBar.getView();
                sv.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
                snackBar.setTextColor(ContextCompat.getColor(this, R.color.darkGrey));
                snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.darkGrey));
                snackBar.show();
            }

            item.setIcon(id);

            Intent sendBack = new Intent();
            sendBack.putExtra("isFavorited", isFavorited);
            sendBack.putExtra("position", myPos);
            setResult(RESULT_OK, sendBack);
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }
}