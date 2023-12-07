import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The JokeFetcher class provides methods to fetch jokes from the JokeAPI.
 * <p>
 * This class connects to the <a href="https://jokeapi.dev/">JokeAPI</a> to
 * retrieve jokes based on specified criteria.
 * </p>
 * Available Categories:
 * <ul>
 * <li>Programming: Jokes related to programming and technology.</li>
 * <li>Miscellaneous: General jokes that don't fit into specific
 * categories.</li>
 * <li>Christmas: Jokes related to the Christmas holiday.</li>
 * <li>Pun: Play-on-word jokes.</li>
 * <li>Spooky: Jokes related to Halloween or spooky themes.</li>
 * <li>Math: Jokes related to mathematics.</li>
 * <li>Any: Fetches a random joke from any category.</li>
 * </ul>
 * <p>
 * Copyright 2023 Meisam Amjad @author amjadm@miamioh.edu
 * </p>
 */
public class JokeFetcher {

    /**
     * Fetches a random joke from any category.
     * 
     * @return A string containing the joke.
     */
    public static String fetchJoke() {
        return getJoke("Any");
    }

    /**
     * Fetches a joke from a specified category.
     * 
     * @param category The desired category from which to fetch the joke.
     * @return A string containing the joke.
     */
    public static String fetchJoke(String category) {
        return getJoke(category);
    }

    // ============== PRIVATE VARIABLES & METHODS ============================

    // Base URL for fetching jokes from the JokeAPI
    private static final String JOKE_API_URL = "https://v2.jokeapi.dev/joke/";

    // Default setting for fetching each joke
    private static final String JOKE_SETTING = "?blacklistFlags=religious,"
            + "political,explicit,sexist,racist&type=single";

    /*
     * A private helper method that extracts the joke content from the provided
     * response string.
     * 
     * @param jsonResponse The JSON response string from the JokeAPI.
     * 
     * @return A string containing the extracted joke.
     */
    private static String extractJoke(String jsonResponse) {
        if (jsonResponse.contains("\"type\": \"single\"")) {
            int startIndex = jsonResponse.indexOf("\"joke\": \"") + 9;
            int endIndex = jsonResponse.indexOf("\"flags\"", startIndex);
            return jsonResponse.substring(startIndex, endIndex).trim()
                    .replace("\",", "");
        } else {
            int setupStart = jsonResponse.indexOf("\"setup \": \"") + 1;
            int setupEnd = jsonResponse.indexOf("\"", setupStart);
            int deliveryStart = jsonResponse.indexOf("\"delivery \": \"") + 1;
            int deliveryEnd = jsonResponse.indexOf("\"", deliveryStart);
            return jsonResponse.substring(setupStart, setupEnd) + " "
                    + jsonResponse.substring(deliveryStart, deliveryEnd);
        }
    }

    /*
     * A private helper method that fetches a joke from a specified category.
     * 
     * @param category The desired category from which to fetch the joke.
     * 
     * @return A string containing the joke.
     */
    private static String getJoke(String category) {
        try {
            // Construct the URL string based on the provided category
            String urlString = JOKE_API_URL + category + JOKE_SETTING;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Use the private helper method to extract the joke
            return extractJoke(response.toString());

        } catch (Exception e) {
            return "Error fetching a joke.";
        }
    }

    public static void main(String[] args) {
        System.out.println(fetchJoke());
        // Fetches a joke excluding religious political, sexist, racist,
        // and explicit categories
        System.out.println(fetchJoke("Programming")); // Fetches a programming
    }

}
