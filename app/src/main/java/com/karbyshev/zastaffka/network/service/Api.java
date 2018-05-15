package com.karbyshev.zastaffka.network.service;

import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.models.SearchResults;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("photos/")
    Single<List<Photo>> getPhotos(@QueryMap Map<String, String> params);

    @GET("search/photos/")
    Single<SearchResults> searchPhotos(@QueryMap Map<String, String> params);

    @GET("photos/{id}")
    Single<Photo> getPhoto(@Path("id") String id, @QueryMap Map<String, String> params);

}
