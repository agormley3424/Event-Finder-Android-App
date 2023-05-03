package com.example.hw9attempt4;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistRecycler extends RecyclerView.Adapter<ArtistRecycler.MyViewHolder>{
    Context context;
    ArrayList<ArtistObject> artists;

    public ArtistRecycler(Context context, ArrayList<ArtistObject> artists)
    {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_single_artist, parent, false);


        return new ArtistRecycler.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistRecycler.MyViewHolder holder, int position) {
        holder.name.setText(artists.get(position).name);
        holder.followers.setText(artists.get(position).followers);

        holder.spotifyLink.setClickable(true);
        holder.spotifyLink.setMovementMethod(LinkMovementMethod.getInstance());
        String url = "<a href='" + artists.get(position).spotifyLink + "'>Check out on Spotify </a>";
        holder.spotifyLink.setText(Html.fromHtml(url));
        



        holder.popularity.setText(artists.get(position).popularity);
        Picasso.get().load(artists.get(position).profileImage).into(holder.profile);
        Picasso.get().load(artists.get(position).album1).into(holder.album1);
        Picasso.get().load(artists.get(position).album2).into(holder.album2);
        Picasso.get().load(artists.get(position).album3).into(holder.album3);

        holder.progress.setProgress(Integer.parseInt(holder.popularity.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView followers;
        TextView spotifyLink;
        TextView popularity;
        ImageView profile;
        ImageView album1;
        ImageView album2;
        ImageView album3;

        ProgressBar progress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.artistName);
            name.setSelected(true);
            followers = itemView.findViewById(R.id.artistFollowers);
            spotifyLink = itemView.findViewById(R.id.artistLink);
            popularity = itemView.findViewById(R.id.artistRank);
            profile = itemView.findViewById(R.id.artistProfile);
            album1 = itemView.findViewById(R.id.artistAlbum1);
            album2 = itemView.findViewById(R.id.artistAlbum2);
            album3 = itemView.findViewById(R.id.artistAlbum3);

            //spotifyLink.setMovementMethod(LinkMovementMethod.getInstance());

            progress = itemView.findViewById(R.id.artistProgress);
            //Linkify.addLinks(spotifyLink, Linkify.WEB_URLS);
        }
    }
}
