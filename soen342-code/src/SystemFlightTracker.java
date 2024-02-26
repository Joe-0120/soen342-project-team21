import java.lang.reflect.Array;
import java.util.ArrayList;

public class SystemFlightTracker {
    private User user;
    private ArrayList<SystemAdministrator> systemAdministrators = new ArrayList<SystemAdministrator>();
    private AirportCatalog airportCatalog;
    private FlightCatalog flightCatalog;
    private AirlineCatalog airlineCatalog;

    public SystemFlightTracker(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<SystemAdministrator> getSystemAdministrators() {
        return systemAdministrators;
    }

    public void setSystemAdministrators(ArrayList<SystemAdministrator> systemAdministrators) {
        this.systemAdministrators = systemAdministrators;
    }

    public AirportCatalog getAirportCatalog() {
        return airportCatalog;
    }

    public void setAirportCatalog(AirportCatalog airportCatalog) {
        this.airportCatalog = airportCatalog;
    }

    public FlightCatalog getFlightCatalog() {
        return flightCatalog;
    }

    public void setFlightCatalog(FlightCatalog flightCatalog) {
        this.flightCatalog = flightCatalog;
    }

    public AirlineCatalog getAirlineCatalog() {
        return airlineCatalog;
    }

    public void setAirlineCatalog(AirlineCatalog airlineCatalog) {
        this.airlineCatalog = airlineCatalog;
    }

    public void viewFlights(Airport source, Airport destination, User user){
        String type = user.getUserType();
        Airport airport = null;
        if (type.equals("airportAdmin")){
            airport = user.getAirport();
        }
        ArrayList<Flight> flights;
        if (type.equals("airportAdmin")){
            flights = flightCatalog.getFlights(airport, source, destination);
        }
        else {
            flights = flightCatalog.getFlights(source, destination);
        }
        if (flights.isEmpty()){
            System.out.println("No flights found");
        }
        else if (user.isRegistered()){
            printFullInformation(flights);
        }
        else {
            printBasicInformation(flights);
        }
    }
    public void printFullInformation(ArrayList<Flight>){

    }
    public void printBasicInformation(ArrayList<Flight>){

    }
    public static void main(String[] args) {

    }
}