import java.util.ArrayList;

public class AirportCatalog {
    private ArrayList<Airport> airports = new ArrayList<Airport>();

    public AirportCatalog() {
    }

    public ArrayList<Airport> getAirports() {
        return airports;
    }

    public void setAirports(ArrayList<Airport> airports) {
        this.airports = airports;
    }
}
