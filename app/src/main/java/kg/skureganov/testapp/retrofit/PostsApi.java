package kg.skureganov.testapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostsApi {
    @GET("posts")
    Call<List<Post>> getPosts();
}
