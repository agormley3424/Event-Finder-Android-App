package com.example.hw9attempt4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

//import android.support.v4.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> autoCompleteStrings = new ArrayList<String>();

    private ArrayAdapter<String> stringAdapter;

    private Snackbar snackBar = null;

    //private String[] testStrings = {"hello, hecko, hemlo"};

    public searchTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchTab.
     */
    // TODO: Rename and change types and number of parameters
    public static searchTab newInstance(String param1, String param2) {
        searchTab fragment = new searchTab();
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

    public void getSuggestions(String url)
    {
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        autoCompleteStrings.clear();
                        try {
                            JSONArray attractions = response.getJSONObject("_embedded").getJSONArray("attractions");
                            for (int i = 0; i < attractions.length(); ++i)
                            {
                                String artistName = attractions.getJSONObject(i).getString("name");
                                autoCompleteStrings.add(artistName);
                            }
                            resetStringAdapter();
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

        queue.add(jsonObjectRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_tab, container, false);
        Button searchButton =  view.findViewById(R.id.searchButton);
        Button clearButton =  view.findViewById(R.id.clearButton);
        AutoCompleteTextView keywordInput =  view.findViewById(R.id.keywordInput);
        EditText distanceInput = view.findViewById(R.id.distanceInput);
        EditText locationInput = view.findViewById(R.id.locationInput);
        Spinner categoryInput = view.findViewById(R.id.categorySpinner);

        Switch autoDetect = view.findViewById(R.id.autoDetect);



        stringAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, autoCompleteStrings);

        keywordInput.setAdapter(stringAdapter);

        autoDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView locationInput = (TextView) view.findViewById(R.id.locationInput);
                Switch me = (Switch) view.findViewById(R.id.autoDetect);
                MainActivity activity = (MainActivity) getActivity();

                if (!(activity.hasGPS))
                {
                    me.setChecked(false);
                    View layout = getActivity().findViewById(android.R.id.content);
                    snackBar = Snackbar.make(layout, "Please enable location permissions to use auto-detect", Snackbar.LENGTH_LONG);
                    View sv = snackBar.getView();
                    sv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey));
                    snackBar.setTextColor(ContextCompat.getColor(getContext(), R.color.darkGrey));
                    snackBar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.darkGrey));
                    snackBar.show();
                    return;
                }

                if (locationInput.getVisibility() == View.GONE){
                    locationInput.setVisibility(View.VISIBLE);
                } else
                {
                    locationInput.setVisibility(View.GONE);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("\n Fragment ID is: " + this.hashCode() +"\n");
                keywordInput.setText("");
                distanceInput.setText("");
                categoryInput.setSelection(0);
                autoDetect.setChecked(false);
                locationInput.setText("");

                snackBar.dismiss();


                //View outerLayout = view.findViewById(R.id.searchContainer);
                //View innerLayout = view.findViewById(R.id.searchContainerChild);

                //Snackbar.make(innerLayout, "Sample text", Snackbar.LENGTH_LONG).show();
            }
        });

        Context context = getContext();

        keywordInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String ticketMaster = "https://app.ticketmaster.com/discovery/v2/suggest?apikey=ZUe4QATYrGXNGmv3VGkGdAz0gC3XXeVo&keyword=" + keywordInput.getText();

                getSuggestions(ticketMaster);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keywordInput = ((TextView) view.findViewById(R.id.keywordInput)).getText().toString();
                String distanceInput = ((TextView) view.findViewById(R.id.distanceInput)).getText().toString();
                Switch autoDetect = (Switch) view.findViewById(R.id.autoDetect);
                String locationInput = ((TextView) view.findViewById(R.id.locationInput)).getText().toString();

                if (keywordInput.equals("") || distanceInput.equals("") || ((!autoDetect.isChecked()) && locationInput.equals(""))){
                    View layout = getActivity().findViewById(android.R.id.content);
                    snackBar = Snackbar.make(layout, "Please fill all fields", Snackbar.LENGTH_LONG);
                    View sv = snackBar.getView();
                    sv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey));
                    snackBar.setTextColor(ContextCompat.getColor(getContext(), R.color.darkGrey));
                    snackBar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.darkGrey));
                    snackBar.show();
                    return;
                }

                MainActivity activity = (MainActivity) getActivity();
                //activity.viewDetails();

                ConstraintLayout boxLayout = view.findViewById(R.id.searchContainerChild);
                boxLayout.setVisibility(ConstraintLayout.INVISIBLE);


                ProgressBar searchProgress = view.findViewById(R.id.searchProgress);
                searchProgress.setVisibility(ProgressBar.VISIBLE);

                activity.showSearchResults();
            }
        });

        return view;
    }

    public void resetStringAdapter()
    {
        AutoCompleteTextView keywordInput =  this.getView().findViewById(R.id.keywordInput);
        stringAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, autoCompleteStrings);

        keywordInput.setAdapter(stringAdapter);
    }

}