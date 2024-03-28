import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;


public class FlightCatalog {
    private ArrayList<Flight> flights = new ArrayList<>();

    public FlightCatalog() {
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public int insertFlightDB(String type, String flightNumber, Airport source, Airport destination, LocalDateTime scheduledDep, LocalDateTime scheduledArr, Airline airline, Aircraft aircraft){
        String insertSql = "INSERT INTO Flight (source, destination, number, scheduledDep, actualDep, scheduledArr, estimatedArr, type, airline_id, aircraft_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *;";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:FlightTracker.db");
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            pstmt.setInt(1, source.getId());
            pstmt.setInt(2, destination.getId());
            pstmt.setString(3, flightNumber);
            pstmt.setString(4, scheduledDep.format(formatter));  // Format LocalDateTime
            // Assuming actual departure is the same as scheduled for this example
            pstmt.setString(5, scheduledDep.format(formatter));  // Format LocalDateTime
            pstmt.setString(6, scheduledArr.format(formatter));  // Format LocalDateTime
            // Assuming estimated arrival is the same as scheduled for this example
            pstmt.setString(7, scheduledArr.format(formatter));  // Format LocalDateTime
            pstmt.setString(8, type);
            if (airline != null)
                pstmt.setInt(9, airline.getId());
            if (aircraft != null)
                pstmt.setInt(10, aircraft.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Inserted Flight ID: " + rs.getInt("flight_id"));
                    return rs.getInt("flight_id");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public String addFlight(String type, String flightNumber, Airport source, Airport destination, LocalDateTime scheduledDep, LocalDateTime scheduledArr, Airline airline, Aircraft aircraft) {
        int id = insertFlightDB(type, flightNumber, source, destination, scheduledDep, scheduledArr, airline, aircraft);
        if (id == -1) return "Error inserting flight";
        switch (type) {
            case "Commercial":
                flights.add(new Commercial(id, flightNumber, source, destination, scheduledDep, null, scheduledArr, null, airline, aircraft));
                break;
            case "Cargo":
                flights.add(new Cargo(id, flightNumber, source, destination, scheduledDep, null, scheduledArr, null, airline, aircraft));
                break;
        }
        return "Flight inserted successfully";
    }
    

    // Normal Flight
//    public String addFlight(int id, String flightNumber, Airport source, Airport destination, LocalDateTime scheduledDep, LocalDateTime scheduledArr, Airline airline) {
//      System.out.println("Adding Flight: " + flightNumber);
//      // Add Flight -> DB
//      // DB returns id
//      // new instance of Flight
//      flights.add(new Commercial(id, flightNumber, source, destination, scheduledDep, null, scheduledArr, null, null, null));
//      System.out.println(flights);
//      return "Flight added";
//    }

    // Private flight
    public String addFlight(String flightNumber, Airport source, Airport destination, LocalDateTime scheduledDep, LocalDateTime scheduledArr) {
      int id = insertFlightDB("Private", flightNumber, source, destination, scheduledDep, scheduledArr, null, null);
      if (id == -1) return "Error inserting flight";
      flights.add(new PrivateFlight(id, flightNumber, source, destination, scheduledDep, null, scheduledArr, null, null, null));
      System.out.println(flights);
      return "Private Flight added";
    }

    public void setFlights(ArrayList<Flight> flights) {
      this.flights = flights;
  }

    public boolean removeFlight(String number) {
      return flights.removeIf(flight -> flight.getNumber().equals(number));
    }

    public List<Flight> getAllFlights() {
      return new ArrayList<>(flights);
    }

    public Flight findFlightByNumber(String number) {
      for (Flight flight : flights) {
          if (flight.getNumber().equals(number)) {
              return flight;
          }
      }
      return null;
    }
    
    public List<Flight> getFlights(Airport airport, Airport source, Airport destination) {
      return flights.stream()
        .filter(flight -> (flight.getSource().equals(airport) || flight.getDestination().equals(airport)) &&
          flight.getSource().equals(source) && flight.getDestination().equals(destination))
        .collect(Collectors.toList());
    }
  
    public List<Flight> getFlights(Airport source, Airport destination) {
      return flights.stream()
        .filter(flight -> flight.getSource().equals(source) && flight.getDestination().equals(destination))
        .collect(Collectors.toList());
    }

    public boolean checkDepUnique(Airport source, LocalDateTime scheduledDep) {
        for (Flight flight : flights) {
            if ((flight.getSource().equals(source)) && (flight.getScheduledDep().equals(scheduledDep))) {
                return false;
            }
        }
        return true;
    }

        public boolean checkArrUnique (Airport destination, LocalDateTime scheduledArr){
            for (Flight flight : flights) {
                if ((flight.getDestination().equals(destination)) && (flight.getScheduledArr().equals(scheduledArr))) {
                    return false;
                }
            }
            return true;
        }
    }