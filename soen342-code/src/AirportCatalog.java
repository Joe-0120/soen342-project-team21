import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirportCatalog {
    private ArrayList<Airport> airports = new ArrayList<Airport>();

    public AirportCatalog() {
    }

    public ArrayList<Airport> getAirports() {
        return airports;
    }

    public void setAirports(ArrayList<Airport> airports) {
        this.airports = airports;
    }

    public Airport getAirport(String code) {
        for (Airport airport : airports) {
            if (airport.getCode().equals(code)) {
                return airport;
            }
        }
        return null; // If airport with the given code is not found
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
                  airports.add(new Airport(rs.getInt("airport_id"), name, code, city));
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
