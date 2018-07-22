package com.example.mango.musicapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NowPlaying extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private ImageButton playPauseBtn;
    private RecyclerView similarSongs;
    private boolean isPlaying = true;
    private boolean isFav = false;
    private TextView songsName;
    private TextView artistName;
    private ImageView songImage;
    private ImageView songFav;
    private  List<Song> allSongs;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        allSongs = new ArrayList<>();
        playPauseBtn = (ImageButton) findViewById(R.id.playButton);
        songsName = (TextView) findViewById(R.id.songsName);
        artistName = (TextView) findViewById(R.id.artistName);
        songImage = (ImageView) findViewById(R.id.trackImage);
        songFav = (ImageView) findViewById(R.id.songFav);

        allSongs.add(new Song(getIntent().getStringExtra("songName"),
                getIntent().getStringExtra("artistName"), getIntent().getStringExtra("songImageUrl")));

        songsName.setText(getIntent().getStringExtra("songName"));
        artistName.setText( " By " + getIntent().getStringExtra("artistName"));
        Picasso.get().load(getIntent().getStringExtra("songImageUrl")).into(songImage);

        similarSongs = (RecyclerView) findViewById(R.id.similarSongs);
        similarSongs.setItemAnimator(new DefaultItemAnimator());
        similarSongs.setLayoutManager(new LinearLayoutManager(NowPlaying.this, LinearLayoutManager.HORIZONTAL, false));

        Bundle bundle = new Bundle();
        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist=" + getIntent().getStringExtra("artistName")+
                "&track=" + getIntent().getStringExtra("songName")+ "&api_key=" + getString(R.string.api_key) + "&format=json&limit=15");

        getSupportLoaderManager().initLoader(1, bundle, this);
    }
    public void playPause(View view){
        // Checking if the isPLay is true for toggeling between the images.
        if(isPlaying){
            playPauseBtn.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            isPlaying = false;
        } else{
            playPauseBtn.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
            isPlaying = true;
        }
    }
    public void addFav(View view){
        if(!isFav){
            songFav.setImageResource(R.drawable.ic_favorite_black_24dp);
            isFav = true;
            Toast.makeText(NowPlaying.this, "Added to Favourite", Toast.LENGTH_SHORT).show();
        } else {
            songFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            isFav = false;
            Toast.makeText(NowPlaying.this, "Removed from Favourite", Toast.LENGTH_SHORT).show();
        }


    }

    public void nextSong(View view){
        if( i + 1 < allSongs.size()){
            Song temp = allSongs.get(++i);
            songsName.setText(temp.getSongName());
            artistName.setText( " By " + temp.getSongArtist());
            Picasso.get().load(temp.getSongImageUrl()).into(songImage);
        }
        Log.w("VALUENext", String.valueOf(i));
    }

    public void priviousSong(View view){
        if(i - 1 >= 0){
            Song temp = allSongs.get(--i);
            songsName.setText(temp.getSongName());
            artistName.setText( " By " + temp.getSongArtist());
            Picasso.get().load(temp.getSongImageUrl()).into(songImage);
        }
    }



    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new LoadData(NowPlaying.this, bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        try{
            JSONObject json = new JSONObject(s);
            if(!json.isNull("similartracks")){
                JSONArray jarray = json.getJSONObject("similartracks").getJSONArray("track");
                for(int i = 0; i < jarray.length(); i++){
                    JSONObject tempObj = jarray.getJSONObject(i);
                    String songName = tempObj.getString("name");
                    String artistName = tempObj.getJSONObject("artist").getString("name");
                    String songImage = tempObj.getJSONArray("image").getJSONObject(2).getString("#text");
                    allSongs.add(new Song(songName, artistName, songImage));
                }
                SongsRecyclerAdapter songsRecyclerAdapter = new SongsRecyclerAdapter(allSongs, NowPlaying.this);
                similarSongs.setAdapter(songsRecyclerAdapter);
                songsRecyclerAdapter.notifyDataSetChanged();
            }
        } catch (JSONException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
