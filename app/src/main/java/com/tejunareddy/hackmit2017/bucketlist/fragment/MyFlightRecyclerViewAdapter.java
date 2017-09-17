package com.tejunareddy.hackmit2017.bucketlist.fragment;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.fragment.FlightFragment.OnListFragmentInteractionListener;
import com.tejunareddy.hackmit2017.bucketlist.model.Flight;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFlightRecyclerViewAdapter extends RecyclerView.Adapter<MyFlightRecyclerViewAdapter.ViewHolder> {

    private final List<Flight> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFlightRecyclerViewAdapter(List<Flight> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_flight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.airlineView.setText(holder.mItem.getAirline() + " Flight");
        holder.priceDateView.setText("$" + holder.mItem.getPrice() + " on " + dateToString(holder.mItem.getDepartDate()));
        holder.citiesView.setText(holder.mItem.getDepartAirport() + " -> " + holder.mItem.getArrivalAirport());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }
    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);
        return simpleDateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView airlineView;
        public final TextView priceDateView;
        public final TextView citiesView;
        public Flight mItem;

        public ViewHolder(View view) {
            super(view);
            airlineView = (TextView) view.findViewById(R.id.flight_airline);
            priceDateView = (TextView) view.findViewById(R.id.flight_price_date);
            citiesView = (TextView) view.findViewById(R.id.flight_depart);
            mView = view;
        }
    }
}
