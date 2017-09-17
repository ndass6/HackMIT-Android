package com.tejunareddy.hackmit2017.bucketlist.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.activity.EditBucketActivity;
import com.tejunareddy.hackmit2017.bucketlist.activity.ViewBucketActivity;
import com.tejunareddy.hackmit2017.bucketlist.fragment.BucketListItemFragment.OnListFragmentInteractionListener;
import com.tejunareddy.hackmit2017.bucketlist.fragment.dummy.DummyBucketListItems;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BucketListItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBucketListItemRecyclerViewAdapter extends RecyclerView.Adapter<MyBucketListItemRecyclerViewAdapter.ViewHolder> {

    private final List<BucketListItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyBucketListItemRecyclerViewAdapter(List<BucketListItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bucketlistitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        if (holder.mItem.getCityPicture() != null) {
            holder.cityImageView.setImageBitmap(holder.mItem.getCityPicture());
        }
        holder.cityNameView.setText(holder.mItem.getEndCity());
        holder.priceView.setText("$" + holder.mItem.getPrice() + " from " + dateToString(holder.mItem.getStartDate()) + " to " + dateToString(holder.mItem.getEndDate()));
        if (holder.mItem.getStarred()) {
            holder.starButton.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            holder.starButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
        holder.starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.mItem.getStarred()) {
                    holder.mItem.setStarred(true);
                    holder.starButton.setImageResource(R.drawable.ic_star_black_24dp);
                } else {
                    holder.mItem.setStarred(false);
                    holder.starButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                }
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, true);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DummyBucketListItems.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, false);
                }
            }
        });
    }

    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/YYYY", Locale.US);
        return simpleDateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView cityImageView;
        public final TextView cityNameView;
        public final TextView priceView;
        //        public final TextView dateView;
        public final ImageButton starButton;
        public final Button editButton;
        public final Button deleteButton;
        public BucketListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cityImageView = (ImageView) view.findViewById(R.id.bucketlist_picture);
            cityNameView = (TextView) view.findViewById(R.id.bucketlist_end_city);
            priceView = (TextView) view.findViewById(R.id.bucketlist_price);
            starButton = (ImageButton) view.findViewById(R.id.bucketlist_star);
            editButton = (Button) view.findViewById(R.id.bucketlist_edit);
            deleteButton = (Button) view.findViewById(R.id.bucketlist_delete);
        }

    }
}
