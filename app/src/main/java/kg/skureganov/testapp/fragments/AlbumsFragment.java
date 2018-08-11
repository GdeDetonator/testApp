package kg.skureganov.testapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kg.skureganov.testapp.activities.AlbumPhotosActivity;
import kg.skureganov.testapp.R;


public class AlbumsFragment extends Fragment {

    private static final String ALBUM_TITLES = "album_title_list";
    private static final String ALBUM_IDS = "album_ids";
    private static final String ALBUM_ID = "album_id";
    private static final String ALBUM_TITLE = "album_title";


    private ArrayList<String> albumTitles;
    private ArrayList<Integer> albumIds;
    private Integer albumId;
//    private String PLACEHOLDER_URL = "http://jsonplaceholder.typicode.com/";

    private ListView albumsLV;
    private ArrayAdapter<String> arrayAdapter;


    public AlbumsFragment() {
        // Required empty public constructor
    }

    public static AlbumsFragment newInstance(ArrayList<String> albumTitleList, ArrayList<Integer> albumIds) {
        AlbumsFragment fragment = new AlbumsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ALBUM_TITLES, albumTitleList);
        args.putIntegerArrayList(ALBUM_IDS, albumIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.albumTitles = getArguments().getStringArrayList(ALBUM_TITLES);
            this.albumIds = getArguments().getIntegerArrayList(ALBUM_IDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumsLV = getActivity().findViewById(R.id.albumLV);
        if (albumTitles != null){
            arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_album_list, R.id.albumTitle, albumTitles);
            albumsLV.setAdapter(arrayAdapter);
            albumsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    albumId = albumIds.get(position);

                    Intent intent = new Intent(getContext(), AlbumPhotosActivity.class);
                    intent.putExtra(ALBUM_TITLE, albumTitles.get(position));
                    intent.putExtra(ALBUM_ID, albumId);
                    startActivity(intent);

                }
            });
        }
        else Toast.makeText(getContext(), R.string.internet_problems, Toast.LENGTH_SHORT).show();



    }
}
