package com.tejunareddy.hackmit2017.bucketlist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;
import com.tejunareddy.hackmit2017.bucketlist.model.Flight;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FlightFragment extends Fragment {

    // TODO: Customize parameter argument names
    private OnListFragmentInteractionListener mListener;
    private BucketListItem bucketListItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FlightFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Fragment newInstance(BucketListItem bucketListItem) {
        FlightFragment fragment = new FlightFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setBucketListItem(bucketListItem);
        return fragment;
    }

    public void setBucketListItem(BucketListItem bucketListItem) {
        this.bucketListItem = bucketListItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new MyFlightRecyclerViewAdapter(bucketListItem.getFlightInfo(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Flight item);
    }
}
