package com.example.mango.musicapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    ListView manageAccountList;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        manageAccountList = (ListView) view.findViewById(R.id.manageList);

        ArrayList<List> managerList = new ArrayList<>();
        managerList.add(new List("My Music", "61 Songs", R.drawable.ic_mymusic_black_24dp, R.drawable.ic_more_black_24dp));
        managerList.add(new List("Following Artist", "26 Artist", R.drawable.ic_account_circle_black_24dp, R.drawable.ic_more_black_24dp));
        managerList.add(new List("Logout", "", R.drawable.ic_logout_black_24dp, R.drawable.ic_more_black_24dp));

        final ListAdapter managerListAdapter = new ListAdapter(getActivity(), 0, managerList);
        manageAccountList.setAdapter(managerListAdapter);

        manageAccountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(managerListAdapter.getItem(i).getListTile().compareTo("My Music") == 0){
                    Intent intent = new Intent(getActivity(), MyMusicActivity.class);
                    startActivity(intent);
                }
                if(managerListAdapter.getItem(i).getListTile().compareTo("Following Artist") == 0){
                    Intent intent = new Intent(getActivity(), MyArtistActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
