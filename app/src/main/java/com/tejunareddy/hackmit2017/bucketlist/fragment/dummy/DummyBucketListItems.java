package com.tejunareddy.hackmit2017.bucketlist.fragment.dummy;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<BucketListItem> ITEMS = new ArrayList<BucketListItem>();
    private static final int COUNT = 25;
    private GoogleApiClient mGoogleApiClient;

    public static void addItem(BucketListItem item) {
        ITEMS.add(item);
    }

    public static BucketListItem createDummyItem(Place place) {
        BucketListItem bucketListItem = new BucketListItem();
        bucketListItem.setAlert(true);
        bucketListItem.setCityPicture(null);
        bucketListItem.setEndCity(place.getName().toString());
        bucketListItem.setEndDate(new Date());
        bucketListItem.setStartDate(new Date());
        bucketListItem.setMaxDuration(5);
        bucketListItem.setMinDuration(3);
        bucketListItem.setPrice(100);
        bucketListItem.setStarred(false);
        bucketListItem.setPlaceId(place.getId());

        return bucketListItem;
    }

    public static void remove(int index) {
        ITEMS.remove(index);
    }
}
