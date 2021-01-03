package com.example.retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofittest.interfaces.JsonPlaceHolderApi;
import com.example.retrofittest.models.Comment;
import com.example.retrofittest.models.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private List<Post> posts;
    private List<Comment> comments;
    private JsonPlaceHolderApi jsonPlaceHolderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.tvText);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        //getComments();
        //createPost();
        //updatePost();
        deletePost();
    }

    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");


        //Call<List<Post>> call = jsonPlaceHolderApi.getPost(new Integer[]{2,6,7}, null,null);
        // Call<List<Post>> call = jsonPlaceHolderApi.getPost(new Integer[]{2,6,7}, null,null);
        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code " + response.code());
                    return;
                }
                posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "UserId: " + post.getUserId() + " \nId: " + post.getId() +
                            "\nTitle: " + post.getTitle() + "\nBody: " + post.getBody() + "\n\n";
                    text.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(3);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code " + response.code());
                    return;
                }
                comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "Post Id: " + comment.getPostId() + "\nId: " + comment.getId()
                            + "\nName: " + comment.getName() + "\nEmail: " + comment.getEmail() +
                            "\nBody: " + comment.getBody() + "\n\n";
                    text.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        // Post post = new Post(23, "new Title", "New body");
        Call<Post> call = jsonPlaceHolderApi.createPost(23, "New Title", "New text");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "UserId: " + postResponse.getUserId() + " \nId: " + postResponse.getId() +
                        "\nTitle: " + postResponse.getTitle() + "\nBody: " + postResponse.getBody() + "\n\n";
                text.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(12, null, "New text");
        Call<Post> call = jsonPlaceHolderApi.putPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "UserId: " + postResponse.getUserId() + " \nId: " + postResponse.getId() +
                        "\nTitle: " + postResponse.getTitle() + "\nBody: " + postResponse.getBody() + "\n\n";
                text.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(1);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                text.setText("Code: "+ response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                    text.setText(t.getMessage());
            }
        });
    }
}