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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


// Class created in reference to https://www.youtube.com/watch?v=Mc0XT58A1Z4
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    Context context;
    ArrayList<eventObject> events;

    public RecyclerAdapter(Context context, ArrayList<eventObject> events)
    {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_single_result, parent, false);


        return new RecyclerAdapter.MyViewHolder(view, events);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        holder.eventName.setText(events.get(position).eventName);
        holder.eventDate.setText(events.get(position).date);
        holder.eventTime.setText(events.get(position).time);
        holder.eventVenue.setText(events.get(position).venue);
        holder.eventCategory.setText(events.get(position).category);
        Picasso.get().load(events.get(position).imageURL).into(holder.eventImage);
        holder.position = holder.getAdapterPosition();
        holder.imageURL = events.get(position).imageURL;
        holder.id = events.get(position).id;

        final MainActivity activity = (MainActivity) holder.itemView.getContext();
        ImageButton favorite = holder.favorite;

        if (activity.isFavorited(holder.id))
            {
                favorite.setImageResource(R.drawable.heart_filled);
            }
        else
            {
                favorite.setImageResource(R.drawable.heart_outline);
            }
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

        ImageButton favorite;

        String id;

        public MyViewHolder(@NonNull View itemView,ArrayList<eventObject> paramEvents) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventName = itemView.findViewById(R.id.eventName);
            eventVenue = itemView.findViewById(R.id.eventVenue);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventCategory = itemView.findViewById(R.id.eventCategory);
            favorite = itemView.findViewById(R.id.favoriteButton);

            eventCategory.setSelected(true);
            eventName.setSelected(true);
            eventVenue.setSelected(true);

            final MainActivity activity = (MainActivity) itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.viewDetails(position);
                }
            });

            // Store this object as a new favorite
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<eventObject> events = paramEvents;
                    MainActivity activity = (MainActivity) itemView.getContext();
                    activity.addFavorite(events.get(position), position);
                }
            });

            // Store this object as a new favorite
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isFavorited = activity.isFavorited(id);
                    if (isFavorited)
                    {
                        favorite.setImageResource(R.drawable.heart_outline);
                        activity.removeFavorite(id);
                    }
                    else
                    {
                        favorite.setImageResource(R.drawable.heart_filled);
                        activity.addFavorite(position);
                    }
                }
            });

//            if (activity.isFavorited(id))
//            {
//                favorite.setImageResource(R.drawable.heart_filled);
//            }
//            else
//            {
//                favorite.setImageResource(R.drawable.heart_outline);
//            }


        }
    }
}
