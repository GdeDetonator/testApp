package kg.skureganov.testapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumsApi {

    @GET("albums")
    Call<List<Album>> getAlbums();

}
