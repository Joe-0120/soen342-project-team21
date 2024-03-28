-- Create the 'City' table
CREATE TABLE IF NOT EXISTS City (
    city_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255),
    country VARCHAR(255),
    temp DOUBLE
);

-- Create the 'Airport' table
CREATE TABLE IF NOT EXISTS Airport (
    airport_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255),
    code VARCHAR(3),
    city_id INT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES City(city_id)
);

-- Create the 'Airline' table
CREATE TABLE IF NOT EXISTS Airline (
    airline_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255)
);

-- Create the 'Flight' table
CREATE TABLE IF NOT EXISTS Flight_temp (
    flight_id INTEGER PRIMARY KEY AUTOINCREMENT,
    source INT NOT NULL,
    destination INT NOT NULL,
    number VARCHAR(255),
    scheduledDep DATETIME,
    actualDep DATETIME,
    scheduledArr DATETIME,
    estimatedArr DATETIME,
    type VARCHAR(255),
    airline_id INT,
    aircraft_id INTEGER,
    FOREIGN KEY (source) REFERENCES Airport(airport_id),
    FOREIGN KEY (destination) REFERENCES Airport(airport_id),
    FOREIGN KEY (airline_id) REFERENCES Airline(airline_id),
    FOREIGN KEY (aircraft_id) REFERENCES Aircraft(aircraft_id)
);


-- Create the 'Aircraft' table
CREATE TABLE IF NOT EXISTS Aircraft (
    aircraft_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255),
    status VARCHAR(255),
    airline_id INT NOT NULL,
    FOREIGN KEY (airline_id) REFERENCES Airline(airline_id)
);
