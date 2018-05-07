package com.karbyshev.zastaffka.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.presenter.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Photo> photoList = new ArrayList<>();
    private RecyclerItemClickListener mListener;

    public void setOnItemClickListiner(RecyclerItemClickListener mListener){
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photoList.get(position);

        String likes = photo.getLikes().toString();
        String userName = photo.getUser().getUsername();
        String imageUrl = photo.getUrls().getRegular();
        String avatarImageUrl = photo.getUser().getProfileImage().getLarge();

        holder.mUserName.setText(userName);
        holder.mLikesTextView.setText(likes);

        Picasso.get().load(imageUrl).into(holder.mMainImage);
        Picasso.get().load(avatarImageUrl).into(holder.mAvaterImageView);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemAvatarImageView)
        CircleImageView mAvaterImageView;
        @BindView(R.id.itemLikesTextView)
        TextView mLikesTextView;
        @BindView(R.id.itemMainImageView)
        ImageView mMainImage;
        @BindView(R.id.itemUserName)
        TextView mUserName;

        public ViewHolder(View itemView) {
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

    public void addAll(List<Photo> list){
        photoList.addAll(list);
        notifyItemRangeInserted(photoList.size() - list.size(), list.size());
    }
}
