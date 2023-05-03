package com.example.hw9attempt4;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link venueTab#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class venueTab extends Fragment implements OnMapReadyCallback {
public class venueTab extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MapView mMapView;

    private String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    public venueTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment venueTab.
     */
    // TODO: Rename and change types and number of parameters
    public static venueTab newInstance(String param1, String param2) {
        venueTab fragment = new venueTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Map code sourced from https://github.com/googlemaps/android-samples/blob/main/ApiDemos/java/app/src/gms/java/com/example/mapdemo/RawMapViewDemoActivity.java

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_venue_tab, container, false);

        EventActivity activity = (EventActivity) getActivity();
        JSONObject eventJSON = activity.eventJSON;


        TextView venueName = view.findViewById(R.id.venueName2);
        venueName.setSelected(true);

        try {
            venueName.setText(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
            venueName.setText("No name found");
        }

        TextView venueAddress = view.findViewById(R.id.venueAddress);
        venueAddress.setSelected(true);

        try {
            venueAddress.setText(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("address").getString("line1"));
        } catch (JSONException e) {
            e.printStackTrace();
            venueAddress.setText("No address found");
        }

        TextView venueCityState = view.findViewById(R.id.venueCityState);
        venueCityState.setSelected(true);

        String cityState = "";

        try {
            cityState += eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("city").getString("name");
            cityState += ", ";
        } catch (JSONException e) {
            e.printStackTrace();
            cityState += "Unknown City, ";
        }

        try {
            cityState += eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("state").getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            cityState += "Unknown State";
        }

        venueCityState.setText(cityState);

        TextView venueContact = view.findViewById(R.id.venueContact);
        venueContact.setSelected(true);

        try {
            venueContact.setText(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("boxOfficeInfo").getString("phoneNumberDetail"));
        } catch (JSONException e) {
            e.printStackTrace();
            venueContact.setText("No contact number found");
        }


        TextView venueHours = view.findViewById(R.id.venueHours);

        venueHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (venueHours.getMaxLines() == 3)
                {
                    venueHours.setMaxLines(Integer.MAX_VALUE);
                } else
                {
                    venueHours.setMaxLines(3);
                }
            }
        });

        venueHours.setSelected(true);

        try {
            venueHours.setText(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("boxOfficeInfo").getString("openHoursDetail"));
        } catch (JSONException e) {
            e.printStackTrace();
            venueHours.setText("No hours found");
        }

        TextView venueGeneralRules = view.findViewById(R.id.venueGeneralRules);
        venueGeneralRules.setSelected(true);

        venueGeneralRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (venueGeneralRules.getMaxLines() == 3)
                {
                    venueGeneralRules.setMaxLines(Integer.MAX_VALUE);
                } else
                {
                    venueGeneralRules.setMaxLines(3);
                }
            }
        });

        try {
            venueGeneralRules.setText(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("generalInfo").getString("generalRule"));
        } catch (JSONException e) {
            e.printStackTrace();
            venueGeneralRules.setText("No general rules found");
        }

        TextView venueChildRules = view.findViewById(R.id.venueChildRules);
        venueChildRules.setSelected(true);

        venueChildRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (venueChildRules.getMaxLines() == 3)
                {
                    venueChildRules.setMaxLines(Integer.MAX_VALUE);
                } else
                {
                    venueChildRules.setMaxLines(3);
                }
            }
        });

        try {
            venueChildRules.setText(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("generalInfo").getString("childRule"));
        } catch (JSONException e) {
            e.printStackTrace();
            venueChildRules.setText("No child rules found");
        }


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = view.findViewById(R.id.venueMap);
        mMapView.onCreate(mapViewBundle);

        float lat;

        try {
            lat = Float.parseFloat(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("latitude"));
        } catch (JSONException e) {
            e.printStackTrace();
            mMapView.setVisibility(MapView.GONE);
            return view;
        }

        float lon;

        try {
            lon = Float.parseFloat(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("longitude"));
        } catch (JSONException e) {
            e.printStackTrace();
            mMapView.setVisibility(MapView.GONE);
            return view;
        }

        final LatLng center = new LatLng(lat, lon);
        final MarkerOptions pin = new MarkerOptions().position(center);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 17));
                googleMap.addMarker(pin);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}