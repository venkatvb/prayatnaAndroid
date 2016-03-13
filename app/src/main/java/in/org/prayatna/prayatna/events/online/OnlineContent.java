package in.org.prayatna.prayatna.events.online;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.org.prayatna.prayatna.events.EventItem;

/**
 * Created by venkatvb on 13/3/16.
 */
public class OnlineContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<OnlineEventItem> ITEMS = new ArrayList<OnlineEventItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, OnlineEventItem> ITEM_MAP = new HashMap<String, OnlineEventItem>();

    static {
        // Add 3 sample items.
        addItem(new OnlineEventItem("1", "Item 1"));
        addItem(new OnlineEventItem("2", "Item 2"));
        addItem(new OnlineEventItem("3", "Item 3"));
    }

    private static void addItem(OnlineEventItem item) {

        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void poplateContents() {
        Firebase myFirebaseRef = new Firebase("https://prayata.firebaseio.com/");
        myFirebaseRef.child("onlineEvents").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }

            @Override public void onCancelled(FirebaseError error) { }

        });
    }
    /**
     * A dummy item representing a piece of content.
     */
    public static class OnlineEventItem extends EventItem {

        public String url;

        public OnlineEventItem(String id, String content) {
            super(id, content);
        }

        @Override
        public String toString() {
            return eventName;
        }
    }
}
