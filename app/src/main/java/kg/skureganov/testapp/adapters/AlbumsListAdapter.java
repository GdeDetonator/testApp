package kg.skureganov.testapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kg.skureganov.testapp.R;
import kg.skureganov.testapp.retrofit.Album;


public class AlbumsListAdapter extends RecyclerView.Adapter<AlbumsListAdapter.AlbumListHolder>{
    ArrayList<Album> albumList;
    OnAlbumItemClickListener onAlbumItemClickListener;

    public AlbumsListAdapter(ArrayList<Album> albumList, OnAlbumItemClickListener onAlbumItemClickListener) {
        this.albumList = albumList;
        this.onAlbumItemClickListener = onAlbumItemClickListener;
    }

    @NonNull
    @Override
    public AlbumListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_list, parent, false);
        AlbumListHolder holder = new AlbumListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbumListHolder holder, int position) {
        holder.albumTitle.setText(albumList.get(position).getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAlbumItemClickListener.onAlbumItemClick(albumList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    static class AlbumListHolder extends RecyclerView.ViewHolder{
        TextView albumTitle;
        View view;

         AlbumListHolder(View itemView) {
            super(itemView);
            albumTitle = itemView.findViewById(R.id.albumTitle);
            view = itemView.findViewById(R.id.albumItemLayout);
        }
    }


    public interface OnAlbumItemClickListener{
        void onAlbumItemClick(Album album);
    }
}
