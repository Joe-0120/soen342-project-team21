import java.time.LocalDateTime;

public class Flight {
  private String number;
  private LocalDateTime scheduledDep;
  private LocalDateTime actualDep;
  private LocalDateTime scheduledArr;
  private LocalDateTime estimatedArr;

  public Flight(String number, LocalDateTime scheduledDep, LocalDateTime actualDep, LocalDateTime scheduledArr, LocalDateTime estimatedArr) {
    this.number = number;
    this.scheduledDep = scheduledDep;
    this.actualDep = actualDep;
    this.scheduledArr = scheduledArr;
    this.estimatedArr = estimatedArr;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
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
}
