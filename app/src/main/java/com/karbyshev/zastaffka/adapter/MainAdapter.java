package com.karbyshev.zastaffka.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.view.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Photo> photoList = new ArrayList<>();
    private RecyclerItemClickListener mListener;
    private boolean isLoading = false;

    public void setOnItemClickListener(RecyclerItemClickListener mListener){
        this.mListener = mListener;
    }

    public void setLoading(boolean isLoading) {
        if (this.isLoading != isLoading) {
            boolean oldValue = this.isLoading;
            this.isLoading = isLoading;
            if (!oldValue && isLoading) {
                notifyItemInserted(photoList.size());
            }
            if (oldValue && !isLoading) {
                notifyItemRemoved(photoList.size());
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
            return new PhotoViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemAvatarImageView)
        CircleImageView mAvaterImageView;
        @BindView(R.id.itemLikesTextView)
        TextView mLikesTextView;
        @BindView(R.id.itemMainImageView)
        ImageView mMainImage;
        @BindView(R.id.itemUserName)
        TextView mUserName;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position, photoList);
                        }
                    }
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.progressBarItem)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotoViewHolder) {
            Photo photo = photoList.get(position);

            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;

            String likes = photo.getLikes().toString();
            String userName = photo.getUser().getUsername();
            String imageUrl = photo.getUrls().getRegular();
            String avatarImageUrl = photo.getUser().getProfileImage().getLarge();

            photoViewHolder.mUserName.setText(userName);
            photoViewHolder.mLikesTextView.setText(likes);

            Picasso.get().load(imageUrl).into(photoViewHolder.mMainImage);
            Picasso.get().load(avatarImageUrl).into(photoViewHolder.mAvaterImageView);
        } else if (holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading && position == photoList.size()) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size() + (isLoading ? 1 : 0);
    }

    public void addAll(List<Photo> list){
        photoList.addAll(list);
        notifyItemRangeInserted(photoList.size() - list.size(), list.size());
    }

    public void deleteAll(){
        photoList.clear();
        notifyDataSetChanged();
    }
}
