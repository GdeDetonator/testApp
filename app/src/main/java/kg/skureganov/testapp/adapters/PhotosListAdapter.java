package kg.skureganov.testapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kg.skureganov.testapp.retrofit.Photo;
import kg.skureganov.testapp.R;


public class PhotosListAdapter extends RecyclerView.Adapter<PhotosListAdapter.PhotosListHolder> {

    private List<Photo> photoList;

    onItemClickListener onItemClickListener;

    public PhotosListAdapter (List<Photo> photoList, onItemClickListener onItemClickListener) {
        this.photoList = photoList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PhotosListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos_list, parent, false);
        PhotosListHolder photosListHolder = new PhotosListHolder(view);
        return photosListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotosListHolder holder, int position) {
        Picasso.get().load(photoList.get(position).getThumbnailUrl()).into(holder.photosListImage);
//        holder.photosListImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onItemClick(photoList.get(holder.getAdapterPosition()).getUrl());
//            }
//        });
        holder.photosListTitle.setText(photoList.get(position).getTitle());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(photoList.get(holder.getAdapterPosition()).getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotosListHolder extends RecyclerView.ViewHolder{
        ImageView photosListImage;
        TextView photosListTitle;
        View layout;

        PhotosListHolder(View itemView) {
            super(itemView);
            photosListImage = itemView.findViewById(R.id.photosListImage);
            photosListTitle = itemView.findViewById(R.id.photosListTitle);
            layout = itemView.findViewById(R.id.photosListLayout);
        }

    }

    public interface onItemClickListener{
        void onItemClick(String url);
    }


}
