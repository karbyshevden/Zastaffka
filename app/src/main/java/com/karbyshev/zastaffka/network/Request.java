package com.karbyshev.zastaffka.network;

import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.models.SearchResults;
import com.karbyshev.zastaffka.network.service.RetrofitService;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Request {

    public static Single<List<Photo>> getPhotos (String clientKey, int page, int pageSize){
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", clientKey);
        params.put("page", Integer.toString(page));
        params.put("per_page", Integer.toString(pageSize));

        return RetrofitService.getApi().getPhotos(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<SearchResults> searchPhotos (String clientKey, int page, int pageSize, String searchQuery){
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", clientKey);
        params.put("page", Integer.toString(page));
        params.put("per_page", Integer.toString(pageSize));
        params.put("query", searchQuery);

        return RetrofitService.getApi().searchPhotos(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<Photo> getLargePhoto (String id, String clientKey){
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", clientKey);

        return RetrofitService.getApi().getPhoto(id, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
