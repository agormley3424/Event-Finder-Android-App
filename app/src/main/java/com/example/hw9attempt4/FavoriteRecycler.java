package com.example.hw9attempt4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


// Class created in reference to https://www.youtube.com/watch?v=Mc0XT58A1Z4
public class FavoriteRecycler extends RecyclerView.Adapter<FavoriteRecycler.MyViewHolder>{
    Context context;

    // A list of all the favorite events in favorited order
    List<eventObject> events;


    public FavoriteRecycler(Context context, List<eventObject> events)
    {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public FavoriteRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_single_result, parent, false);


        return new FavoriteRecycler.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecycler.MyViewHolder holder, int position) {
        MainActivity activity = (MainActivity) holder.itemView.getContext();

        //List<eventObject> mainEvents = activity.eventArray;
        //int myPos = events.get(position).pos;

        //holder.eventName.setText(events.get(position).get("eventName"));
        holder.eventName.setText(events.get(position).eventName);
        holder.eventDate.setText(events.get(position).date);
        holder.eventTime.setText(events.get(position).time);
        holder.eventVenue.setText(events.get(position).venue);
        holder.eventCategory.setText(events.get(position).category);
        Picasso.get().load(events.get(position).imageURL).into(holder.eventImage);
        holder.position = holder.getAdapterPosition();
        holder.imageURL = events.get(position).imageURL;

        ImageButton unfavorite = holder.unfavorite;

        unfavorite.setImageResource(R.drawable.heart_filled);

        unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = holder.itemView.getRootView().findViewById(android.R.id.content);
                Snackbar.make(layout, holder.eventName.getText() + " removed from favorites", Snackbar.LENGTH_LONG).show();
                unfavorite.setImageResource(R.drawable.heart_outline);
                activity.removeFavorite(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        int position;
        ImageView eventImage;
        String imageURL;
        TextView eventName;
        TextView eventVenue;
        TextView eventDate;
        TextView eventTime;
        TextView eventCategory;

        ImageButton unfavorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.eventImage);
            eventName = itemView.findViewById(R.id.eventName);
            eventVenue = itemView.findViewById(R.id.eventVenue);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventCategory = itemView.findViewById(R.id.eventCategory);
            unfavorite = itemView.findViewById(R.id.favoriteButton);

            eventCategory.setSelected(true);
            eventName.setSelected(true);
            eventVenue.setSelected(true);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) itemView.getContext();
                    activity.viewDetails(position);
                }
            });
        }
    }
}
