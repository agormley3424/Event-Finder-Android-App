package com.example.hw9attempt4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.core.splashscreen.SplashScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private SectionPageAdapter adapter;

    //public String searchJSON;

    public JSONObject searchJSON;

    public int testValue = 2;

    private RequestQueue queue;

    public List<eventObject> favorites = new ArrayList<eventObject>();

    public ArrayList<eventObject> eventArray = new ArrayList<eventObject>();

    public SharedPreferences prefs;

    public Location userLocation = null;

    private Gson gson = new Gson();

    private favoriteTab favoriteTab;

    private SearchResults searchResults = null;

    private searchTab searchTabVar;
    public boolean hasGPS;

    private Activity myActivity = this;

    private String stringDest = null;

    private boolean requestSatisfied = false;

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

        requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            hasGPS = false;
//        } else {
//            hasGPS = true;
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
//        }


        //setTheme(R.style.Theme_HW9Attempt4);

        setContentView(R.layout.activity_main);

        prefs = getPreferences(MODE_PRIVATE);

        // set up the view pager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        this.queue = Volley.newRequestQueue(this);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        loadFavorites();
        int i = 1;

        searchTabVar = new searchTab();

        getApplicationContext();
        int x = 1;


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#50C31B'>EventFinder</font>"));
    }


    // Sourced from 'https://medium.com/@royanimesh2211/swipeable-tab-layout-using-view-pager-and-fragment-in-android-ea62f839502b'
    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionPageAdapter(getSupportFragmentManager());
