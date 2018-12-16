package com.support.android.designlibdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AvatarRecyclerViewAdapter
    extends RecyclerView.Adapter<AvatarRecyclerViewAdapter.ViewHolder> {

  private final TypedValue mTypedValue = new TypedValue();
  private int mBackground;
  private List<String> mValues;

  public AvatarRecyclerViewAdapter(Context context, List<String> items) {
    context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
    mBackground = mTypedValue.resourceId;
    mValues = items;
  }

  public void addAll(List<String> items) {
    mValues = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.avatar_list_item, parent, false);
    view.setBackgroundResource(mBackground);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    Glide.with(holder.mImageView.getContext())
        .load(Cheeses.getRandomCheeseDrawable())
        .fitCenter()
        .into(holder.mImageView);
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final ImageView mImageView;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mImageView = (ImageView) view.findViewById(R.id.avatar);
    }

  }
}