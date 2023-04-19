package com.example.hw9attempt4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.util.Linkify;

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
        TextView detailArtists =  view.findViewById(R.id.venueName);
        detailArtists.setSelected(true);

        TextView detailVenue = view.findViewById(R.id.venueAddress);
        detailVenue.setSelected(true);

        TextView detailDate = view.findViewById(R.id.venueCityState);
        detailDate.setSelected(true);

        TextView detailTime = view.findViewById(R.id.venueContact);
        detailTime.setSelected(true);

        TextView detailGenres = view.findViewById(R.id.detailGenres);
        detailGenres.setSelected(true);

        TextView detailPrice = view.findViewById(R.id.detailPrice);
        detailPrice.setSelected(true);

        TextView detailStatus = view.findViewById(R.id.detailStatus);
        detailStatus.setSelected(true);

        TextView detailBuyAt = view.findViewById(R.id.detailBuyAt);
        detailBuyAt.setSelected(true);
        detailBuyAt.setSingleLine(false);
        //detailBuyAt.setMovementMethod(LinkMovementMethod.getInstance());
        //detailBuyAt.setSingleLine(true);

        Linkify.addLinks(detailBuyAt, Linkify.WEB_URLS);

        ImageView venueImg = view.findViewById(R.id.detailImage);

        // Inflate the layout for this fragment
        return view;
    }


}