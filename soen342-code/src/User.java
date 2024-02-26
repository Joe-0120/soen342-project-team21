public abstract class User {
    private int id;
    private String username;
    private String password;


    public User (String username, String password, int id){
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
    public void setId(int id){
       this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
