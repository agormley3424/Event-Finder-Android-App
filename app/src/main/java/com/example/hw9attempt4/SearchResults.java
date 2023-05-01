package com.example.hw9attempt4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResults#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResults extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<eventObject> eventArray = new ArrayList<eventObject>();

    private MainActivity activity;

    public SearchResults() {
        // Required empty public constructor
    }

    public void createList(JSONObject resultJSON) throws JSONException {
        // Select a JSONObject obj from an (unordered) JSONObject: JSONObject.getJSONObject("obj")
        // Select a JSONArray obj from an (unordered) JSONObject: JSONObject.getJSONArray("obc");
        // Select a String obj from an (unordered) JSONObject: JSONObject.getString("obj");
        // Select the 5th JSONObject from an (ordered) JSONArray: JSONArray.getJSONObject(5);
        // Select the 5th JSONArray from an (ordered) JSONArray: JSONArray.getJSONArray(5);
        // Select the 5th String from an (ordered) JSONArray: JSONArray.getString(5);

        JSONObject obj = resultJSON.getJSONObject("_embedded");
        JSONArray eventList = resultJSON.getJSONObject("_embedded").getJSONArray("events");

        for (int i = 0; i < eventList.length(); ++i)
        {
            JSONObject detailRow = eventList.getJSONObject(i);

            String imageURL = null;
            try {
                imageURL = detailRow.getJSONArray("images").getJSONObject(0).getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String eventName = null;
            try {
                eventName = detailRow.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String venue = null;
            try {
                venue = detailRow.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String category = null;
            try {
                category = detailRow.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String date = null;
            try {
                date = detailRow.getJSONObject("dates").getJSONObject("start").getString("localDate");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String time = null;
            try {
                time = detailRow.getJSONObject("dates").getJSONObject("start").getString("localTime");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String id = null;
            try {
                id = detailRow.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            eventArray.add(new eventObject(imageURL, eventName, venue, category, date, time, id));
        }

//        String bertieImage1 = "https://static.wikia.nocookie.net/tucabertie/images/9/9d/Bertie_Songthrush.png/revision/latest?cb=20200814011717";
//        String bertieImage2 = "https://variety.com/wp-content/uploads/2019/03/tucabertie_season1_episode1_00_00_19_03.png";
//        eventArray.add(new eventObject(bertieImage1, "SEARCH RESULTS",
//                "string3", "string4", "string5", "string6"));
//
//        eventArray.add(new eventObject(bertieImage2, "string8",
//                "string9", "string10", "string11", "string12"));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchResults.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResults newInstance(String param1, String param2) {
        SearchResults fragment = new SearchResults();
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

    // Class defined in reference to https://www.youtube.com/watch?v=Mc0XT58A1Z4
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        activity = (MainActivity) getActivity();

        RecyclerView eventList = view.findViewById(R.id.resultList);

        try {
            createList(activity.searchJSON);
        } catch (JSONException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }



        activity.updateEvents(this.eventArray);

        RecyclerAdapter adapter = new RecyclerAdapter(this.getContext(), eventArray);

        eventList.setAdapter(adapter);
        eventList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        Button backButton =  view.findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showSearchBox();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}