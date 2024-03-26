import java.util.ArrayList;

public class CityCatalog {
    private ArrayList<City> cities = new ArrayList<>();
    public CityCatalog(){
    }
    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public City getAirport(String cityName) {
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null; // If airport with the given code is not found
    }


}
