import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Airport {
    private int id;
    private String name;
    private String code;
    private City city;
    private ArrayList<AirportAdministrator> administrators  = new ArrayList<AirportAdministrator>();
    private ArrayList<Flight> sourceFlights = new ArrayList<Flight>();
    private ArrayList<Flight> destinationFlights = new ArrayList<Flight>();
    private ArrayList<Flight> handledPrivateFlights = new ArrayList<Flight>();

    public Airport(int id, String name, String code, City city) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.city = city;
    }
    public Airport(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", city=" + city +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<AirportAdministrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(ArrayList<AirportAdministrator> administrators) {
        this.administrators = administrators;
    }

    public ArrayList<Flight> getSourceFlights() {
        return sourceFlights;
    }

    public void setSourceFlights(ArrayList<Flight> sourceFlights) {
        this.sourceFlights = sourceFlights;
    }

    public ArrayList<Flight> getDestinationFlights() {
        return destinationFlights;
    }

    public void setDestinationFlights(ArrayList<Flight> destinationFlights) {
        this.destinationFlights = destinationFlights;
    }

    public ArrayList<Flight> getHandledPrivateFlights() {
        return handledPrivateFlights;
    }

    public void setHandledPrivateFlights(ArrayList<Flight> handledPrivateFlights) {
        this.handledPrivateFlights = handledPrivateFlights;
    }

    public String addAirport(String name, String code, City city) {
      String insertSql = "INSERT INTO Airport (name, code, city_id) VALUES (?, ?, ?) RETURNING *;";
      try (Connection conn = DriverManager.getConnection("jdbc:sqlite:FlightTracker.db")) {
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
          pstmt.setString(1, name);
          pstmt.setString(2, code);
          pstmt.setInt(3, city.getId());

          try (ResultSet rs = pstmt.executeQuery()) {
              if (rs.next()) {
                  System.out.println("Inserted Airport ID: " + rs.getInt("airport_id"));
              }
          }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }
      return "Airport added.";
    }
}
