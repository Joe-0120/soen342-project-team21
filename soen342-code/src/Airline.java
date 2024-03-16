import java.util.ArrayList;

public class Airline {
    private String name;
    private ArrayList<Aircraft> fleet = new ArrayList<>();

    public Airline(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Aircraft> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Aircraft> fleet) {
        this.fleet = fleet;
    }

    public Aircraft getAircraft(Airport source){
        for (Aircraft aircraft : fleet) {
            if ((aircraft.getStatus().equals(source.getCode()))) {
                return aircraft;
            }
        }

        return null;

    }

}
