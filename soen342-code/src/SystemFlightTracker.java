import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SystemFlightTracker {
    private static User user;
    private static ArrayList<SystemAdministrator> systemAdministrators = new ArrayList<SystemAdministrator>();
    private static AirportCatalog airportCatalog;
    private static FlightCatalog flightCatalog;
    private static AirlineCatalog airlineCatalog;
    private static CityCatalog cityCatalog;

    public SystemFlightTracker() {

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

    public static CityCatalog getCityCatalog() {
        return cityCatalog;
    }

    public static void setCityCatalog(CityCatalog cityCatalog) {
        SystemFlightTracker.cityCatalog = cityCatalog;
    }

    public static void viewFlights(String sourceCode, String destinationCode) {
        Airport source = airportCatalog.getAirport(sourceCode);
        if (source == null) {
            System.out.println("No airport with code " + sourceCode + " was found.");
        }
        Airport destination = airportCatalog.getAirport(destinationCode);
        if (destination == null) {
            System.out.println("No airport with code " + destinationCode + " was found.");
        }
        String type = user.getUserType();
        Airport airport = null;
        if (type.equals("airportAdmin")) {
            airport = ((AirportAdministrator) user).getAirport();
        }
        ArrayList<Flight> flights;
        if (type.equals("airportAdmin")) {
            flights = (ArrayList<Flight>) flightCatalog.getFlights(airport, source, destination);
        } else {
            flights = (ArrayList<Flight>) flightCatalog.getFlights(source, destination);
        }
        if (flights.isEmpty()) {
            System.out.println("No flights found");
        } else if (!user.getUserType().equals("nonRegisteredEndUser")) { // User is registered
            printFullInformation(flights);
        } else {
            printBasicInformation(flights);
        }
    }

    public static void printBasicInformation(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Basic flight information for flight: " + flight.getNumber());
            System.out.println("Source: " + flight.getSource());
            System.out.println("Destination: " + flight.getDestination());
        }
    }

    public static void printFullInformation(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
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
    public static void loadDataDB(Connection conn) throws SQLException{
        systemAdministrators.add(new SystemAdministrator("John", "Doe", 0));

        // Fetching cities
        cityCatalog = new CityCatalog();
        ArrayList<City> cities = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM City");
            while (rs.next()) {
                City city = new City(
                        rs.getInt("city_id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getDouble("temp")
                );
                cities.add(city);
                System.out.println(city.getTemperature());
            }
        }
        cityCatalog.setCities(cities);
        System.out.println(cityCatalog.getCities());

        // Fetching airlines
        airlineCatalog = new AirlineCatalog();
        ArrayList<Airline> airlines = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Airline");
            while (rs.next()) {
                Airline airline = new Airline(
                        rs.getInt("airline_id"),
                        rs.getString("name")
                );
                airlines.add(airline);
            }
        }
        airlineCatalog.setAirlines(airlines);
        System.out.println(airlineCatalog.getAirlines());

        // Fetch aircrafts and add them to the respective airline's fleet
        try (Statement stmt = conn.createStatement()) {
            ResultSet rsAircrafts = stmt.executeQuery("SELECT * FROM Aircraft");
            while (rsAircrafts.next()) {
                Aircraft aircraft = new Aircraft(
                        rsAircrafts.getInt("aircraft_id"),
                        rsAircrafts.getString("name"),
                        rsAircrafts.getString("status")
                );
                int airlineId = rsAircrafts.getInt("airline_id");
                for (Airline airline : airlineCatalog.getAirlines()) {
                    if (airline.getId() == airlineId) {
                        airline.getFleet().add(aircraft);
                        aircraft.setAirline(airline); // Set the airline reference in the aircraft
                        break;
                    }
                }
            }
            for (Airline airline: airlineCatalog.getAirlines()){
                System.out.println(airline);
                System.out.println(airline.getFleet());
            }
        }

        // Fetch airports
        ArrayList<Airport> airports = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rsAirports = stmt.executeQuery("SELECT * FROM Airport");
            while (rsAirports.next()) {
                Airport airport = new Airport(
                        rsAirports.getInt("airport_id"),
                        rsAirports.getString("name"),
                        rsAirports.getString("code")
                );
                int cityId = rsAirports.getInt("city_id");
                // Find the city associated with the airport
                City city = null;
                for (City c : cities) {
                    if (c.getId() == cityId) {
                        city = c;
                        break;
                    }
                }
                if (city != null) {
                    airport.setCity(city);
                    airports.add(airport);
                }
            }
            airportCatalog = new AirportCatalog();
            airportCatalog.setAirports(airports);
            for (Airport airport: airportCatalog.getAirports()){
                System.out.println(airport);
            }
        }

        // Fetch flights and associate them with airports, airlines, and aircraft
        ArrayList<Flight> flights = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rsFlights = stmt.executeQuery("SELECT * FROM Flight");
            while (rsFlights.next()) {
                int sourceAirportId = rsFlights.getInt("source");
                int destinationAirportId = rsFlights.getInt("destination");
                int airlineId = rsFlights.getInt("airline_id");
                int aircraftId = rsFlights.getInt("aircraft_id");

                // Find source and destination airports, airline, and aircraft
                Airport sourceAirport = null;
                Airport destinationAirport = null;
                Airline airline = null;
                Aircraft aircraft = null;

                for (Airport airport : airportCatalog.getAirports()) {
                    if (airport.getId() == sourceAirportId) {
                        sourceAirport = airport;
                    } else if (airport.getId() == destinationAirportId) {
                        destinationAirport = airport;
                    }
                }

                for (Airline a : airlineCatalog.getAirlines()) {
                    if (a.getId() == airlineId) {
                        airline = a;
                    }
                }

                if (airline != null){
                    for (Aircraft a : airline.getFleet()) {
                        if (a.getId() == aircraftId) {
                            aircraft = a;
                        }
                    }
                }

                if (sourceAirport != null && destinationAirport != null && airline != null && aircraft != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    switch (rsFlights.getString("type")) {
                      case "Commercial":
                        Flight commercialFlight = new Commercial(
                          rsFlights.getInt("flight_id"),
                          rsFlights.getString("number"),
                          sourceAirport,
                          destinationAirport,
                          LocalDateTime.parse(rsFlights.getString("scheduledDep"), formatter),
                          LocalDateTime.parse(rsFlights.getString("actualDep"), formatter),
                          LocalDateTime.parse(rsFlights.getString("scheduledArr"), formatter),
                          LocalDateTime.parse(rsFlights.getString("estimatedArr"), formatter),
                          airline,
                          aircraft
                        );
                        flights.add(commercialFlight);
                        break;
                      case "Cargo":
                        Flight cargoFlight = new Cargo(
                          rsFlights.getInt("flight_id"),
                          rsFlights.getString("number"),
                          sourceAirport,
                          destinationAirport,
                          LocalDateTime.parse(rsFlights.getString("scheduledDep"), formatter),
                          LocalDateTime.parse(rsFlights.getString("actualDep"), formatter),
                          LocalDateTime.parse(rsFlights.getString("scheduledArr"), formatter),
                          LocalDateTime.parse(rsFlights.getString("estimatedArr"), formatter),
                          airline,
                          aircraft
                        );
                        flights.add(cargoFlight);
                        break;
                      case "Private":
                        Flight privateFlight = new PrivateFlight(
                          rsFlights.getInt("flight_id"),
                          rsFlights.getString("number"),
                          sourceAirport,
                          destinationAirport,
                          LocalDateTime.parse(rsFlights.getString("scheduledDep"), formatter),
                          LocalDateTime.parse(rsFlights.getString("actualDep"), formatter),
                          LocalDateTime.parse(rsFlights.getString("scheduledArr"), formatter),
                          LocalDateTime.parse(rsFlights.getString("estimatedArr"), formatter),
                          null,
                          aircraft
                        );
                        flights.add(privateFlight);
                        break;
                    }
                }
            }
        }

        // Now you have populated flights associated with airports, airlines, and aircraft
        // You can set the flights in the flightCatalog
        flightCatalog = new FlightCatalog();
        flightCatalog.setFlights(flights);
        for (Flight flight: flightCatalog.getFlights()){
            System.out.println(flight);
        }

    }
