package com.example.mango.musicapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongsRecyclerAdapter extends RecyclerView.Adapter<SongsRecyclerAdapter.SongHolder> {

    private List<Song> allSongs = new ArrayList<>();
    private Context context;
    SongsRecyclerAdapter(List<Song> allSongs, Context context){
        this.context = context;
        this.allSongs = allSongs;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_songs, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(SongHolder holder, int position) {
        final Song currentSong = allSongs.get(position);
        String tempName = currentSong.getSongName();
        if(tempName.length() > 15)
            tempName = tempName.substring(0, 12) + "....";
        holder.songName.setText(tempName);
        Picasso.get().load(currentSong.getSongImageUrl()).into(holder.songImage);
        holder.superView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NowPlaying.class);
                i.putExtra("songName", currentSong.getSongName());
                i.putExtra("artistName", currentSong.getSongArtist());
                i.putExtra("songImageUrl", currentSong.getSongImageUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allSongs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder{

        public TextView songName;
        public ImageView songImage;
        public View superView;

        public SongHolder(View itemView) {
            super(itemView);
            superView = (View) itemView.findViewById(R.id.superView);
            songName = (TextView) itemView.findViewById(R.id.trackName);
            songImage = (ImageView) itemView.findViewById(R.id.trackImage);
        }
    }
}
