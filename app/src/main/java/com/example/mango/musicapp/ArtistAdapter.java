package com.example.mango.musicapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<Artist> allArtists;
    private Context context;

    public ArtistAdapter(List<Artist> allArtists, Context context) {
        this.allArtists = allArtists;
        this.context = context;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_artist, parent, false);

        return new ArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        final Artist currentArtist = allArtists.get(position);
        holder.artistName.setText(currentArtist.getArtistsName());
        Picasso.get()
                .load(currentArtist.getArtistsImageUrl())
                .into(holder.artistImage);
        holder.superView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ArtistActivity.class);
                i.putExtra("artistName", currentArtist.getArtistsName());
                i.putExtra("artistImageUrl", currentArtist.getArtistsImageUrl());
                Runtime.getRuntime().gc();
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allArtists.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder{
        public View superView;
        public ImageView artistImage;
        public TextView artistName;
        public ArtistViewHolder(View itemView) {
            super(itemView);
            superView = (View) itemView.findViewById(R.id.superView);
            artistImage = (ImageView) itemView.findViewById(R.id.artistImage);
            artistName = (TextView) itemView.findViewById(R.id.artistName);
        }
    }
}
