import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightCatalog {
    private ArrayList<Flight> flights = new ArrayList<>();

    public FlightCatalog() {
    }

    // Normal Flight
    public void addFlight(String flightNumber, Airport source, Airport destination, LocalDateTime scheduledDep, LocalDateTime scheduledArr, Airline airline) {
      System.out.println("Adding Flight: " + flightNumber);
      // Add switch for Commercial or Cargo
      flights.add(new Commercial(flightNumber, source, destination, scheduledDep, null, scheduledArr, null, null, null));
    }

    // Private flight
    public void addFlight(String flightNumber, Airport source, Airport destination, LocalDateTime scheduledDep, LocalDateTime scheduledArr) {
      System.out.println("Adding Flight: " + flightNumber);
      flights.add(new PrivateFlight(flightNumber, source, destination, scheduledDep, null, scheduledArr, null, null, null));
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