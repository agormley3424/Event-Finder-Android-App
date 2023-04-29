package com.example.hw9attempt4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link artistTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class artistTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<ArtistObject> artistArray = new ArrayList<ArtistObject>();

    private EventActivity activity;

    private RequestQueue queue;

    private boolean artistFound;

    private boolean albumsFound;

    private boolean abort;

    private ArtistObject currentArtist;

    public artistTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment artistTab.
     */
    // TODO: Rename and change types and number of parameters
    public static artistTab newInstance(String param1, String param2) {
        artistTab fragment = new artistTab();
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



            eventArray.add(new eventObject(imageURL, eventName, venue, category, date, time));
        }

//        String bertieImage1 = "https://static.wikia.nocookie.net/tucabertie/images/9/9d/Bertie_Songthrush.png/revision/latest?cb=20200814011717";
//        String bertieImage2 = "https://variety.com/wp-content/uploads/2019/03/tucabertie_season1_episode1_00_00_19_03.png";
//        eventArray.add(new eventObject(bertieImage1, "SEARCH RESULTS",
//                "string3", "string4", "string5", "string6"));
//
//        eventArray.add(new eventObject(bertieImage2, "string8",
//                "string9", "string10", "string11", "string12"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artist_tab, container, false);

        activity = (EventActivity) getActivity();

        RecyclerView artistList = view.findViewById(R.id.artistList);


        ArtistRecycler adapter = new ArtistRecycler(this.getContext(), artistArray);

        artistList.setAdapter(adapter);
        artistList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        JSONObject eventJSON = activity.eventJSON;

        try {
            JSONArray artists = eventJSON.getJSONObject("_embedded").getJSONArray("attractions");

            for (int i = 0; i < artists.length(); ++i)
            {
                artistFound = false;
                albumsFound = false;
                abort = false;
                currentArtist = new ArtistObject();

                String requestString = "https://hw8-380107.wl.r.appspot.com/spotify?artist=";
                String artistName = artists.getJSONObject(i).getString("name");
                requestString += artistName;
                artistRequest(requestString, artistName);

                while (!abort && (!artistFound || !albumsFound));

                if (!abort)
                {
                    artistArray.add(currentArtist);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return view;
    }

    public void artistRequest(String url, String targetName)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray artists;
                        try {
                            artists = response.getJSONObject("artists").getJSONArray("items");
                        } catch (JSONException e){
                            e.printStackTrace();
                            return;
                        }

                        for (int i = 0; i < artists.length(); ++i)
                        {
                            String innerName;
                            try {
                                innerName = artists.getJSONObject(i).getString("name");
                            } catch (JSONException e){
                                e.printStackTrace();
                                innerName = "";
                            }

                            if (innerName.equals(targetName))
                            {
                                addArtist(response, i);
                                return;
                            }
                        }

                        abort = true;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        abort = true;
                    }
                });

        queue.add(jsonObjectRequest);
    }

    public void albumRequest(String url)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        addAlbums(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        abort = true;
                    }
                });

        queue.add(jsonObjectRequest);
    }


    // Add a new artist to the artist array
    public void addArtist(JSONObject response, int position)
    {
        JSONObject artistJSON;
        try {
            artistJSON = response.getJSONObject("artists").getJSONArray("items").getJSONObject(position);

            String albumString = "https://hw8-380107.wl.r.appspot.com/spotifyAlbums?artistID=" + artistJSON.getString("id");
            albumRequest(albumString);

            currentArtist.name = artistJSON.getString("name");
            currentArtist.followers = artistJSON.getJSONObject("followers").getString("total");
            currentArtist.popularity = artistJSON.getString("popularity");
            currentArtist.spotifyLink = artistJSON.getJSONObject("external_urls").getString("spotify");
            currentArtist.profileImage = artistJSON.getJSONArray("images").getJSONObject(0).getString("url");

            artistFound = true;

        } catch (JSONException e)
        {
            e.printStackTrace();
            abort = true;
        }
    }

    public void addAlbums(JSONObject response)
    {
        try {
            currentArtist.album1 = response.getJSONArray("items").getJSONObject(0).getJSONArray("images").getJSONObject(0).getString("url");
            currentArtist.album2 = response.getJSONArray("items").getJSONObject(0).getJSONArray("images").getJSONObject(1).getString("url");
            currentArtist.album3 = response.getJSONArray("items").getJSONObject(0).getJSONArray("images").getJSONObject(2).getString("url");

            albumsFound = true;
        } catch (JSONException e) {
            e.printStackTrace();
            abort = true;
        }
    }
}