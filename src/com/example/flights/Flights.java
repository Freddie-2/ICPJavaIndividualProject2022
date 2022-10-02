package com.example.flights;

import java.io.*;
import java.util.*;

/**
 * @author Fredrick Kiarie Njoki
 */
public class Flights {
    /**
     * Instance Variables/Fields
     */
    private String flightName;
    private String flightNumber;
    private String countryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flights)) return false;
        Flights flights = (Flights) o;
        return Objects.equals(flightName, flights.flightName) && Objects.equals(flightNumber,
                flights.flightNumber) && Objects.equals(countryName, flights.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightName, flightNumber, countryName);
    }

    /**
     * Constructor:
     * Build and initialise objects of this class
     * @param flightName the name of the flight
     * @param flightNumber the number of the flight
     * @param countryName the country of origin
     */
    public Flights(String flightName, String flightNumber, String countryName){
        this.flightName = flightName;
        this.flightNumber = flightNumber;
        this.countryName = countryName;
    }

    /**
     * returns the name of the flight
     * @return this.flightName
     */
    public String getFlightName() {
        return this.flightName;
    }
    /**
     * returns the number of the flight
     * @return this.flightNumber
     */
    public String getFlightNumber() {
        return this.flightNumber;
    }
    /**
     * returns the country of origin of the flight
     * @return this.countryName
     */
    public String getCountryName() {
        return this.countryName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    /**
     * method to open and read a csv file
     */
    public static void read(String csvFile) {
        String delimiter = ",";
        try {
            File file = new File(csvFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                for(String tempStr : tempArr) {
                }
                System.out.println();
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * method to write into the input text file which contains the start city and country
     * and destination city and country
     */
    public static void write(String inputFile){
        System.out.println("Enter the start city and country, and the destination city and country: ");

        try (   Scanner in = new Scanner(System.in);
                FileOutputStream file = new FileOutputStream(inputFile);
                OutputStreamWriter out = new OutputStreamWriter(file, "UTF-8");
                BufferedWriter buf = new BufferedWriter(out);
                PrintWriter writer = new PrintWriter(buf)) {

            String first = in.nextLine();
            String second = in.nextLine();

            writer.println(first);
            writer.print(second);
            writer.println();

        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("done.");
    }

    /**
     * method that creates a hashmap with a String containing city concatenated with the country as the key
     * and the airport codes as the values.
     * The parameter "csvFile1" is the airports.csv file
     * @return map
     */
    public static Map<String, String> aiportSearch(String csvFile1) throws IOException {
        String delimiter = ",";
        Map<String, String> map = new HashMap<>();
        File file = new File(csvFile1);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] tempArr;
        while((line = br.readLine()) != null) {
            tempArr = line.split(delimiter);
            String myList = tempArr[2]+tempArr[3];
            map.put(myList, tempArr[4]);
        }
        br.close();
        return map;
    }
    /**
     * method that gets the start airport(city and country) and the destination airport(city and country)
     * adds the start airport and destination airport in an ArrayList called results
     * @return results
     */
    public static ArrayList<String> outputRoutes(String csvFile2) throws IOException {
        String delimiter = ",";
        String source = "";
        String dest = "";
        try {
            File file = new File(csvFile2);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] start = br.readLine().split(",");
            String[] destination = br.readLine().split(",");
            source = start[0]+start[1];
            dest = destination[0]+ destination[1];
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        ArrayList<String> results = new ArrayList<>();
        results.add(source.strip());
        results.add(dest.strip());

        return results;
    }

    /**
     * method that gets the airport codes of the start and destination cities
     *
     * @return codes
     */
    public static ArrayList<String> getCodes(Map<String, String> map) throws IOException {
        String csvFile2 = "accra_winnipeg.txt";
        String startCode = "";
        String destCode = "";
        if (map.containsKey(outputRoutes(csvFile2).get(0))){
            startCode = map.get(outputRoutes(csvFile2).get(0));
        }
        else{
            System.out.println("Yes bana");
        }
        if (map.containsKey(outputRoutes(csvFile2).get(1))){
           destCode = map.get(outputRoutes(csvFile2).get(1));
        }
        else {
            System.out.println("No bana");
        }
        ArrayList<String> codes = new ArrayList<>();
        codes.add(startCode);
        codes.add(destCode);

        return codes;
    }
    /**
     * method that creates a Hashmap that maps the airport code to a node.
     * the nodes are added to an ArrayList
     * @return routeMap
     */
    public static Map<String, ArrayList<Node>> routesMap(String csvFile3) throws IOException {
        String delimiter = ",";
        Map<String, ArrayList<Node>> routemap = new HashMap<>();
        File file = new File(csvFile3);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] tempArr;
        while((line = br.readLine()) != null) {
            tempArr = line.split(delimiter);
            Node newNode = new Node(tempArr[4], null, tempArr[0], Integer.parseInt(tempArr[7]));
            ArrayList<Node> myList = new ArrayList<Node>(); // Create an ArrayList object
            if (routemap.containsKey(tempArr[2])) {
                myList = routemap.get(tempArr[2]);
            }
            myList.add(newNode);
            routemap.put(tempArr[2], myList);

        }
        br.close();
        return routemap;
    }

    /**
     * method that find the possible routes from start to destination
     * the method uses the breadth-first search algorithm
     *
     * @return boolean
     */
    public static boolean findRoutes(Map<String, ArrayList<Node>> myMap ) throws IOException{
        String csvFile1 = "C:/Users/Admin/Desktop/SEM COURSES/2.2 Sem/Intermediate Computer Programming/airports.csv/";
        String start = getCodes(aiportSearch(csvFile1)).get(0);
        String end = getCodes(aiportSearch(csvFile1)).get(1);

        Node startAirportNode = new Node(start, null, null, 0);

        Queue<Node> frontier = new LinkedList<Node>();
        HashSet<String> explored = new HashSet<String>();
        frontier.add(startAirportNode);
        ArrayList<String> successor = new ArrayList<String>();

        while(frontier.size() != 0){
            Node currentAirportNode  = frontier.poll();
            explored.add(String.valueOf(currentAirportNode));

            ArrayList<Node> destinations = myMap.get(start);
            System.out.println(destinations);
            if (destinations != null){
            for (Node desti : destinations) {
                System.out.println(desti);
                desti.setParent(currentAirportNode);
                if (desti.getAirportCode() == end) {
                    System.out.println("Destination Found");
                    String csvFile4 = "accra_winnipeg.txt";
                    return solutionPath(csvFile4, desti);
                }
                if (!explored.contains(desti.getAirportCode()) &&
                        !frontier.contains(desti)) {
                    frontier.add(desti);
                }
            }
                }
            }
            return false;
        }

    /**
     * method that gets the solution path, i.e., a series of flights
     * from the start city to the destination city and writes to the output file.
     * @return boolean
     */
    public static boolean solutionPath(String outputFile, Node current){

        try {   Scanner in = new Scanner(System.in);
            FileOutputStream file = new FileOutputStream(outputFile);
            OutputStreamWriter out = new OutputStreamWriter(file, "UTF-8");
            BufferedWriter buf = new BufferedWriter(out);
            PrintWriter writer = new PrintWriter(buf);
            int count = 0;
            while(current.getParent() != null) {
                writer.println(count + ". " + current.getAirlineCode() + " from " + current.getParent().getAirportCode() +
                        " to " + current.getAirportCode() + current.getStops() + " stops ");
                count++;
            }
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
        }

        System.out.println("done.");

        return false;
    }

    /**
     * main method
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Flights flight = new Flights("Boeing", "WB 220", "Rwanda");

        String inputFile = "accra_winnipeg.txt";
        write(inputFile);

        String csvFile1 = "C:/Users/Admin/Desktop/SEM COURSES/2.2 Sem/Intermediate Computer Programming/airports.csv/";
        aiportSearch(csvFile1);

        String csvFile2 = "accra_winnipeg.txt";
        outputRoutes(csvFile2);

        getCodes(aiportSearch(csvFile1));

        String csvFile3 = "C:/Users/Admin/Desktop/SEM COURSES/2.2 Sem/Intermediate Computer Programming/routes.csv/";
        routesMap(csvFile3);

        //findRoutes(routesMap(csvFile3));
    }
}




