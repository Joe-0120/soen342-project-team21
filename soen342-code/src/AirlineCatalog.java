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

    public Airline getAirline(String name) {
        for (Airline airline : airlines) {
            if (airline.getName().equals(name)) {
                return airline;
            }
        }
        return null; // If airport with the given code is not found
    }
}
