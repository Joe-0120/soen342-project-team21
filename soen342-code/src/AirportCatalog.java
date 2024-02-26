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

    public Airport getAirport(String code) {
        for (Airport airport : airports) {
            if (airport.getCode().equals(code)) {
                return airport;
            }
        }
        return null; // If airport with the given code is not found
    }
}
