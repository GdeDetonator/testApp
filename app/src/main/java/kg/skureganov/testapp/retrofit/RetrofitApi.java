package kg.skureganov.testapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("albums")
    Call<List<Album>> getAlbums();

    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("albumId") Integer albumId);

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("comments")
    Call<List<Comment>> getComments (@Query("postId") Integer postId );
}
