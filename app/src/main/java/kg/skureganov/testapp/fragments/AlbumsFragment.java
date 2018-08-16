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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kg.skureganov.testapp.activities.AlbumPhotosActivity;
import kg.skureganov.testapp.R;
import kg.skureganov.testapp.adapters.AlbumsListAdapter;
import kg.skureganov.testapp.retrofit.Album;
import kg.skureganov.testapp.retrofit.Photo;
import kg.skureganov.testapp.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AlbumsFragment extends Fragment {

    private static final String ALBUM_ID = "album_id";
    private static final String ALBUM_TITLE = "album_title";

    private ArrayList<Album> albumList;

    private String PLACEHOLDER_URL = "http://jsonplaceholder.typicode.com/";

    private RecyclerView recyclerView;
    private AlbumsListAdapter adapter;
    private View albumFragmentLayout;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    public static AlbumsFragment newInstance(ArrayList<Album> albumList) {
        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setAlbumList(albumList);
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
        albumFragmentLayout = view.findViewById(R.id.albumFragmentLayout);


        if (albumList != null){
            setRandomBackgroundImage(albumList);

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

    private void setRandomBackgroundImage(ArrayList<Album> albumList){
        final Random random = new Random();
        int randomAlbum = random.nextInt(albumList.size());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PLACEHOLDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi photosApi = retrofit.create(RetrofitApi.class);
        final Call<List<Photo>>  photos = photosApi.getPhotos(albumList.get(randomAlbum).getUserId());
        photos.enqueue(new retrofit2.Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                ArrayList<Photo> photosList =(ArrayList<Photo>) response.body();
                int randomPhoto = random.nextInt(photosList.size());
                final ImageView backgroundImage = new ImageView(getContext());
                Picasso.get().load(photosList.get(randomPhoto)
                        .getUrl()).into(backgroundImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        albumFragmentLayout.setBackground(backgroundImage.getDrawable());
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });
    }

    private void setAlbumList(ArrayList<Album> albumList){
        this.albumList = albumList;
    }
}
