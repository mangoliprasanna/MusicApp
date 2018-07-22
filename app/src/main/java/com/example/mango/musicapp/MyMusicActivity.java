package com.example.mango.musicapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyMusicActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private RecyclerView songsView;
    private List<Song> allSongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);

        songsView = (RecyclerView) findViewById(R.id.songsView);
        songsView.setItemAnimator(new DefaultItemAnimator());
        songsView.setLayoutManager(new GridLayoutManager( this, 3));


        // Loading top tracks from internet.
        Bundle bundle = new Bundle();
        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=" + getString(R.string.api_key) +"&format=json&limit=61");
        getSupportLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new LoadData(MyMusicActivity.this, bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        // Parsing JSON string into Object for better handeling.
        try{
            JSONObject json = new JSONObject(s);
            if(!json.isNull("tracks")){
                allSongs = new ArrayList<>();
                JSONArray jarray = json.getJSONObject("tracks").getJSONArray("track");
                for(int i = 0; i < jarray.length(); i++){
                    JSONObject tempObj = jarray.getJSONObject(i);
                    String songName = tempObj.getString("name");
                    String artistName = tempObj.getJSONObject("artist").getString("name");
                    String songImage = tempObj.getJSONArray("image").getJSONObject(2).getString("#text");
                    allSongs.add(new Song(songName, artistName, songImage));
                }
                SongsRecyclerAdapter songsRecyclerAdapter = new SongsRecyclerAdapter(allSongs, MyMusicActivity.this);
                songsView.setAdapter(songsRecyclerAdapter);
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
