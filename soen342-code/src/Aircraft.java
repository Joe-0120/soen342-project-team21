public class Aircraft {
    private String name;
    private String status;
    private String id;
    private Airline airline;

    public Aircraft(String name, String status, String id, Airline airline) {
        this.name = name;
        this.status = status;
        this.id = id;
        this.airline = airline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
