package com.karbyshev.zastaffka.network;

import com.karbyshev.zastaffka.models.Photo;
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
}
