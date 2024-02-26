public class AirlineAdministrator extends RegisteredUser{
    private Airline airline;

    public AirlineAdministrator(String username, String password, int id, Airline airline){
        super(username, password, id);
        this.airline = airline;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
