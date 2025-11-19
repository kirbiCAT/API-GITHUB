package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//get user input for ID
        Scanner myID = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username:");
        String name = myID.nextLine();
        try {
            //Public API:
            //https://www.metaweather.com/api/location/search/?query=<CITY>
            //https://www.metaweather.com/api/location/44418/

            //# https://api.github.com/users/<username>/events
            //# Example: https://api.github.com/users/kamranahmedse/events

            //URL url = new URL("https://www.metaweather.com/api/location/search/?query=London");
            URL url = new URL("https://api.github.com/users/"+name+"/events");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println(informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                //Get the first JSON object in the JSON array
                System.out.println(dataObject.get(0));

                JSONObject users = (JSONObject) dataObject.get(0);

                System.out.println("your user id:" + users.get("id"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}