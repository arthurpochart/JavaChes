package chesslogic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class AiEngine {

    public String[] getMove(String moveLine) {
        String firstmove = null;
        String secondmove = null;
        StringBuilder informationString = null;
        try {
            //Api: http://chess-api.herokuapp.com/next_best/MOVELIST

            URL url = new URL("http://chess-api.herokuapp.com/next_best/" + moveLine);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                informationString.replace(0, 13, "");
                informationString.replace(informationString.length() - 2, informationString.length(), "");


            }
        } catch (Exception e) {
            System.out.println("Verifiez votre connection internet!");
        }
        return new String[]{informationString.toString().substring(0, 2), informationString.toString().substring(2, 4)};

    }
}



