package com.tejunareddy.hackmit2017.bucketlist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.fragment.FlightFragment;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewBucketActivity extends AppCompatActivity {

    private static BucketListItem bucket;
    private FloatingActionButton fab;
    private ImageView cityImageView;
    private TextView priceView;
    private TextView startDateView;
    private TextView endDateView;

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
                Intent intent = new Intent(ViewBucketActivity.this, EditBucketActivity.class);
                EditBucketActivity.bucket = bucket;
                startActivity(intent);
            }
        });

        cityImageView = (ImageView) findViewById(R.id.bucket_view_image);
        cityImageView.setImageBitmap(bucket.getCityPicture());

        priceView = (TextView) findViewById(R.id.bucket_view_price);
        priceView.setText("Best Price: $" + bucket.getPrice());

        startDateView = (TextView) findViewById(R.id.bucket_view_startDate);
        startDateView.setText("Depart Date: " + dateToString(bucket.getStartDate()));

        endDateView = (TextView) findViewById(R.id.bucket_view_endDate);
        endDateView.setText("Return Date: " + dateToString(bucket.getEndDate()));

        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.bucket_view_frame, FlightFragment.newInstance(bucket));
        fragmentTransaction.commit();

    }

    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/YYYY", Locale.US);
        return simpleDateFormat.format(date);
    }
}
