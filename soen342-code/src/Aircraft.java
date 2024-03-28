public class Aircraft {
    private String name;
    private String status;
    private int id;
    private Airline airline;

    public Aircraft(int id, String name, String status, Airline airline) {
        this.name = name;
        this.status = status;
        this.id = id;
        this.airline = airline;
    }
    public Aircraft(int id, String name, String status) {
        this.name = name;
        this.status = status;
        this.id = id;
        this.airline = airline;
    }


    @Override
    public String toString() {
        return "Aircraft{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
