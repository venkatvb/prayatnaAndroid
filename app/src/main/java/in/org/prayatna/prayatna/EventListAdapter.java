package in.org.prayatna.prayatna;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.squareup.picasso.Picasso;

/**
 * Created by venkatvb on 13/3/16.
 */
public class EventListAdapter extends FirebaseListAdapter<Event> {
    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public EventListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Event.class, layout, activity);
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Event event) {
        // Map a Chat object to an entry in our listview
        String author = event.getName();
        TextView authorText = (TextView) view.findViewById(R.id.eventname);
        authorText.setText(author);
        // Set the color
        authorText.setTextColor(Color.BLUE);
        ImageView eventImage = (ImageView) view.findViewById(R.id.eventImage);
        Picasso.with(eventImage.getContext()).load(event.getImg()).resize(75, 75).into(eventImage);
    }
}
