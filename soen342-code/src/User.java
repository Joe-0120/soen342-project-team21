public abstract class User {
    private int id;

    public User (int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
       this.id = id;
    }


    public String getUserType(){
        if(this instanceof NonRegisteredEndUser)
            return "nonRegisteredEndUser";
        else if( this instanceof RegisteredEndUser)
            return "registeredEndUser";
        else if (this instanceof AirlineAdministrator)
            return "airlineAdmin";
        else if (this instanceof AirportAdministrator)
            return "airportAdmin";
        else if (this instanceof SystemAdministrator)
            return "systemAdmin";
        else
            return "invalid user";
    }
}
