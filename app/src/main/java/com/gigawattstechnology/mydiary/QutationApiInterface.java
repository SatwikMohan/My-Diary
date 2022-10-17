package com.gigawattstechnology.mydiary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QutationApiInterface {
    @GET("quotes")
    Call<List<QutationModal>> getPosts();
}
