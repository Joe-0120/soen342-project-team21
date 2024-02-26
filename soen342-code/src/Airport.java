import java.util.ArrayList;

public class Airport {
    private String name;
    private String code;
    private City city;
    private ArrayList<AirportAdministrator> administrators  = new ArrayList<AirportAdministrator>();
    private ArrayList<Flight> sourceFlights = new ArrayList<Flight>();
    private ArrayList<Flight> destinationFlights = new ArrayList<Flight>();
    private ArrayList<Flight> handledPrivateFlights = new ArrayList<Flight>();

    public Airport(String name, String code, City city) {
        this.name = name;
        this.code = code;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<AirportAdministrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(ArrayList<AirportAdministrator> administrators) {
        this.administrators = administrators;
    }

    public ArrayList<Flight> getSourceFlights() {
        return sourceFlights;
    }

    public void setSourceFlights(ArrayList<Flight> sourceFlights) {
        this.sourceFlights = sourceFlights;
    }

    public ArrayList<Flight> getDestinationFlights() {
        return destinationFlights;
    }

    public void setDestinationFlights(ArrayList<Flight> destinationFlights) {
        this.destinationFlights = destinationFlights;
    }

    public ArrayList<Flight> getHandledPrivateFlights() {
        return handledPrivateFlights;
    }

    public void setHandledPrivateFlights(ArrayList<Flight> handledPrivateFlights) {
        this.handledPrivateFlights = handledPrivateFlights;
    }
}
