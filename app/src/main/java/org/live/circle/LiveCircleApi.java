package org.live.circle;

import org.live.circle.entity.MovieEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by drakeet on 8/9/15.
 */
public interface LiveCircleApi {
//    @GET("v1/live/2")
//    Call<DouyuEntity> getDouyuLushi(@Query("limit") int limit, @Query("offset") int offset, @Query("time") long time, @Query("auth") String auth);

//@GET("v1/live/2")
//    Call<ResponseBody> getDouyuLushi(@Query("limit") int limit, @Query("offset") int offset, @Query("time") long time, @Query("auth") String auth);

    @GET("v1/live/2")
    Observable<ResponseBody> getDouyuLushi(@Query("limit") int limit, @Query("offset") int offset, @Query("time") long time, @Query("auth") String auth);


    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

}
