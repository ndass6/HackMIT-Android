package com.tejunareddy.hackmit2017.bucketlist.fragment.dummy;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.tejunareddy.hackmit2017.bucketlist.model.BucketListItem;
import com.tejunareddy.hackmit2017.bucketlist.model.Flight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyBucketListItems {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<BucketListItem> ITEMS = new ArrayList<BucketListItem>();

    public static final List<BucketListItem> SUGGESTIONS = new ArrayList<>();

    public static void addItem(BucketListItem item) {
        ITEMS.add(item);
    }

    public static void addSuggestion(BucketListItem item) {
        SUGGESTIONS.add(item);
    }

    public static void remove(int index) {
        ITEMS.remove(index);
    }
}
