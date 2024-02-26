public class AirportAdministrator extends RegisteredUser{
    private Airport airport;

    public AirportAdministrator(String username, String password, int id, Airport airport){
        super(username, password, id);
        this.airport = airport;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}
