package in.org.prayatna.prayatna;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventPageActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mDat;
    TextView mDesc;
    TextView mVenue;
    TextView mResult;
    TextView mOrganizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = (ImageView) findViewById(R.id.backdropimage);
        mDesc = (TextView) findViewById(R.id.desc_event_text_view);
        mVenue = (TextView) findViewById(R.id.venue_event_text_view);
        mResult = (TextView) findViewById(R.id.results_event_text_view);
        mOrganizer = (TextView) findViewById(R.id.organizer_event_text_view);

        mImageView.setAlpha(50);
        final String url = getIntent().getStringExtra("url");
        final String title = getIntent().getStringExtra("title");
        setTitle(title);

        Firebase ref = new Firebase(url);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                final Event event = snapshot.getValue(Event.class);
                Log.d("url", url);
                Log.d("event name", event.getName());
                mDesc.setText(event.getDesc());
                mDesc.setText(event.getDesc());
                mVenue.setText(event.getLocation());
                mResult.setText(event.getResult());
                mOrganizer.setText(event.getOrganizer());
                Picasso.with(mImageView.getContext()).load(event.getImg()).resize(175, 175).into(mImageView);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+event.getContact()));
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

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
}
