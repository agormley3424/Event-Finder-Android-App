package com.example.hw9attempt4;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import androidx.core.splashscreen.SplashScreen;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private SectionPageAdapter adapter;

    //public String searchJSON;

    public JSONObject searchJSON;

    public int testValue = 2;

    private RequestQueue queue;

    public class MyAdapter extends SectionPageAdapter {
        static final int NUM_ITEMS = 2;
        private final FragmentManager mFragmentManager;
        private Fragment mFragmentAtPos0;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public int getItemPosition(Object object) {
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

        this.queue = Volley.newRequestQueue(this);

        requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
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
    public void viewDetails(int test) {
        Intent switchActivityIntent = new Intent(this, EventActivity.class);
        switchActivityIntent.putExtra("testInt", test);
        startActivity(switchActivityIntent);
    }

    public void viewSearchResults()
    {
        adapter.replaceFragment(0, new SearchResults());
    }

    // Replaces the search fragment with the results fragment
    public void showSearchResults() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String stringDest = "https://hw8-380107.wl.r.appspot.com/ticketMaster?";
        stringDest += "keyword=" + ((TextView) findViewById(R.id.keywordInput)).getText();
        stringDest += "&distance=" + ((TextView) findViewById(R.id.distanceInput)).getText();
        String catString = (String) ((Spinner) findViewById(R.id.categorySpinner)).getSelectedItem();
        stringDest += "&category=" + catString.toLowerCase();

        Switch autoDetect = (Switch) findViewById(R.id.autoDetect);

        if (autoDetect.isChecked()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

                return;
            }
            Location userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (userLocation == null)
            {
                userLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }

            stringDest += "&location=" + userLocation.getLongitude() + "," + userLocation.getLatitude();

            stringDest += "&locationSearch=false";
        } else {
            String rawAddress = ((TextView) findViewById(R.id.locationInput)).getText().toString();
            stringDest += "&location=" + stringToAddress(rawAddress);
            stringDest += "&locationSearch=true";
        }

        getJSON(stringDest);
        //getString(stringDest);

       // System.out.println(stringDest);
        //getSearch(stringDest);
        //System.out.println(searchJSON);
    }


    public String stringToAddress(String input) {
        String returnAddress = "";

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                returnAddress += '+';
            } else {
                returnAddress += input.charAt(i);
            }
        }

        return returnAddress;
    }

    // Code sourced from 'https://google.github.io/volley/request.html'

    public void getString(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //searchJSON = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void getJSON(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        searchJSON = response;
                        viewSearchResults();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        queue.add(jsonObjectRequest);
    }

    public void getJSONArray(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        int i = 5;

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        queue.add(jsonArrayRequest);
    }

    // Replaces the search results fragment with the search box fragment
    public void showSearchBox() {
        adapter.replaceFragment(0, new searchTab());
    }

    //get access to location permission
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


}