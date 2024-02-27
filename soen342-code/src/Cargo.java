import java.time.LocalDateTime;

public class Cargo extends NonPrivateFlight {
  public Cargo(
    String number,
    Airport source,
    Airport destination,
    LocalDateTime scheduledDep,
    LocalDateTime actualDep,
    LocalDateTime scheduledArr,
    LocalDateTime estimatedArr,
    Airline airline,
    Aircraft aircraft
  ) {
    super(number, source, destination, scheduledDep, actualDep, scheduledArr, estimatedArr, airline, aircraft);
  }
}