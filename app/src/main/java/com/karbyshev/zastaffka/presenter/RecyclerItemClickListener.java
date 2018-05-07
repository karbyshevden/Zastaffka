package com.karbyshev.zastaffka.presenter;

import com.karbyshev.zastaffka.models.Photo;

import java.util.List;

public interface RecyclerItemClickListener {

    void onItemClick (int position, List<Photo> list);
}
