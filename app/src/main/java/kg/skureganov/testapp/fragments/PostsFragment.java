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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import kg.skureganov.testapp.activities.CommentsActivity;
import kg.skureganov.testapp.R;
import kg.skureganov.testapp.adapters.PostsListAdapter;
import kg.skureganov.testapp.retrofit.Post;


public class PostsFragment extends Fragment {


    private static final String POST_ID = "post_id";
    private ArrayList<Post> postList;

    private RecyclerView recyclerView;
    private PostsListAdapter adapter;


    public PostsFragment() {
        // Required empty public constructor
    }

    public static PostsFragment newInstance(ArrayList<Post> postList) {
        PostsFragment fragment = new PostsFragment();
        fragment.setPostList(postList);
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
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (postList != null){
            recyclerView = view.findViewById(R.id.postRV);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
            adapter = new PostsListAdapter(postList, new PostsListAdapter.OnPostItemClickListener() {
                @Override
                public void onPostItemClick(Post post) {
                    Intent intent = new Intent(getContext(), CommentsActivity.class);
                    intent.putExtra(POST_ID, post.getId());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);

        }
        else Toast.makeText(getContext(), R.string.internet_problems, Toast.LENGTH_SHORT).show();
    }


    public void setPostList(ArrayList<Post> postList){
        this.postList = postList;
    }

}
