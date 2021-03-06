package kg.skureganov.testapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kg.skureganov.testapp.R;
import kg.skureganov.testapp.fragments.AlbumsFragment;
import kg.skureganov.testapp.fragments.PostsFragment;
import kg.skureganov.testapp.retrofit.Album;
import kg.skureganov.testapp.retrofit.Post;
import kg.skureganov.testapp.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static List<Album> albumList;
    private static ArrayList<Album> randomAlbumList;
    private static List<Post> postList;
    private static ArrayList<Post> randomPostList;

    private static final String PLACEHOLDER_URL = "http://jsonplaceholder.typicode.com/";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_albums:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContainer, AlbumsFragment.newInstance(randomAlbumList))
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

        if ((postList == null) && (randomPostList == null)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PLACEHOLDER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitApi postsApi = retrofit.create(RetrofitApi.class);
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

        if ((albumList == null) && (randomAlbumList == null)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PLACEHOLDER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitApi albumsApi = retrofit.create(RetrofitApi.class);
            Call<List<Album>> albums = albumsApi.getAlbums();
            albums.enqueue(new Callback<List<Album>>() {
                @Override
                public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                    albumList = response.body();

                    randomAlbumList = getRandomAlbumList(albumList);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.mainContainer, AlbumsFragment.newInstance(randomAlbumList))
                            .commit();


                }

                @Override
                public void onFailure(Call<List<Album>> call, Throwable t) {

                }
            });
        }
        else {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainContainer, AlbumsFragment.newInstance(randomAlbumList))
                    .commit();
        }


    }


    private ArrayList<Album> getRandomAlbumList(List<Album> albums) {
        ArrayList<Album> randomAlbumList = new ArrayList<>();
        ArrayList<Integer> randomValues = new ArrayList<>();
        Random random = new Random();
        Integer value = random.nextInt(albums.size());

        for (int i = 0; i < 10; i++) {
            while (randomValues.contains(value)) {
                value = random.nextInt(albums.size());
            }
            randomValues.add(value);
            randomAlbumList.add(albums.get(value));
        }
        return randomAlbumList;
    }



}
