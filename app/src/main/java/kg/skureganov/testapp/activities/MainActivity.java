package kg.skureganov.testapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kg.skureganov.testapp.R;
import kg.skureganov.testapp.fragments.AlbumsFragment;
import kg.skureganov.testapp.fragments.PostsFragment;
import kg.skureganov.testapp.retrofit.Album;
import kg.skureganov.testapp.retrofit.AlbumsApi;
import kg.skureganov.testapp.retrofit.Post;
import kg.skureganov.testapp.retrofit.PostsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Album> albumList;
    private ArrayList<Integer> albumIds;
    private ArrayList<String> albumTitles;
    private List<Post> postList;
    private ArrayList<Post> randomPostList;

    private String PLACEHOLDER_URL = "http://jsonplaceholder.typicode.com/";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_albums:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContainer, AlbumsFragment.newInstance(albumTitles, albumIds))
                            .commit();
                    return true;
                case R.id.navigation_posts:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContainer, PostsFragment.newInstance(randomPostList))
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getAlbums();

        getPosts();


    }


    private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PLACEHOLDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostsApi postsApi = retrofit.create(PostsApi.class);
        Call<List<Post>> posts = postsApi.getPosts();
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                postList = response.body();
                randomPostList = getRandomPostList(postList);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    private ArrayList<Post> getRandomPostList(List<Post> posts) {
        ArrayList<Post> randomPostList = new ArrayList<>();
        ArrayList<Integer> randomValues = new ArrayList<>();
        Random random = new Random();
        Integer value = random.nextInt(posts.size());

        for (int i = 0; i < 15; i++) {
            while (randomValues.contains(value)) {
                value = random.nextInt(posts.size());
            }
            randomValues.add(value);
            randomPostList.add(posts.get(value));
        }
        return randomPostList;
    }

    private void getAlbums() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PLACEHOLDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AlbumsApi albumsApi = retrofit.create(AlbumsApi.class);
        Call<List<Album>> albums = albumsApi.getAlbums();
        albums.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albumList = response.body();

                albumTitles = getRandomAlbumList(albumList);

                albumIds = new ArrayList<Integer>();

                for (int i = 0; i < albumTitles.size(); i++) {
                    for (int j = 0; j < albumList.size(); j++) {
                        if (albumList.get(j).getTitle() == albumTitles.get(i)) {
                            albumIds.add(albumList.get(j).getId());
                        }
                    }
                }


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainContainer, AlbumsFragment.newInstance(albumTitles, albumIds))
                        .commit();


            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }


    private ArrayList<String> getRandomAlbumList(List<Album> albums) {
        ArrayList<String> albumTitles = new ArrayList<String>();
        ArrayList<Integer> randomValues = new ArrayList<>();
        Random random = new Random();
        Integer value = random.nextInt(albums.size());

        for (int i = 0; i < 10; i++) {
            while (randomValues.contains(value)) {
                value = random.nextInt(albums.size());
            }
            randomValues.add(value);
            albumTitles.add(albums.get(value).getTitle());
        }
        return albumTitles;
    }



}
