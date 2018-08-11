package kg.skureganov.testapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kg.skureganov.testapp.R;
import kg.skureganov.testapp.retrofit.Post;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostListHolder> {

    private ArrayList<Post> postList;
    private OnPostItemClickListener onPostItemClickListener;

    public PostsListAdapter(ArrayList<Post> postList, OnPostItemClickListener listener) {
        this.postList = postList;
        this.onPostItemClickListener = listener;
    }

    @NonNull
    @Override
    public PostListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_list, parent, false);
        PostListHolder holder = new PostListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PostListHolder holder, int position) {
        holder.postTitle.setText(postList.get(position).getTitle());
        holder.postBody.setText(postList.get(position).getBody());
        holder.postItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPostItemClickListener.onPostItemClick(postList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostListHolder extends RecyclerView.ViewHolder{
        TextView postTitle;
        TextView postBody;
        View postItem;

        PostListHolder(View itemView) {
            super(itemView);
            postItem = itemView.findViewById(R.id.postsItemLayout);
            postTitle = itemView.findViewById(R.id.postsListTitle);
            postBody = itemView.findViewById(R.id.postListBody);
        }
    }

    public interface OnPostItemClickListener{
        void onPostItemClick(Post post);
    }
}
