import java.util.ArrayList;

public class Airline {
    private int id;
    private String name;
    private ArrayList<Aircraft> fleet = new ArrayList<>();

    public Airline(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "name='" + name + '\'' +
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

    public ArrayList<Aircraft> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Aircraft> fleet) {
        this.fleet = fleet;
    }

    public void addAircraft(Aircraft aircraft){
        this.fleet.add(aircraft);
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
