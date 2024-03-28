import java.time.LocalDateTime;

public abstract class NonPrivateFlight extends Flight {
  
  public NonPrivateFlight(
          int id,
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
    super(id, number, source, destination, scheduledDep, actualDep, scheduledArr, estimatedArr, airline, aircraft);
  }

}