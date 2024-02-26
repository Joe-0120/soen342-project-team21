import java.util.ArrayList;

public class AirlineCatalog {
    private ArrayList<Airline> airlines = new ArrayList<Airline>();

    public AirlineCatalog() {
    }

    public ArrayList<Airline> getAirlines() {
        return airlines;
    }

    public void setAirlines(ArrayList<Airline> airlines) {
        this.airlines = airlines;
    }
}
