package com.example.hw9attempt4;

import android.os.Bundle;

//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favoriteTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favoriteTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private ArrayList<eventObject> eventArray = new ArrayList<eventObject>();

    private List<eventObject> favorites;

    FavoriteRecycler adapter;

    public favoriteTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favoriteTab.
     */
    // TODO: Rename and change types and number of parameters
    public static favoriteTab newInstance(String param1, String param2) {
        favoriteTab fragment = new favoriteTab();
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
        View view = inflater.inflate(R.layout.fragment_favorite_tab, container, false);
        MainActivity activity = (MainActivity) getActivity();
        favorites = activity.favorites;
        ArrayList<eventObject> mainEvents = activity.eventArray;



        //createList();



        RecyclerView eventList = view.findViewById(R.id.favoriteList);

        adapter = new FavoriteRecycler(this.getContext(), favorites);

        eventList.setAdapter(adapter);
        eventList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        TextView emptyText = view.findViewById(R.id.favoriteText);

        if (favorites.size() == 0)
        {
            emptyText.setVisibility(TextView.VISIBLE);
        }
        else
        {
            emptyText.setVisibility(TextView.GONE);
        }

        // Inflate the layout for this fragment
        return view;
    }

    public void refreshAdd(int position)
    {
        adapter.notifyItemInserted(position);
        TextView emptyText = getView().findViewById(R.id.favoriteText);
        emptyText.setVisibility(TextView.GONE);
    }

    public void refreshDelete(int position)
    {
        adapter.notifyItemRemoved(position);

        for (int i = position; i < adapter.getItemCount(); ++i)
        {
            adapter.notifyItemChanged(i);
        }

        if (favorites.size() == 0)
        {
            TextView emptyText = getView().findViewById(R.id.favoriteText);
            emptyText.setVisibility(TextView.VISIBLE);
        }
    }

}