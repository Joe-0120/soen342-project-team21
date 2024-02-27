import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightCatalog {
    private ArrayList<Flight> flights = new ArrayList<>();

    public FlightCatalog() {
    }

    public void addFlight(Flight flight) {
      flights.add(flight);
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
        .filter(flight -> (flight.getSource().equals(airport)) &&
          flight.getSource().equals(source) && flight.getDestination().equals(destination))
        .collect(Collectors.toList());
    }
  
    public List<Flight> getFlights(Airport source, Airport destination) {
      return flights.stream()
        .filter(flight -> flight.getSource().equals(source) && flight.getDestination().equals(destination))
        .collect(Collectors.toList());
    }
}
