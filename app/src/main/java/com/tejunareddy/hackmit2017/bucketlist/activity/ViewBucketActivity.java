package com.tejunareddy.hackmit2017.bucketlist.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.fragment.FlightFragment;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;

public class ViewBucketActivity extends AppCompatActivity {

    private static BucketListItem bucket;
    private FloatingActionButton fab;
    private ImageView cityImageView;

    public static void setBucket(BucketListItem bucket) {
        ViewBucketActivity.bucket = bucket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bucket);
        CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar.setTitle(bucket.getEndCity());

        fab = (FloatingActionButton) findViewById(R.id.bucket_view_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        cityImageView = (ImageView) findViewById(R.id.bucket_view_image);
        cityImageView.setImageBitmap(bucket.getCityPicture());

        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.bucket_view_frame, FlightFragment.newInstance(bucket));
        fragmentTransaction.commit();

    }

}
