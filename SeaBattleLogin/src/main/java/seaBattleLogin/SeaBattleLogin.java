package seaBattleLogin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;


public class SeaBattleLogin implements ISeaBattleLogin {

    @Override
    public void registerPlayer(String name, String password){
        var values = new HashMap<String, String>() {{
            put("email", "julianblauw12345@hotmail.com");
            put ("password", "Appeltaart16!");
            put ("firstName", "Julian");
            put ("lastName", "Lu");
        }};



        /*
 HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://seabattle-s3.herokuapp.com/auth/register"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());

         */
    }

    @Override
    public void loginPlayer(String name, String password) {

    }
}
