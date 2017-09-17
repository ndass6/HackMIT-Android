package com.tejunareddy.hackmit2017.bucketlist.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tejunareddy.hackmit2017.bucketlist.R;
import com.tejunareddy.hackmit2017.bucketlist.fragment.dummy.DummyBucketListItems;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;
import com.tejunareddy.hackmit2017.bucketlist.model.Flight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static int userId = -1;
    public static String startCity = "Atlanta";

    protected GeoDataClient mGeoDataClient;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.login_email);

        mPasswordView = (EditText) findViewById(R.id.login_password);

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showProgress(true);

            // example of the post request here
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, "{}");
            Request request = new Request.Builder()
                    .url("http://hackmit2017.pythonanywhere.com/login?email=" + email + "&password=" + password)
                    .post(body)
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

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {

                            LoginActivity.userId = jsonObject.getInt("user_id");

                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://hackmit2017.pythonanywhere.com/user/" + LoginActivity.userId + "/bucket-list")
                                    .get()
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

                                        JSONObject jsonObject = new JSONObject(responseBody.string());
                                        final JSONArray results = jsonObject.getJSONArray("results");
                                        final int[] count = {0};
                                        for (int i = 0; i < results.length(); i++) {
                                            count[0] = i;
                                            JSONObject result = results.getJSONObject(i);
                                            int duration = result.getInt("duration");
                                            String endCity = result.getString("end_city");

                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
                                            try {
                                                Date endDate = simpleDateFormat.parse(result.getString("end_date"));
                                                Date startDate = simpleDateFormat.parse(result.getString("start_date"));

                                                final BucketListItem bucketListItem = new BucketListItem();
                                                bucketListItem.setDuration(duration);
                                                bucketListItem.setEndCity(endCity);
                                                bucketListItem.setUserSetEndDate(endDate);
                                                bucketListItem.setUserSetStartDate(startDate);

                                                // TODO picture

                                                if (!result.has("place_id")) {
                                                    doNext(bucketListItem, count, results.length());
                                                } else {
                                                    String placeId = result.getString("place_id");

                                                    final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
                                                    photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                                                            // Get the list of photos.
                                                            PlacePhotoMetadataResponse photos = task.getResult();
                                                            // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                                                            PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                                                            // Get the first photo in the list.
                                                            PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                                                            // Get the attribution text.
                                                            CharSequence attribution = photoMetadata.getAttributions();
                                                            // Get a full-size bitmap for the photo.
                                                            Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                                                            photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                                                                    PlacePhotoResponse photo = task.getResult();
                                                                    Bitmap bitmap = photo.getBitmap();
                                                                    bucketListItem.setCityPicture(bitmap);


                                                                    doNext(bucketListItem, count, results.length());
                                                                }
                                                            });
                                                        }
                                                    });
                                                }

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mPasswordView.setError("Incorrect login information");
                                    mPasswordView.requestFocus();
                                    showProgress(false);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void doNext(final BucketListItem bucketListItem, final int[] count, final int max) {

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd", Locale.US);
        try {
            String startCity = URLEncoder.encode(LoginActivity.startCity, "UTF-8");
            String endCity = URLEncoder.encode(bucketListItem.getEndCity(), "UTF-8");

            // best route api
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://hackmit2017.pythonanywhere.com/calculate-price?start_city=" + startCity
                            + "&end_city=" + endCity
                            + "&start_date=" + simpleDateFormat.format(bucketListItem.getUserSetStartDate())
                            + "&end_date=" + simpleDateFormat.format(bucketListItem.getUserSetEndDate())
                            + "&duration=" + bucketListItem.getDuration())
                    .get()
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

                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        bucketListItem.setDuration(Integer.parseInt(jsonObject.getString("duration")));
                        bucketListItem.setStartDate(simpleDateFormat.parse(jsonObject.getString("start_date")));
                        bucketListItem.setEndDate(simpleDateFormat.parse(jsonObject.getString("end_date")));

                        List<Flight> flights = new ArrayList<Flight>();
                        JSONObject departureFlightJson = jsonObject.getJSONObject("departure_flight");
                        Flight flight1 = new Flight();
                        flight1.setPrice(Integer.parseInt(departureFlightJson.getString("price").split(".")[0]));
                        flight1.setDepartDate(simpleDateFormat.parse(departureFlightJson.getString("departure_date")));
                        flight1.setArrivalAirport(departureFlightJson.getString("destination"));
                        flight1.setAirline(departureFlightJson.getString("airline"));
                        flights.add(flight1);

                        JSONObject returnFlightJson = jsonObject.getJSONObject("return_flight");
                        Flight flight2 = new Flight();
                        flight2.setPrice(Integer.parseInt(returnFlightJson.getString("price").split(".")[0]));
                        flight2.setDepartDate(simpleDateFormat.parse(returnFlightJson.getString("departure_date")));
                        flight2.setArrivalAirport(returnFlightJson.getString("destination"));
                        flight2.setAirline(returnFlightJson.getString("airline"));
                        flights.add(flight2);

                        flight1.setDepartAirport(returnFlightJson.getString("destination"));
                        flight2.setDepartAirport(departureFlightJson.getString("destination"));

                        bucketListItem.setPrice(flight1.getPrice() + flight2.getPrice());
                        bucketListItem.setFlightInfo(flights);
                        // Do the rest of the stuff
                        DummyBucketListItems.addItem(bucketListItem);

                        // Note this is before the transition to the main activity
                        // Go to the next activity here
                        // pass in user id
                        if (count[0] == max - 1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showProgress(false);
                                }
                            });
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmailValid(String email) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        mProgressView.setIndeterminate(true);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}

