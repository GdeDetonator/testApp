package kg.skureganov.testapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommentsApi {
    @GET("comments")
     Call<List<Comment>> getComments (@Query("postId") Integer postId );
}
