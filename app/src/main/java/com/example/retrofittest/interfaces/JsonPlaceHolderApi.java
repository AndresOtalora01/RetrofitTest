package com.example.retrofittest.interfaces;

import com.example.retrofittest.models.Comment;
import com.example.retrofittest.models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    /*  @GET ("posts")
      Call<List<Post>> getPost(@Query("userId") int position); //this will add a '?' before user id and an equal after. Like this: ?userId=position //Call<List<Post>> getPost();
  */

    //Query for posts sorted by id in descending order. posts?userId=1&_sort=id&_order=desc // this would work for path annotation the same way adding / before each parameter
    @GET("posts")
    Call<List<Post>> getPost(@Query("userId") Integer[] position, //im using Integer cos it is nullable. Btw i added array so i can do a multiple user id search
                             @Query("_sort") String sort,
                             @Query("_order") String order);

    @GET("posts")
    Call<List<Post>> getPost(@QueryMap Map<String, String> parameters);

    @GET("posts/{id}/comments")
        //@GET("posts/2/comments") hardcoded way
    Call<List<Comment>> getComments(@Path("id") int position);

    @GET
    Call<List<Comment>> getComments(@Url String url); // i could use this get method to use other url or a complex one.

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("tite") String title,
            @Field("body") String body
    );

    //replace the whole object
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id")int id, @Body Post post);

    //update the fields we indicate
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id")int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
