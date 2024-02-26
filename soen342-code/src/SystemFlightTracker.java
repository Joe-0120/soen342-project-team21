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

    public void viewFlights(Airport source, Airport destination){
        String type = user.getUserType();
        Airport airport = null;
        if (type.equals("airportAdmin")){
            airport = ((AirportAdministrator)user).getAirport();
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
        else if (!user.getUserType().equals("nonRegisteredEndUser")){ // User is registered
            printFullInformation(flights);
        }
        else {
            printBasicInformation(flights);
        }
    }
    public void printFullInformation(ArrayList<Flight> flights){
        for (Flight flight: flights){
            System.out.println("-----------------------------------------------------------");
            System.out.println("Basic flight information for flight: " + flight.getNumber());
            System.out.println("Source: " + flight.getSource());
            System.out.println("Destination: " + flight.getDestination());
        }
    }
    public void printBasicInformation(ArrayList<Flight> flights){
        for (Flight flight: flights){
            System.out.println("-----------------------------------------------------------");
            System.out.println("Basic flight information for flight: " + flight.getNumber());
            System.out.println("Source: " + flight.getSource());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("Scheduled departure time: " + flight.getScheduledDep());
            System.out.println("Scheduled arrival time: " + flight.getScheduledArr());
            System.out.println("Actual departure time: " + flight.getActualDep());
            System.out.println("Estimated arrival time: " + flight.getEstimatedArr());
            System.out.println("Airline: " + flight.getAirline());
            System.out.println("Aircraft: " + flight.getAircraft());
        }
    }
    public static void main(String[] args) {

    }
}