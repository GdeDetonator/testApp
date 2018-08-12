package kg.skureganov.testapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kg.skureganov.testapp.activities.AlbumPhotosActivity;
import kg.skureganov.testapp.R;
import kg.skureganov.testapp.adapters.AlbumsListAdapter;
import kg.skureganov.testapp.retrofit.Album;


public class AlbumsFragment extends Fragment {

    private static final String ALBUM_ID = "album_id";
    private static final String ALBUM_TITLE = "album_title";

    private ArrayList<Album> albumList;

//    private String PLACEHOLDER_URL = "http://jsonplaceholder.typicode.com/";

    private RecyclerView recyclerView;
    private AlbumsListAdapter adapter;


    public AlbumsFragment() {
        // Required empty public constructor
    }

    public static AlbumsFragment newInstance(ArrayList<Album> albumList) {
        AlbumsFragment fragment = new AlbumsFragment();
        Bundle args = new Bundle();
        fragment.setAlbumList(albumList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        if (albumList != null){

            recyclerView = getActivity().findViewById(R.id.albumRV);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
            adapter = new AlbumsListAdapter(albumList, new AlbumsListAdapter.OnAlbumItemClickListener() {
                @Override
                public void onAlbumItemClick(Album album) {
                    Intent intent = new Intent(getContext(), AlbumPhotosActivity.class);
                    intent.putExtra(ALBUM_ID, album.getId());
                    intent.putExtra(ALBUM_TITLE, album.getTitle());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);

        }
        else Toast.makeText(getContext(), R.string.internet_problems, Toast.LENGTH_SHORT).show();



    }

    private void setAlbumList(ArrayList<Album> albumList){
        this.albumList = albumList;
    }
}
