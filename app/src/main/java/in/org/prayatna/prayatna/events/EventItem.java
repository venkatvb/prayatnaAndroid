package in.org.prayatna.prayatna.events;

/**
 * Created by venkatvb on 13/3/16.
 */
public class EventItem {
    public String id;
    public String eventName;
    public String eventDescription;
    public String date;
    public String time;
    public String location;
    public String organizerName;

    public EventItem(String id, String eventName) {
        this.id = id;
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return eventName;
    }
}
