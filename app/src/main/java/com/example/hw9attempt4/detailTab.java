package com.example.hw9attempt4;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.util.Linkify;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detailTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detailTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public detailTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detailTab.
     */
    // TODO: Rename and change types and number of parameters
    public static detailTab newInstance(String param1, String param2) {
        detailTab fragment = new detailTab();
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

        View view = inflater.inflate(R.layout.fragment_detail_tab, container, false);
        EventActivity activity = (EventActivity) getActivity();
        JSONObject eventJSON = activity.eventJSON;


        TextView detailArtists =  view.findViewById(R.id.detailArtists);
        detailArtists.setSelected(true);

        String artistString = "";


        try {
            JSONArray artists = eventJSON.getJSONObject("_embedded").getJSONArray("attractions");

            for (int i = 0; i < artists.length(); ++i)
            {
                JSONObject artist = artists.getJSONObject(i);
                artistString += artist.getString("name");
                artistString += i < artists.length() - 1 ? " | " : "";
            }

            detailArtists.setText(artistString);
        } catch (JSONException e) {
            e.printStackTrace();
            detailArtists.setText("No artists found");
        }

        TextView detailVenue = view.findViewById(R.id.detailVenue);
        detailVenue.setSelected(true);

        try {
            detailVenue.setText(eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
            detailVenue.setText("No venue found");
        }

        TextView detailDate = view.findViewById(R.id.detailDate);
        detailDate.setSelected(true);

        try {
            detailDate.setText(eventJSON.getJSONObject("dates").getJSONObject("start").getString("localDate"));
        } catch (JSONException e) {
            e.printStackTrace();
            detailDate.setText("No dates found");
        }

        TextView detailTime = view.findViewById(R.id.detailTime);
        detailTime.setSelected(true);

        try {
            detailTime.setText(eventJSON.getJSONObject("dates").getJSONObject("start").getString("localTime"));
        } catch (JSONException e) {
            e.printStackTrace();
            detailTime.setText("No times found");
        }

        TextView detailGenres = view.findViewById(R.id.detailGenres);
        detailGenres.setSelected(true);
        //detailGenres.setText("");
        String genreText = "";

        try {
            String genre = eventJSON.getJSONArray("classifications").getJSONObject(0).getJSONObject("genre").getString("name");

            if (!genre.equals("Undefined"))
            {
                genreText += genre;
                genreText += " | ";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String segment = eventJSON.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name");
            if (!segment.equals("Undefined"))
            {
                genreText += segment;
                genreText += " | ";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String subGenre = eventJSON.getJSONArray("classifications").getJSONObject(0).getJSONObject("subGenre").getString("name");

            if (!subGenre.equals("Undefined"))
            {
                genreText += subGenre;
                genreText += " | ";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String type = eventJSON.getJSONArray("classifications").getJSONObject(0).getJSONObject("type").getString("name");
            if (!type.equals("Undefined"))
            {
                genreText += type;
                genreText += " | ";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String subType = eventJSON.getJSONArray("classifications").getJSONObject(0).getJSONObject("subType").getString("name");

            if (!subType.equals("Undefined"))
            {
                genreText += subType;
                genreText += " | ";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (genreText.equals(""))
        {
            detailGenres.setText("No genres found");
        }
        else
        {
            genreText = genreText.substring(0, genreText.length() - 3);
            detailGenres.setText(genreText);
        }



        TextView detailPrice = view.findViewById(R.id.detailPrice);
        detailPrice.setSelected(true);
        String priceText = "";

        try {
            priceText += eventJSON.getJSONArray("priceRanges").getJSONObject(0).getString("min");
            priceText += " - ";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            priceText += eventJSON.getJSONArray("priceRanges").getJSONObject(0).getString("max");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (priceText.equals(""))
        {
            detailPrice.setText("No prices found");
        }
        else
        {
            priceText += " (USD)";
            detailPrice.setText(priceText);
        }




        TextView detailStatus = view.findViewById(R.id.detailStatus);
        detailStatus.setSelected(true);

        try {
            String status = eventJSON.getJSONObject("dates").getJSONObject("status").getString("code");

            switch (status){
                case "onsale":
                    detailStatus.setText("On Sale");
                    detailStatus.setBackgroundResource(R.drawable.status_green_background);
                    break;
                case "rescheduled":
                case "postponed":
                    detailStatus.setText("Rescheduled");
                    detailStatus.setBackgroundResource(R.drawable.yellow_background);
                    break;
                case "offsale":
                    detailStatus.setText("Off Sale");
                    detailStatus.setBackgroundResource(R.drawable.red_background);
                    break;
                case "cancelled":
                    detailStatus.setText("Cancelled");
                    detailStatus.setBackgroundResource(R.drawable.black_background);
                    break;
                default:
                    detailStatus.setText("Error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView detailBuyAt = view.findViewById(R.id.detailBuyAt);
        detailBuyAt.setSelected(true);
        //detailBuyAt.setSingleLine(false);
        //detailBuyAt.setMovementMethod(LinkMovementMethod.getInstance());
        //detailBuyAt.setSingleLine(true);

        try {
            detailBuyAt.setText(eventJSON.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
            detailBuyAt.setText("No link found");
        }

        Linkify.addLinks(detailBuyAt, Linkify.WEB_URLS);


        ImageView detailImage = view.findViewById(R.id.detailImage);
        try {
            String url = eventJSON.getJSONObject("seatmap").getString("staticUrl");
            Picasso.get().load(url).into(detailImage);
        } catch (JSONException e) {
            e.printStackTrace();
            Picasso.get().load(R.drawable.no_venue).into(detailImage);
            //detailImage.setVisibility(ImageView.GONE);
        }

        //ImageView venueImg = view.findViewById(R.id.detailImage);


        // Inflate the layout for this fragment
        return view;
    }


}