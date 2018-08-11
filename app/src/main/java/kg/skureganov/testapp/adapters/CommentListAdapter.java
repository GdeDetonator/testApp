package kg.skureganov.testapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kg.skureganov.testapp.R;
import kg.skureganov.testapp.retrofit.Comment;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentsListHolder> {

    private List<Comment> commentsList;

    public CommentListAdapter(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments_list, parent, false);
        CommentsListHolder holder = new CommentsListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsListHolder holder, int position) {
        holder.commentName.setText(commentsList.get(position).getName());
        holder.commentEmail.setText(commentsList.get(position).getEmail());
        holder.commentBody.setText(commentsList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }



    static class CommentsListHolder extends RecyclerView.ViewHolder{
        TextView commentName;
        TextView commentEmail;
        TextView commentBody;

        CommentsListHolder(View itemView) {
            super(itemView);
            commentName = itemView.findViewById(R.id.commentName);
            commentEmail = itemView.findViewById(R.id.commentEmail);
            commentBody = itemView.findViewById(R.id.commentBody);
        }
    }
}
