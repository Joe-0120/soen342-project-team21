import java.util.ArrayList;
import java.util.List;

public class FlightCatalog {
  private ArrayList<Flight> flights = new ArrayList<Flight>();

  public FlightCatalog() {
  }

  public void addFlight(Flight flight) {
      flights.add(flight);
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
}
