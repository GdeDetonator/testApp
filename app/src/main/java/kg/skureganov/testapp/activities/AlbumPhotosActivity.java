package kg.skureganov.testapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.List;

import kg.skureganov.testapp.R;
import kg.skureganov.testapp.adapters.PhotosListAdapter;
import kg.skureganov.testapp.retrofit.Photo;
import kg.skureganov.testapp.retrofit.PhotosApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumPhotosActivity extends AppCompatActivity {
    private String PLACEHOLDER_URL = "http://jsonplaceholder.typicode.com/";
    private static final String ALBUM_ID = "album_id";
    private static final String ALBUM_TITLE = "album_title";
    private static final String BIG_PICTURE_URL = "big_picture_url";

    private List<Photo> photoList;
    private Integer albumId;
    private String albumTitle;
    private RecyclerView recyclerView;
    private PhotosListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photos);
        Intent intent = getIntent();
        albumId = intent.getIntExtra(ALBUM_ID, 0);
        albumTitle = intent.getStringExtra(ALBUM_TITLE);
        setTitle(albumTitle);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PLACEHOLDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PhotosApi photosApi = retrofit.create(PhotosApi.class);
        Call <List<Photo>> photos = photosApi.getPhotos(albumId);
        photos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                photoList = response.body();

                recyclerView = findViewById(R.id.photosList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                adapter = new PhotosListAdapter(getApplicationContext(), photoList, new PhotosListAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(String url) {
                        Intent intent1 = new Intent(AlbumPhotosActivity.this, PhotoFullscreenActivity.class);
                        intent1.putExtra(BIG_PICTURE_URL, url);
                        startActivity(intent1);
                    }
                });

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });

    }
}
