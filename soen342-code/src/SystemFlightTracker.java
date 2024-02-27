import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemFlightTracker {
    private static User user;
    private static ArrayList<SystemAdministrator> systemAdministrators = new ArrayList<SystemAdministrator>();
    private static AirportCatalog airportCatalog;
    private static FlightCatalog flightCatalog;
    private static AirlineCatalog airlineCatalog;

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

    public static void viewFlights(String sourceCode, String destinationCode){
        Airport source = airportCatalog.getAirport(sourceCode);
        if (source == null){
            System.out.println("No airport with code " + sourceCode + " was found.");
        }
        Airport destination = airportCatalog.getAirport(destinationCode);
        if (destination == null){
            System.out.println("No airport with code " + destinationCode + " was found.");
        }
        String type = user.getUserType();
        Airport airport = null;
        if (type.equals("airportAdmin")){
            airport = ((AirportAdministrator)user).getAirport();
        }
        ArrayList<Flight> flights;
        if (type.equals("airportAdmin")){
            flights = (ArrayList<Flight>) flightCatalog.getFlights(airport, source, destination);
        }
        else {
            flights = (ArrayList<Flight>) flightCatalog.getFlights(source, destination);
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
    public static void printBasicInformation(ArrayList<Flight> flights){
        for (Flight flight: flights){
            System.out.println("-----------------------------------------------------------");
            System.out.println("Basic flight information for flight: " + flight.getNumber());
            System.out.println("Source: " + flight.getSource());
            System.out.println("Destination: " + flight.getDestination());
        }
    }
    public static void printFullInformation(ArrayList<Flight> flights){
        for (Flight flight: flights){
            System.out.println("-----------------------------------------------------------");
            System.out.println("Full flight information for flight: " + flight.getNumber());
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
    
    public static void loadData() {
      // Create Airlines
      Airline globalAirways = new Airline("Global Airways");
      Airline skylineFlights = new Airline("Skyline Flights");
      Airline oceanicAirlines = new Airline("Oceanic Airlines");

      ArrayList<Aircraft> globalFleet = new ArrayList<>();
      ArrayList<Aircraft> skylineFleet = new ArrayList<>();
      ArrayList<Aircraft> oceanicFleet = new ArrayList<>();
  
      // Create Aircraft and assign to airlines
      globalFleet.add(new Aircraft("Glider One", "Active", "A1", globalAirways));
      globalFleet.add(new Aircraft("Sky Cruiser", "Active", "A2", globalAirways));
      globalAirways.setFleet(globalFleet);

      skylineFleet.add(new Aircraft("Jet Streamer", "Maintenance", "S1", skylineFlights));
      skylineFleet.add(new Aircraft("Cloud Hopper", "Active", "S2", skylineFlights));
      skylineFlights.setFleet(skylineFleet);

      oceanicFleet.add(new Aircraft("Sea Breeze", "Active", "O1", oceanicAirlines));
      oceanicAirlines.setFleet(oceanicFleet);

      airlineCatalog = new AirlineCatalog();
      ArrayList<Airline> airlines = new ArrayList<>();
      airlines.add(globalAirways);
      airlines.add(skylineFlights);
      airlines.add(oceanicAirlines);
      airlineCatalog.setAirlines(airlines);

      airportCatalog = new AirportCatalog();
      ArrayList<Airport> airports = new ArrayList<>();

      City newYork = new City("New York", "USA", 16.0);
      City tokyo = new City("Tokyo", "Japan", 20.0);
      City paris = new City("Paris", "France", 18.0);
      City sydney = new City("Sydney", "Australia", 22.0);
      City cairo = new City("Cairo", "Egypt", 30.0);

      Airport jfk = new Airport("John F. Kennedy International Airport", "JFK", newYork);
      Airport narita = new Airport("Narita International Airport", "NRT", tokyo);
      Airport charlesDeGaulle = new Airport("Charles de Gaulle Airport", "CDG", paris);
      Airport sydneyAirport = new Airport("Sydney Airport", "SYD", sydney);
      Airport cairoAirport = new Airport("Cairo International Airport", "CAI", cairo);

      airports.add(jfk);
      airports.add(narita);
      airports.add(charlesDeGaulle);
      airports.add(sydneyAirport);
      airports.add(cairoAirport);
      systemAdministrators.add(new SystemAdministrator("John", "Doe", 0));

      flightCatalog = new FlightCatalog();
      ArrayList<Flight> flights = new ArrayList<>();
      flights.add(new Commercial("GA101", jfk, narita, LocalDateTime.of(2024, 3, 14, 10, 0), LocalDateTime.of(2024, 3, 14, 10, 15), LocalDateTime.of(2024, 3, 15, 12, 0), LocalDateTime.of(2024, 3, 15, 12, 20), globalAirways, globalFleet.get(0)));
      flights.add(new Commercial("SF202", charlesDeGaulle, sydneyAirport, LocalDateTime.of(2024, 4, 20, 9, 0), LocalDateTime.of(2024, 4, 20, 9, 30), LocalDateTime.of(2024, 4, 21, 22, 0), LocalDateTime.of(2024, 4, 21, 22, 45), skylineFlights, skylineFleet.get(1)));
      flights.add(new Cargo("OA303", cairoAirport, jfk, LocalDateTime.of(2024, 5, 5, 18, 0), LocalDateTime.of(2024, 5, 5, 18, 20), LocalDateTime.of(2024, 5, 6, 3, 0), LocalDateTime.of(2024, 5, 6, 3, 30), oceanicAirlines, oceanicFleet.get(0)));
      flights.add(new Cargo("GA404", sydneyAirport, charlesDeGaulle, LocalDateTime.of(2024, 6, 10, 13, 0), LocalDateTime.of(2024, 6, 10, 13, 10), LocalDateTime.of(2024, 6, 11, 7, 0), LocalDateTime.of(2024, 6, 11, 7, 15), globalAirways, globalFleet.get(1)));
      flights.add(new PrivateFlight("SF505", narita, cairoAirport, LocalDateTime.of(2024, 7, 15, 21, 0), LocalDateTime.of(2024, 7, 15, 21, 15), LocalDateTime.of(2024, 7, 16, 5, 0), LocalDateTime.of(2024, 7, 16, 5, 25), skylineFlights, skylineFleet.get(0)));

      flightCatalog.setFlights(flights);
      airportCatalog.setAirports(airports);
    }

    public static void main(String[] args) {
      loadData();
      System.out.println("-----------------------------------------------------------");
      System.out.println(" ----------------         Welcome         ----------------");
      System.out.println("-----------------------------------------------------------");
  
      Scanner scanner = new Scanner(System.in);
  
      System.out.println("Please select your user type:");
      System.out.println("1. Non-Registered User");
      System.out.println("2. Registered User");
      System.out.println("3. Airline Administrator");
      System.out.println("4. Airport Administrator");
      System.out.println("5. System Administrator");
  
      System.out.print("Enter your choice (1-5): ");
  
      int userType = scanner.nextInt();
      scanner.nextLine(); // Consume the newline left-over
  
      switch (userType) {
        case 1:
          System.out.println("Non-Registered User Selected.");
          user = new NonRegisteredEndUser(1);
          break;
        case 2:
          System.out.println("Registered User Selected.");
          user = new RegisteredEndUser("username", "password", 1);
          break;
        case 3:
          System.out.println("Airline Administrator Selected.");
          user = new AirlineAdministrator("username", "password", 1, airlineCatalog.getAirlines().get(0));
          break;
        case 4:
          System.out.println("Airport Administrator Selected.");
          user = new AirportAdministrator("username", "password", 1, airportCatalog.getAirports().get(0));
          break;
        case 5:
          System.out.println("System Administrator Selected.");
          user = new SystemAdministrator("username", "password", 1);
          break;
        default:
          System.out.println("Invalid choice. Please enter a number between 1 and 5.");
          return;
      }
  
      // Ask for source and destination airport codes
      System.out.print("Enter source airport code: ");
      String sourceCode = scanner.nextLine();
  
      System.out.print("Enter destination airport code: ");
      String destinationCode = scanner.nextLine();
  
      viewFlights(sourceCode, destinationCode);

      scanner.close();
  }
  
}