package kg.skureganov.testapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.List;

import kg.skureganov.testapp.R;
import kg.skureganov.testapp.adapters.CommentListAdapter;
import kg.skureganov.testapp.retrofit.Comment;
import kg.skureganov.testapp.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentsActivity extends AppCompatActivity {

    private static final String POST_ID = "post_id";
    private String PLACEHOLDER_URL = "http://jsonplaceholder.typicode.com/";

    List<Comment> commentList;
    private RecyclerView recyclerView;
    private CommentListAdapter adapter;
    private Intent intent;
    private Integer postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setTitle(R.string.comments_activity_title);

        intent = getIntent();
        postId = intent.getIntExtra(POST_ID, 0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PLACEHOLDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi commentsApi = retrofit.create(RetrofitApi.class);
        Call<List<Comment>> comments =  commentsApi.getComments(postId);
        comments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                commentList =  response.body();
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });



    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.commentsRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        adapter = new CommentListAdapter(commentList);
        recyclerView.setAdapter(adapter);
    }



}
