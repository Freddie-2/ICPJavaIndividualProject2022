package com.example.flights;

/**
 * @author Fredrick Kiarie Njoki
 */
public class Node {
    /**
     * Instance Variables/Fields
     */
    private String airportCode;
    private Node parent;
    private String airlineCode;
    private int stops;

    /**
     * Constructor:
     * Build and initialise objects of this class
     * @param airportCode the code of the airport
     * @param parent the parent
     * @param airlineCode the code of the airline
     * @param stops the number of stops of a flight
     */
    public Node(String airportCode, Node parent, String airlineCode, int stops) {
        this.airportCode = airportCode;
        this.parent = parent;
        this.airlineCode = airlineCode;
        this.stops = stops;
    }

    /**
     * returns the airport code
     * @return airportCode
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * returns the parent of a node
     * @return parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * returns the airline code
     * @return airlineCode
     */
    public String getAirlineCode() {
        return airlineCode;
    }

    /**
     * returns the number of stops of a flight
     * @return stops
     */
    public int getStops() {
        return stops;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

}

