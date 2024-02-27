import java.time.LocalDateTime;

public abstract class Flight {
  private String number;
  private Airport source;
  private Airport destination;
  private LocalDateTime scheduledDep;
  private LocalDateTime actualDep;
  private LocalDateTime scheduledArr;
  private LocalDateTime estimatedArr;
  private Airline airline;
  private Aircraft aircraft;

  public Flight(
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
    this.number = number;
    this.source = source;
    this.destination = destination;
    this.scheduledDep = scheduledDep;
    this.actualDep = actualDep;
    this.scheduledArr = scheduledArr;
    this.estimatedArr = estimatedArr;
    this.airline = airline;
    this.aircraft = aircraft;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Airport getSource() {
    return source;
  }

  public void setSource(Airport source) {
    this.source = source;
  }

  public Airport getDestination() {
    return destination;
  }

  public void setDestination(Airport destination) {
    this.destination = destination;
  }

  public LocalDateTime getScheduledDep() {
    return scheduledDep;
  }

  public void setScheduledDep(LocalDateTime scheduledDep) {
    this.scheduledDep = scheduledDep;
  }

  public LocalDateTime getActualDep() {
    return actualDep;
  }

  public void setActualDep(LocalDateTime actualDep) {
    this.actualDep = actualDep;
  }

  public LocalDateTime getScheduledArr() {
    return scheduledArr;
  }

  public void setScheduledArr(LocalDateTime scheduledArr) {
    this.scheduledArr = scheduledArr;
  }

  public LocalDateTime getEstimatedArr() {
    return estimatedArr;
  }

  public void setEstimatedArr(LocalDateTime estimatedArr) {
    this.estimatedArr = estimatedArr;
  }

  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }

  public Aircraft getAircraft() {
    return aircraft;
  }

  public void setAircraft(Aircraft aircraft) {
    this.aircraft = aircraft;
  }
}
