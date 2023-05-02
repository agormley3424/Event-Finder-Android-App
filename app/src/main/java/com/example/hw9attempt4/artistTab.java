package com.example.hw9attempt4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

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

    //private RequestQueue queue;

    private boolean artistFound;

    private boolean albumsFound;

    private boolean abort;

    private ArtistObject newArtist;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artist_tab, container, false);

        activity = (EventActivity) getActivity();

        RecyclerView artistList = view.findViewById(R.id.artistList);

        JSONObject eventJSON = activity.eventJSON;

        String segment1 = "nothing";

        try {
            segment1 = eventJSON.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        String segment2 = "nothing";

        try {
            segment1 = eventJSON.getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(0).getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        TextView noArtist = view.findViewById(R.id.noArtist);

        if ((!segment1.equals("Music")) && (!segment2.equals("Music")))
        {
            noArtist.setVisibility(TextView.VISIBLE);

            return view;
        }
        else
        {
            noArtist.setVisibility(TextView.GONE);
        }

        //this.queue = Volley.newRequestQueue(this.getContext());


        try {
            JSONArray artists = eventJSON.getJSONObject("_embedded").getJSONArray("attractions");

            for (int i = 0; i < artists.length(); ++i)
            {
                artistFound = false;
                albumsFound = false;
                abort = false;
                //currentArtist = new ArtistObject();

                final String artistName = artists.getJSONObject(i).getString("name");
                final String requestString = "https://hw8-380107.wl.r.appspot.com/spotify?artist=" + artistName;


                Thread thread = new Thread(() -> {
                    RequestQueue queue = Volley.newRequestQueue(this.getContext());
                    artistRequest(requestString, artistName, new ArtistObject(), queue, artistList);
                    //multiThreadRequest(requestString, artistName, new ArtistObject(), queue);
                });

                thread.start();






                //while (!albumsFound);

                int x = 1;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        ArtistRecycler adapter = new ArtistRecycler(this.getContext(), artistArray);
//
//        artistList.setAdapter(adapter);
//        artistList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Inflate the layout for this fragment
        return view;
    }

    public void artistRequest(String url, String targetName, ArtistObject newArtist, RequestQueue queue, RecyclerView artistList)
    {
        int i = 1;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

//                    @Override
//                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                        try {
//                            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//                            JSONObject jsonObject = new JSONObject(jsonString);
//                            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
//                        } catch (JSONException | UnsupportedEncodingException e) {
//                            return Response.error(new ParseError(e));
//                        }
//                    }


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
                                addArtist(response, i, newArtist, queue, artistList);
                                return;
                            }
                        }

                        TextView noArtist = getView().findViewById(R.id.noArtist);

                        noArtist.setVisibility(TextView.VISIBLE);

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

    public void albumRequest(String url, ArtistObject newArtist, RequestQueue queue, RecyclerView artistList)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        addAlbums(response, newArtist, artistList);
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
    public void addArtist(JSONObject response, int position, ArtistObject newArtist, RequestQueue queue, RecyclerView artistList)
    {
        JSONObject artistJSON;
        try {
            artistJSON = response.getJSONObject("artists").getJSONArray("items").getJSONObject(position);

            String albumString = "https://hw8-380107.wl.r.appspot.com/spotifyAlbums?artistID=" + artistJSON.getString("id");

            newArtist.name = artistJSON.getString("name");
            newArtist.followers = artistJSON.getJSONObject("followers").getString("total");
            newArtist.popularity = artistJSON.getString("popularity");
            newArtist.spotifyLink = artistJSON.getJSONObject("external_urls").getString("spotify");
            newArtist.profileImage = artistJSON.getJSONArray("images").getJSONObject(0).getString("url");

            albumRequest(albumString, newArtist, queue, artistList);

            artistFound = true;

        } catch (JSONException e)
        {
            e.printStackTrace();
            abort = true;
        }
    }

    public void addAlbums(JSONObject response, ArtistObject newArtist, RecyclerView artistList)
    {
        try {
            newArtist.album1 = response.getJSONArray("items").getJSONObject(0).getJSONArray("images").getJSONObject(0).getString("url");
            newArtist.album2 = response.getJSONArray("items").getJSONObject(1).getJSONArray("images").getJSONObject(0).getString("url");
            newArtist.album3 = response.getJSONArray("items").getJSONObject(2).getJSONArray("images").getJSONObject(0).getString("url");

            artistArray.add(newArtist);


            albumsFound = true;

            ArtistRecycler adapter = new ArtistRecycler(this.getContext(), artistArray);

            artistList.setAdapter(adapter);
            artistList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
            abort = true;
        }
    }

//    public void multiThreadRequest(String url, String targetName, ArtistObject newArtist, RequestQueue queue) {
//        Runnable blockingRequest = new Runnable() {
//
//            @Override
//            public void run() {
//                RequestFuture<JSONObject> future = RequestFuture.newFuture();
//                JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
//                queue.add(request);
//
//                try {
//                    JSONObject response = future.get(); // this will block
//                    int i = 1;
//                    System.out.println("Hello asynchronous world!");
//                } catch (InterruptedException e) {
//                    // exception handling
//                } catch (ExecutionException e) {
//                    // exception handling
//                }
//            }
//        };
//    }

//    public void multiThreadRequest(String url, String targetName, ArtistObject newArtist, RequestQueue queue)
//    {
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
//        queue.add(request);
//
//        try {
//            JSONObject response = future.get();
//            System.out.println("Hello asynchronous world!");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
}