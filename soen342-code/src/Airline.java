import java.util.ArrayList;

public class Airline {
    private String name;
    private ArrayList<Aircraft> fleet = new ArrayList<>();

    public Airline(String name) {
        this.name = name;
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
}
