package in.org.prayatna.prayatna;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class OnlineEventsActivity extends AppCompatActivity {

    private static final String FIREBASE_URL = "https://prayatna.firebaseio.com/online";
    private static final String ONLINE_EVENTS = "onlineEvents";
    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private EventListAdapter mEventListAdapter;
    private Snackbar mSnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_online_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseRef = new Firebase(FIREBASE_URL).child(ONLINE_EVENTS);
        setTitle("Online Events");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onStart() {
        super.onStart();
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setFocusable(false);
        mEventListAdapter = new EventListAdapter(mFirebaseRef, this, R.layout.content_online_events, "venkat");
        View view = findViewById(android.R.id.content);
        mSnackbar = Snackbar.make(view, "Connecting...", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null);
        listView.setAdapter(mEventListAdapter);
        mEventListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mEventListAdapter.getCount() - 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event item = (Event) parent.getItemAtPosition(position);

                Intent intent = new Intent(OnlineEventsActivity.this, EventPageActivity.class);
                //based on item add info to intent
                intent.putExtra("url", FIREBASE_URL + "/" + ONLINE_EVENTS + "/" + item.getId());
                intent.putExtra("title", item.getName());
                startActivity(intent);
            }

        });

        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    mSnackbar.dismiss();
                    Toast.makeText(OnlineEventsActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    mSnackbar.show();
                    Toast.makeText(OnlineEventsActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }
}
