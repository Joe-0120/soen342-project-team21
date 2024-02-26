public class AirlineAdministrator extends RegisteredUser{
    private Airline airline;

    public AirlineAdministrator(String username, String password, int id, Airport airport){
        super(username, password, id);
        this.airport = airport;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
