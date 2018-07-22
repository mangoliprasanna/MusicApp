package com.example.mango.musicapp;

import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ArtistActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private SongsRecyclerAdapter trackAdapter;
    private ArtistAdapter artistAdapter;
    private RecyclerView trackView;
    private RecyclerView artistView;
    private Artist curruntArtist;
    private TextView artistAboutTitle;
    private TextView artistAboutContent;
    private TextView artistName;
    private String artistNameString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        ImageView artistImage = (ImageView) findViewById(R.id.artistImage);
        artistName = (TextView) findViewById(R.id.artistName);
        artistAboutTitle = (TextView) findViewById(R.id.artistAboutTitle);
        artistAboutContent = (TextView) findViewById(R.id.artistAboutSummary);

        trackView = (RecyclerView) findViewById(R.id.trackView);
        trackView.setItemAnimator(new DefaultItemAnimator());
        trackView.setLayoutManager(new LinearLayoutManager(ArtistActivity.this, LinearLayoutManager.HORIZONTAL, false));

        artistView = (RecyclerView) findViewById(R.id.artistView);
        artistView.setItemAnimator(new DefaultItemAnimator());
        artistView.setLayoutManager(new LinearLayoutManager(ArtistActivity.this, LinearLayoutManager.HORIZONTAL, false));


        artistNameString = getIntent().getStringExtra("artistName");
        artistName.setText(artistNameString);
        setTitle(artistNameString);
        Picasso.get().load(getIntent().getStringExtra("artistImageUrl")).into(artistImage);


        // Load all top Tracks from SERVER
        Bundle bundle = new Bundle();
        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=" + getIntent().getStringExtra("artistName") + "&api_key=" + getString(R.string.api_key) + "&format=json&limit=15");
        getSupportLoaderManager().initLoader(1,bundle, ArtistActivity.this);

        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + getIntent().getStringExtra("artistName") + "&api_key=" + getString(R.string.api_key) + "&format=json");
        getSupportLoaderManager().initLoader(2,bundle, this);

        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" + getIntent().getStringExtra("artistName") + "&api_key=" + getString(R.string.api_key) + "&format=json&limit=10");
        getSupportLoaderManager().initLoader(3,bundle, this);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {

        return new LoadData(this, bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        try
        {
            JSONObject json = new JSONObject(s);
            if(!json.isNull("toptracks"))
            {
                List<Song> allTracks = new ArrayList<>();
                JSONArray tracks = json.getJSONObject("toptracks").getJSONArray("track");
                for(int i = 0; i < tracks.length(); i++)
                {
                    JSONObject tempObj = tracks.getJSONObject(i);
                    String songName = tempObj.getString("name");
                    String artistName = tempObj.getJSONObject("artist").getString("name");
                    String songImage = tempObj.getJSONArray("image").getJSONObject(2).getString("#text");
                    allTracks.add(new Song(songName, artistName, songImage));
                }
                trackAdapter = new SongsRecyclerAdapter(allTracks, ArtistActivity.this);
                trackView.setAdapter(trackAdapter);
                trackAdapter.notifyDataSetChanged();
            }
            if(!json.isNull("artist"))
            {
                List<Artist> allArtists = new ArrayList<>();
                JSONObject obj =  json.getJSONObject("artist");
                String artistName = obj.getString("name");
                String artistUrl = obj.getString("url");
                String artistListeners = obj.getJSONObject("stats").getString("listeners");
                String artistSummary = obj.getJSONObject("bio").getString("summary");
                artistAboutTitle.setText("About " + artistName);
                artistAboutContent.setText(Html.fromHtml( artistSummary));

                DecimalFormat df = new DecimalFormat("0.00M");
                artistListeners = df.format( Double.parseDouble(artistListeners)/ 1000000).toString();
                String artistImageUrl = "";

                // If in case the image is unavalible in JSON, handeling the error.
                try {
                    artistImageUrl = obj.getJSONArray("image").getJSONObject(2).getString("#text");
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                curruntArtist = new Artist(artistName, artistImageUrl);
                curruntArtist.setArtistSummary(artistSummary);

                JSONArray similarArtist = obj.getJSONObject("similar").getJSONArray("artist");
                for(int i = 0; i < similarArtist.length(); i++)
                {
                    obj = similarArtist.getJSONObject(i);
                    artistName = obj.getString("name");
                    artistUrl = obj.getString("url");

                    try {
                        artistImageUrl = obj.getJSONArray("image").getJSONObject(2).getString("#text");
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    Artist temp = new Artist(artistName, artistImageUrl);
                    allArtists.add(temp);
                }

                artistAdapter = new ArtistAdapter(allArtists, ArtistActivity.this);
                artistView.setAdapter(artistAdapter);
                artistAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}