package com.tejunareddy.hackmit2017.bucketlist.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditBucketActivity extends AppCompatActivity {

    public static BucketListItem bucket = null;
    private FloatingActionButton fab;
    private ImageView cityImageView;
    private EditText dayView;
    private EditText startDateView;
    private EditText endDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bucket);
        CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.edit_collapsing_toolbar);
        toolbar.setTitle(bucket.getEndCity());

        cityImageView = (ImageView) findViewById(R.id.bucket_edit_image);
        cityImageView.setImageBitmap(bucket.getCityPicture());

        startDateView = (EditText) findViewById(R.id.bucket_edit_startDate);
        startDateView.setText(dateToString(bucket.getUserSetStartDate()));

        endDateView = (EditText) findViewById(R.id.bucket_edit_endDate);
        endDateView.setText(dateToString(bucket.getUserSetEndDate()));

        dayView = (EditText) findViewById(R.id.bucket_edit_days_text);
        dayView.setText(bucket.getDuration() + "");

        fab = (FloatingActionButton) findViewById(R.id.bucket_edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/YYYY", Locale.US);
                try {
                    Date startDate = simpleDateFormat.parse(startDateView.getText().toString());
                    Date endDate = simpleDateFormat.parse(endDateView.getText().toString());

                    bucket.setUserSetStartDate(startDate);
                    bucket.setUserSetEndDate(endDate);

                } catch (ParseException e) {
                    return;
                }

                bucket.setDuration(Integer.parseInt(dayView.getText().toString()));

                // TODO save item to api

                finish();
            }
        });
    }

    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/YYYY", Locale.US);
        return simpleDateFormat.format(date);
    }
}
