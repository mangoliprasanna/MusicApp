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

public class MyArtistActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private RecyclerView artistView;
    private List<Artist> allArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_artist);

        artistView = (RecyclerView) findViewById(R.id.artistView);
        artistView.setItemAnimator(new DefaultItemAnimator());
        artistView.setLayoutManager(new GridLayoutManager(MyArtistActivity.this, 3));

        Bundle bundle = new Bundle();
        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=" + getString(R.string.api_key) + "&format=json&limit=26");

        getSupportLoaderManager().initLoader(1, bundle, MyArtistActivity.this);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new LoadData(MyArtistActivity.this, bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        try{
            JSONObject json = new JSONObject(s);
            if(!json.isNull("artists"))
            {
                allArtists = new ArrayList<>();
                JSONArray tracks =  json.getJSONObject("artists").getJSONArray("artist");
                for(int i = 0; i < tracks.length(); i++)
                {
                    JSONObject obj = tracks.getJSONObject(i);
                    String artistName = obj.getString("name");

                    String artistImageUrl = "";

                    try {
                        artistImageUrl = obj.getJSONArray("image").getJSONObject(2).getString("#text");
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Artist temp = new Artist(artistName, "", artistImageUrl, "");
                    allArtists.add(temp);
                }
                ArtistAdapter artistAdapter = new ArtistAdapter(allArtists, MyArtistActivity.this);
                artistView.setAdapter(artistAdapter);
                artistAdapter.notifyDataSetChanged();
            }
        } catch (JSONException ex){

        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