//    public static void loadData(){
//        cityCatalog = new CityCatalog();
//        ArrayList<City> cities = new ArrayList<>();
//        City newYork = new City(1,"New York", "USA", 16.0);
//        City tokyo = new City(2,"Tokyo", "Japan", 20.0);
//        City paris = new City(3,"Paris", "France", 18.0);
//        City sydney = new City(4,"Sydney", "Australia", 22.0);
//        City cairo = new City(5,"Cairo", "Egypt", 30.0);
//        cities.add(newYork);
//        cities.add(tokyo);
//        cities.add(paris);
//        cities.add(sydney);
//        cities.add(cairo);
//        cityCatalog.setCities(cities);
//        // Create Airlines
//        Airline globalAirways = new Airline("Global Airways");
//        Airline skylineFlights = new Airline("Skyline Flights");
//        Airline oceanicAirlines = new Airline("Oceanic Airlines");
//
//        ArrayList<Aircraft> globalFleet = new ArrayList<>();
//        ArrayList<Aircraft> skylineFleet = new ArrayList<>();
//        ArrayList<Aircraft> oceanicFleet = new ArrayList<>();
//
//        // Create Aircraft and assign to airlines
//        globalFleet.add(new Aircraft("Glider One", "JFK", "A1", globalAirways));
//        globalFleet.add(new Aircraft("Sky Cruiser", "JFK", "A2", globalAirways));
//        globalAirways.setFleet(globalFleet);
//
//        skylineFleet.add(new Aircraft("Jet Streamer", "Maintenance", "S1", skylineFlights));
//        skylineFleet.add(new Aircraft("Cloud Hopper", "SYD", "S2", skylineFlights));
//        skylineFlights.setFleet(skylineFleet);
//
//        oceanicFleet.add(new Aircraft("Sea Breeze", "NRT", "O1", oceanicAirlines));
//        oceanicAirlines.setFleet(oceanicFleet);
//
//        airlineCatalog = new AirlineCatalog();
//        ArrayList<Airline> airlines = new ArrayList<>();
//        airlines.add(globalAirways);
//        airlines.add(skylineFlights);
//        airlines.add(oceanicAirlines);
//        airlineCatalog.setAirlines(airlines);
//
//        airportCatalog = new AirportCatalog();
//        ArrayList<Airport> airports = new ArrayList<>();
//        Airport jfk = new Airport("John F. Kennedy International Airport", "JFK", newYork);
//        Airport narita = new Airport("Narita International Airport", "NRT", tokyo);
//        Airport charlesDeGaulle = new Airport("Charles de Gaulle Airport", "CDG", paris);
//        Airport sydneyAirport = new Airport("Sydney Airport", "SYD", sydney);
//        Airport cairoAirport = new Airport("Cairo International Airport", "CAI", cairo);
//        airports.add(jfk);
//        airports.add(narita);
//        airports.add(charlesDeGaulle);
//        airports.add(sydneyAirport);
//        airports.add(cairoAirport);

