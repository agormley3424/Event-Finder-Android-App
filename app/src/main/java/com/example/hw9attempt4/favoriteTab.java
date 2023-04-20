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

import java.util.ArrayList;

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

    private ArrayList<eventObject> eventArray = new ArrayList<eventObject>();

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

    public void createList()
    {
        String bertieImage1 = "https://static.wikia.nocookie.net/tucabertie/images/9/9d/Bertie_Songthrush.png/revision/latest?cb=20200814011717";
        String bertieImage2 = "https://variety.com/wp-content/uploads/2019/03/tucabertie_season1_episode1_00_00_19_03.png";
        eventArray.add(new eventObject(bertieImage1, "string2",
                "string3", "string4", "string5", "string6"));

        eventArray.add(new eventObject(bertieImage2, "string8",
                "string9", "string10", "string11", "string12"));
    }

    public eventObject dynamicAdd()
    {
        String bertieImage = "https://media.cdn.adultswim.com/uploads/20220829/thumbnails/2_228291541119-TucaAndBertie_310_TheMole.png";
        return new eventObject(bertieImage, "stringA",
                "stringB", "stringC", "stringD", "stringE");
    }

    // Class defined in reference to https://www.youtube.com/watch?v=Mc0XT58A1Z4
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tab, container, false);

        RecyclerView eventList = view.findViewById(R.id.favoriteList);

        createList();

        RecyclerAdapter adapter = new RecyclerAdapter(this.getContext(), eventArray);

        eventList.setAdapter(adapter);
        eventList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        eventArray.add(2, dynamicAdd());

        adapter.notifyItemInserted(2);

        eventArray.remove(2);

        adapter.notifyItemRemoved(2);

        // Inflate the layout for this fragment
        return view;
    }

    public void removeFavorite(int index)
    {
        eventArray.remove(index);
    }
}