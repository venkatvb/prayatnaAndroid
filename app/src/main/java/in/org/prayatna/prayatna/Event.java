package in.org.prayatna.prayatna;

/**
 * Created by venkatvb on 13/3/16.
 */
public class Event {

    private String name;
    private String result;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Event() {
    }

    Event(String name, String result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }
}