//        systemAdministrators.add(new SystemAdministrator("John", "Doe", 0));
//
//        flightCatalog = new FlightCatalog();
//        ArrayList<Flight> flights = new ArrayList<>();
//        flights.add(new Commercial("GA101", jfk, narita, LocalDateTime.of(2024, 3, 14, 10, 0), LocalDateTime.of(2024, 3, 14, 10, 15), LocalDateTime.of(2024, 3, 15, 12, 0), LocalDateTime.of(2024, 3, 15, 12, 20), globalAirways, globalFleet.get(0)));1
//        flights.add(new Commercial("SF202", charlesDeGaulle, sydneyAirport, LocalDateTime.of(2024, 4, 20, 9, 0), LocalDateTime.of(2024, 4, 20, 9, 30), LocalDateTime.of(2024, 4, 21, 22, 0), LocalDateTime.of(2024, 4, 21, 22, 45), skylineFlights, skylineFleet.get(1)));4
//        flights.add(new Cargo("OA303", cairoAirport, jfk, LocalDateTime.of(2024, 5, 5, 18, 0), LocalDateTime.of(2024, 5, 5, 18, 20), LocalDateTime.of(2024, 5, 6, 3, 0), LocalDateTime.of(2024, 5, 6, 3, 30), oceanicAirlines, oceanicFleet.get(0)));5
//        flights.add(new Cargo("GA404", sydneyAirport, charlesDeGaulle, LocalDateTime.of(2024, 6, 10, 13, 0), LocalDateTime.of(2024, 6, 10, 13, 10), LocalDateTime.of(2024, 6, 11, 7, 0), LocalDateTime.of(2024, 6, 11, 7, 15), globalAirways, globalFleet.get(1)));2
//        flights.add(new PrivateFlight("SF505", narita, cairoAirport, LocalDateTime.of(2024, 7, 15, 21, 0), LocalDateTime.of(2024, 7, 15, 21, 15), LocalDateTime.of(2024, 7, 16, 5, 0), LocalDateTime.of(2024, 7, 16, 5, 25), skylineFlights, skylineFleet.get(0)));3
//
//        flightCatalog.setFlights(flights);
//        airportCatalog.setAirports(airports);
//    }

    public static void registerFlight(String type, String number, Airport source, Airport destination, LocalDateTime scheduledDep, LocalDateTime scheduledArr, Airline airline) {
        if (!flightCatalog.checkDepUnique(source, scheduledDep)){
            System.out.println("Error: Departure time taken by other flight at the source airport.");
            return;
        }
        if (!flightCatalog.checkArrUnique(destination, scheduledArr)){
            System.out.println("Error: Arrival time taken by other flight at the destination airport");
            return;
        }
        if (user.getUserType().equals("airlineAdmin")){
            Aircraft aircraft = airline.getAircraft(source);
            if (aircraft == null){
                System.out.println("Error: No available aircrafts from fleet in the source airport");
                return;
            }
            String confirmation = flightCatalog.addFlight(type, number, source, destination, scheduledDep, scheduledArr, airline, aircraft);
            System.out.println(confirmation);
        }
        else if(user.getUserType().equals("airportAdmin")){
            String confirmation = flightCatalog.addFlight(number, source, destination, scheduledDep, scheduledArr);
            System.out.println(confirmation);
        }

    }

    public static void addAirport(String name, String code, String cityName){
        City city = cityCatalog.getCity(cityName);
        if (city == null){
            System.out.println("Error: City does not exist");
        }
        else {
            airportCatalog.addAirport(name, code, city);
        }
    }
    public static LocalDateTime getTimeUser(Scanner scanner) {

        System.out.println("Enter year:");
        int year = scanner.nextInt();

        System.out.println("Enter month (1-12):");
        int month = scanner.nextInt();

        System.out.println("Enter day of month:");
        int dayOfMonth = scanner.nextInt();

        System.out.println("Enter hour (0-23):");
        int hour = scanner.nextInt();

        System.out.println("Enter minute (0-59):");
        int minute = scanner.nextInt();


        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    public static void insertFlightTest() {
      String insertSql = "INSERT INTO Flight (source, destination, number, scheduledDep, actualDep, scheduledArr, estimatedArr, type, airline_id, aircraft_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *;";
      try (Connection conn = DriverManager.getConnection("jdbc:sqlite:FlightTracker.db")) {
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
          // Assuming you have values for these parameters
          pstmt.setInt(1, 1); // Replace sourceId with the actual source ID
          pstmt.setInt(2, 2); // Replace destinationId with the actual destination ID
          pstmt.setString(3, "CACA777"); // Flight number example
          pstmt.setString(4, "2023-10-01 08:00"); // Scheduled departure
          pstmt.setString(5, "2023-10-01 08:10"); // Actual departure
          pstmt.setString(6, "2023-10-01 12:00"); // Scheduled arrival
          pstmt.setString(7, "2023-10-01 11:50"); // Estimated arrival
          pstmt.setString(8, "Domestic"); // Type
          pstmt.setInt(9, 3); // Airline ID
          pstmt.setInt(10, 5); // Aircraft ID

          try (ResultSet rs = pstmt.executeQuery()) {
              if (rs.next()) {
                  // Output the inserted row or specific columns as needed
                  System.out.println("Inserted Flight ID: " + rs.getInt("flight_id"));
              }
          }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }
    }

    public static void main(String[] args) {
      System.out.println("Working Directory = " + System.getProperty("user.dir"));
      // insertFlightTest();

       // Connect to the database
       try (Connection conn = DriverManager.getConnection("jdbc:sqlite:FlightTracker.db")) {
           System.out.println("Connection to SQLite has been established.");
           loadDataDB(conn);
//           // Create a statement object to perform a query
//           try (Statement stmt = conn.createStatement()) {
//               // Example query
//               ResultSet rs = stmt.executeQuery("SELECT * FROM City");
//
//               // Iterate over the result set
//               while (rs.next()) {
//                   // Replace 'columnName' with your actual column names
//                   System.out.println(rs.getString("name"));
//               }
//           }

       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
        // loadData();
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
                user = new AirportAdministrator("username", "password", 1, airportCatalog.getAirports().get(1));
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

        // Driver code for registerFlight
        if (user.getUserType().equals("airportAdmin") || user.getUserType().equals("airlineAdmin")){
            String ans;
            System.out.print("Would you like to add a flight? (y/n): ");
            do {
                ans = scanner.nextLine();
                ans = ans.toLowerCase();
            } while (!ans.equals("y") && !ans.equals("n"));
            if (ans.equals("y")){
                String type = null;
                if (user.getUserType().equals("airlineAdmin")){
                    do {
                        System.out.println("Enter the type of the flight (Cargo/Commercial)");
                        type = scanner.nextLine();
                    } while (!type.equals("Cargo") && !type.equals("Commercial"));
                }
                System.out.print("Enter the number of the flight: ");
                String number = scanner.nextLine();
                System.out.print("Enter source airport code: ");
                String sourceCode2 = scanner.nextLine();
                Airport sourceAirport = airportCatalog.getAirport(sourceCode2);
                System.out.print("Enter destination airport code: ");
                String destinationCode2 = scanner.nextLine();
                Airport destinationAirport = airportCatalog.getAirport(destinationCode2);
                System.out.println("Enter the information for the time of departure ~");
                LocalDateTime depTime = getTimeUser(scanner);
                System.out.println("Enter the information for the time of arrival ~");
                LocalDateTime arrTime = getTimeUser(scanner);
                Airline airline = null;
                if (user.getUserType().equals("airlineAdmin")){
                    airline = ((AirlineAdministrator)user).getAirline();
                }

                registerFlight(type, number, sourceAirport, destinationAirport, depTime, arrTime, airline);

            }
        }

        // Driver Code for addAirport
        if (user.getUserType().equals("systemAdmin")){
            System.out.print("Would you like to add an airport? (y/n): ");
            String ans;
            do {
                ans = scanner.nextLine();
                ans = ans.toLowerCase();
            } while (!ans.equals("y") && !ans.equals("n"));
            if (ans.equals("y")){
                System.out.print("Enter the name of the airport: ");
                String airportName = scanner.nextLine();
                System.out.print("Enter airport code: ");
                String airportCode = scanner.nextLine();
                System.out.print("Enter city name: ");
                String cityName = scanner.nextLine();
                addAirport(airportName, airportCode, cityName);
            }
        }

        scanner.close();
    }

}