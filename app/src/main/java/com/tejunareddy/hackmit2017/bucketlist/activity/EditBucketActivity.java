package com.tejunareddy.hackmit2017.bucketlist.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EditBucketActivity extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);
                try {
                    Date startDate = simpleDateFormat.parse(startDateView.getText().toString());
                    Date endDate = simpleDateFormat.parse(endDateView.getText().toString());

                    bucket.setUserSetStartDate(startDate);
                    bucket.setUserSetEndDate(endDate);

                } catch (ParseException e) {
                    return;
                }

                bucket.setDuration(Integer.parseInt(dayView.getText().toString()));

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

                // TODO save item to api
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://hackmit2017.pythonanywhere.com/user/" + LoginActivity.userId
                                + "/bucket-item/" + bucket.getEndCity()
                                + "?start_date=" + simpleDateFormat1.format(bucket.getUserSetStartDate())
                                + "&end_date=" + simpleDateFormat1.format(bucket.getUserSetStartDate())
                                + "&duration=" + bucket.getDuration())
                        .put(RequestBody.create(JSON, "{}"))
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                            Log.d("res", responseBody.string());
                        }
                    }
                });

                finish();
            }
        });
    }

    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);
        return simpleDateFormat.format(date);
    }
}
