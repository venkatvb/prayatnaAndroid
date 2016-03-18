package in.org.prayatna.prayatna;

/**
 * Created by venkatvb on 13/3/16.
 */
public class Event {

    private String name;
    private String desc;
    private String dat;
    private String location;
    private String organizer;
    private String contact;
    private String result;
    private String img;
    private String id;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Event() {
    }

    Event(String id, String name, String desc, String dat, String location, String organizer, String contact, String result, String img) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.location = location;
        this.organizer = organizer;
        this.contact = contact;
        this.result = result;
        this.img = img;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return  desc;
    }

    public String getLocation() {
        return location;
    }

    public String getDat() {
        return dat;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getContact() {
        return contact;
    }

    public String getImg() {
        return img;
    }

    public String getResult() {
        return result;
    }
}
