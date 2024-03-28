import java.util.ArrayList;

public class City {
    private int id;
    private String name;
    private String country;
    private double temperature;
    private ArrayList<Airport> airport = new ArrayList<Airport>();

    public City(int id, String name, String country, double temperature) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.temperature = temperature;
    }

    public String toString(){
        return name + ", " + country + ", " + temperature + "C";
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public ArrayList<Airport> getAirport() {
        return airport;
    }

    public void setAirport(ArrayList<Airport> airport) {
        this.airport = airport;
    }
}