//        Fragment searchTab = new searchTab();
//        Fragment favoriteTab = new favoriteTab();

        adapter.addFragment(new SearchParentFragment(), getString(R.string.searchText));

        favoriteTab = new favoriteTab();
        adapter.addFragment(favoriteTab, getString(R.string.favoriteText));
        //adapter.addFragment(new detailTab(), "Details");
        viewPager.setAdapter(adapter);
    }

    // Sourced from 'https://learntodroid.com/how-to-switch-between-activities-in-android/'
    public void viewDetails(int position) {
//        RecyclerView resultList = findViewById(R.id.resultList);
//        resultList.setVisibility(RecyclerView.INVISIBLE);
//        View resultProgress = findViewById(R.id.resultList);
//        resultProgress.setVisibility(View.VISIBLE);


        Intent switchActivityIntent = new Intent(this, EventActivity.class);
        switchActivityIntent.putExtra("position", position);
        switchActivityIntent.putExtra("JSON", searchJSON.toString());
        switchActivityIntent.putExtra("isFavorited", isFavorited(eventArray.get(position).id));
        startActivityForResult(switchActivityIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            boolean isFavoritedDetail = data.getBooleanExtra("isFavorited", false);
            int detailPos = data.getIntExtra("position", -1);
            boolean isFavoritedHere = isFavorited(eventArray.get(detailPos).id);

            // Do nothing
            if (isFavoritedDetail == isFavoritedHere) {
                return;
            }
            // Add to favorites
            else if (isFavoritedDetail) {
                addFavorite(detailPos);
            }
            // Remove from favorites
            else // if (!isFavoritedDetail)
            {
                removeFavorite(eventArray.get(detailPos).id);
            }
        }
    }

    public void viewSearchResults() {
        searchResults = new SearchResults();
        adapter.replaceFragment(0, searchResults);
    }

    // Replaces the search fragment with the results fragment
    public void showSearchResults() {
        stringDest = "https://event-finder-417906.wl.r.appspot.com/ticketMaster?";
        stringDest += "keyword=" + stringToAddress(((TextView) findViewById(R.id.keywordInput)).getText().toString());
        stringDest += "&distance=" + ((TextView) findViewById(R.id.distanceInput)).getText();
        String catString = (String) ((Spinner) findViewById(R.id.categorySpinner)).getSelectedItem();
        if (catString.equals("All")) catString = "default";
        stringDest += "&category=" + catString.toLowerCase();

        Switch autoDetect = (Switch) findViewById(R.id.autoDetect);


        if (autoDetect.isChecked()) {

            Executor newThread = Executors.newSingleThreadExecutor();
            CancellationSignal cancelSignal = new CancellationSignal();
            Consumer<Location> consumer = new Consumer<Location>() {
                @Override
                public void accept(Location location) {
                    userLocation = location;
                    requestSatisfied = true;
                }
            };

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                View layout = findViewById(android.R.id.content);
                Snackbar snackBar = Snackbar.make(layout, "Error in Auto-Detection, please enable location permission", Snackbar.LENGTH_LONG);
                View sv = snackBar.getView();
                sv.setBackgroundColor(ContextCompat.getColor(myActivity, R.color.grey));
                snackBar.setTextColor(ContextCompat.getColor(myActivity, R.color.darkGrey));
                snackBar.setActionTextColor(ContextCompat.getColor(myActivity, R.color.darkGrey));
                snackBar.show();
                return;
            }
            locationManager.getCurrentLocation(LocationManager.GPS_PROVIDER, cancelSignal, newThread, consumer);

            while (!requestSatisfied);

            requestSatisfied = false;

            if (userLocation == null)
            {
                View layout = findViewById(android.R.id.content);
                Snackbar snackBar = Snackbar.make(layout, "Error in Auto-Detection, please try manual search instead", Snackbar.LENGTH_LONG);
                View sv = snackBar.getView();
                sv.setBackgroundColor(ContextCompat.getColor(myActivity, R.color.grey));
                snackBar.setTextColor(ContextCompat.getColor(myActivity, R.color.darkGrey));
                snackBar.setActionTextColor(ContextCompat.getColor(myActivity, R.color.darkGrey));
                snackBar.show();
                return;
            }

            stringDest += "&location=" + userLocation.getLatitude() + "," + userLocation.getLongitude();
            stringDest += "&locationSearch=false";
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                //requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
//
//                return;
//            }

//            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            }
//            userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (userLocation == null)
//            {
//                userLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//            }
//

//            try {
//                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                } else {
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
//                    requestSatisfied = false;
//                }
//            } catch (java.lang.SecurityException e)
//            {
//                e.printStackTrace();
//            }


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
                        View layout = findViewById(android.R.id.content);

                        Snackbar snackBar = Snackbar.make(layout, "Error in Ticketmaster request", Snackbar.LENGTH_LONG);
                        View sv = snackBar.getView();
                        sv.setBackgroundColor(ContextCompat.getColor(myActivity, R.color.grey));
                        snackBar.setTextColor(ContextCompat.getColor(myActivity, R.color.darkGrey));
                        snackBar.setActionTextColor(ContextCompat.getColor(myActivity, R.color.darkGrey));
                        snackBar.show();

                        findViewById(R.id.searchContainerChild).setVisibility(View.VISIBLE);
                        findViewById(R.id.searchProgress).setVisibility(View.INVISIBLE);

                    }
                });

        queue.add(jsonObjectRequest);
    }

    public void updateEvents(ArrayList<eventObject> eventArray)
    {
        this.eventArray = eventArray;
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
        adapter.replaceFragment(0, searchTabVar);
//        adapter.removeFragment(searchTabVar);
//        adapter.saveState();
//        adapter.destroyItem();
    }

    //get access to location permission
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public void saveFavorites()
    {
        /* Attempt to save files using SharedPreferences*/

        /* Sourced from https://stackoverflow.com/questions/7145606/how-do-you-save-store-objects-in-sharedpreferences-on-android */

        SharedPreferences.Editor prefsEditor = prefs.edit();
        //List<eventObject> favoriteList = Arrays.asList(favorites);
        //eventObject[] simpleFavorites = (eventObject[]) favoriteList;
        String jsonFavorites = gson.toJson(favorites);
        prefsEditor.putString("favoritesList", jsonFavorites);
        prefsEditor.commit();

    }

    public void loadFavorites ()
    {
        /* Sourced from https://stackoverflow.com/questions/7145606/how-do-you-save-store-objects-in-sharedpreferences-on-android */

        String jsonFavorites = prefs.getString("favoritesList", "[]");

        TypeToken<ArrayList<eventObject>> favoriteType = new TypeToken<ArrayList<eventObject>>() {};

        favorites = (gson.fromJson(jsonFavorites, favoriteType.getType()));

        int i = 1;
    }

    public void clearFavorites()
    {
        favorites = new ArrayList<eventObject>();

        saveFavorites();

        // Not sure how to refresh adapter for this

        int i = 1;
    }

    public void addFavorite(eventObject event)
    {
        favorites.add(event);
        saveFavorites();
        favoriteTab.refreshAdd(favorites.size());
    }

    public void addFavorite(int position)
    {
        addFavorite(eventArray.get(position));
        searchResults.refreshResults(position);
    }

    public void removeFavorite(int i)
    {
        int eventPosition = favorites.get(i).pos;
        String eventID = favorites.get(i).id;

        favorites.remove(i);
        saveFavorites();
        favoriteTab.refreshDelete(i);


        if (searchResults != null && isInResults(eventID)) searchResults.refreshResults(eventPosition);
    }

    public void removeFavorite(String id)
    {
        for (int i = 0; i < favorites.size(); ++i)
        {
            if (favorites.get(i).id.equals(id))
            {
                removeFavorite(i);
                return;
            }
        }
    }

    public boolean isFavorited(String id)
    {
        for (int i = 0; i < favorites.size(); ++i)
        {
            if (favorites.get(i).id.equals(id))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isInResults(String id)
    {
        for (int i = 0; i < eventArray.size(); ++i)
        {
            if (eventArray.get(i).id.equals(id))
            {
                return true;
            }
        }

        return false;
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        // Handle new location update
//
//        if (!requestSatisfied)
//        {
//            userLocation = location;
//
//
//        }
//
//
//        int i = 1;
//    }

    public boolean hasGPS()
    {
        return !(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }


}