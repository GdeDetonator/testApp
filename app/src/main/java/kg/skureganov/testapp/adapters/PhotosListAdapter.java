package kg.skureganov.testapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kg.skureganov.testapp.retrofit.Photo;
import kg.skureganov.testapp.R;


public class PhotosListAdapter extends RecyclerView.Adapter<PhotosListAdapter.PhotosListHolder> {

    private List<Photo> photoList;
    Context context;
    public onItemClickListener onItemClickListener;

    public PhotosListAdapter(Context context, List<Photo> photoList, onItemClickListener onItemClickListener) {
        this.photoList = photoList;
        this.context = context;
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
    public void onBindViewHolder(@NonNull PhotosListHolder holder, final int position) {
        Picasso.get().load(photoList.get(position).getThumbnailUrl()).into(holder.photosListImage);
        holder.photosListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(photoList.get(position).getUrl());
            }
        });
        //holder.photosListImage = photoList.get(position).getThumbnailUrl();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotosListHolder extends RecyclerView.ViewHolder{
        ImageView photosListImage;

        public PhotosListHolder(View itemView) {
            super(itemView);
            photosListImage = itemView.findViewById(R.id.photosListImage);
        }

    }

    public interface onItemClickListener{
        void onItemClick(String url);
    }


}
