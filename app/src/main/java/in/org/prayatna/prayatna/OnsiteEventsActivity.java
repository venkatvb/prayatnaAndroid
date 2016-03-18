package in.org.prayatna.prayatna;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class OnsiteEventsActivity extends AppCompatActivity {

    private static final String FIREBASE_URL = "https://prayatna.firebaseio.com/onsite";
    private static final String ONSITE_EVENTS = "onsiteEvents";
    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private EventListAdapter mEventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mFirebaseRef = new Firebase(FIREBASE_URL).child(ONSITE_EVENTS);
        setTitle("Onsite Events");
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
        mEventListAdapter = new EventListAdapter(mFirebaseRef, this, R.layout.content_online_events, "venkat");
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

                Intent intent = new Intent(OnsiteEventsActivity.this, EventPageActivity.class);
                //based on item add info to intent
                intent.putExtra("url", FIREBASE_URL + "/" + ONSITE_EVENTS + "/" + item.getId());
                intent.putExtra("title", item.getName());
                startActivity(intent);
            }

        });

        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(OnsiteEventsActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OnsiteEventsActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }
}
