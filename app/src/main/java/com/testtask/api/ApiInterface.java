package com.testtask.api;


import com.testtask.model.ImageModel;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("/photos/?client_id=896d4f52c589547b2134bd75ed48742db637fa51810b49b607e37e46ab2c0043")
    Call<List<ImageModel>> getImages(
            @Query("page") int page,
            @Query("per_page") int per_page
    );
}
