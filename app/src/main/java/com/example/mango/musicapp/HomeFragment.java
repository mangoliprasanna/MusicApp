package com.example.mango.musicapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private RecyclerView tradingSongs;
    private RecyclerView artistView;
    private RecyclerView recommendSongs;
    private RecyclerView historySongs;
    private List<Song> allSongs;
    private View view;
    public HomeFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tradingSongs = (RecyclerView) view.findViewById(R.id.tradingSongs);
        artistView = (RecyclerView) view.findViewById(R.id.artistView);
        recommendSongs = (RecyclerView) view.findViewById(R.id.recommendSongs);
        historySongs = (RecyclerView) view.findViewById(R.id.historySongs);

        tradingSongs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tradingSongs.setItemAnimator(new DefaultItemAnimator());

        artistView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        artistView.setItemAnimator(new DefaultItemAnimator());

        recommendSongs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendSongs.setItemAnimator(new DefaultItemAnimator());

        historySongs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        historySongs.setItemAnimator(new DefaultItemAnimator());

        Bundle bundle = new Bundle();
        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=India&api_key=6ec25830383be032963ec5907d989f5e&format=json&limit=15");
        getLoaderManager().initLoader(1, bundle, this);

        //Locad All top artists in India
        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=India&api_key=" + getString(R.string.api_key) + "&format=json&limit=15");
        getLoaderManager().initLoader(2,bundle, this);

        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=India&api_key=" + getString(R.string.api_key) + "&format=json&limit=15&page=2");
        getLoaderManager().initLoader(3,bundle, this);

        bundle.putString("URL", "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=India&api_key=" + getString(R.string.api_key) + "&format=json&limit=15&page=3");
        getLoaderManager().initLoader(4,bundle, this);

        return view;
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new LoadData(getActivity(), bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try{
            JSONObject json = new JSONObject(data);
            if(loader.getId() == 1 || loader.getId() == 3 || loader.getId() == 4){
                if(!json.isNull("tracks"))
                {
                    allSongs = new ArrayList<>();
                    JSONArray jarray = json.getJSONObject("tracks").getJSONArray("track");
                    for(int i = 0; i < jarray.length(); i++){
                        JSONObject tempObj = jarray.getJSONObject(i);
                        String songName = tempObj.getString("name");
                        String artistName = tempObj.getJSONObject("artist").getString("name");
                        String songImage = tempObj.getJSONArray("image").getJSONObject(2).getString("#text");
                        allSongs.add(new Song(songName, artistName, songImage));
                    }
                    if(loader.getId() == 1)
                    {
                        SongsRecyclerAdapter songsRecyclerAdapter = new SongsRecyclerAdapter(allSongs, getContext());
                        tradingSongs.setAdapter(songsRecyclerAdapter);
                        songsRecyclerAdapter.notifyDataSetChanged();
                    }
                    if(loader.getId() == 3)
                    {
                        SongsRecyclerAdapter songsRecyclerAdapter1 = new SongsRecyclerAdapter(allSongs, getContext());
                        recommendSongs.setAdapter(songsRecyclerAdapter1);
                        songsRecyclerAdapter1.notifyDataSetChanged();
                    }
                    if(loader.getId() == 4)
                    {
                        SongsRecyclerAdapter songsRecyclerAdapter2 = new SongsRecyclerAdapter(allSongs ,getContext());
                        historySongs.setAdapter(songsRecyclerAdapter2);
                        songsRecyclerAdapter2.notifyDataSetChanged();
                    }

                }
            }
            if(loader.getId() == 2){
                if(!json.isNull("topartists"))
                {
                    List<Artist> allArtists = new ArrayList<>();
                    JSONArray tracks =  json.getJSONObject("topartists").getJSONArray("artist");
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

                        Artist temp = new Artist(artistName, artistImageUrl);
                        allArtists.add(temp);
                    }
                    ArtistAdapter artistAdapter = new ArtistAdapter(allArtists, getActivity());
                    artistView.setAdapter(artistAdapter);
                    artistAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {

    }
}
