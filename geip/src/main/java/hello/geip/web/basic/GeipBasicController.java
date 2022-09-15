package hello.geip.web.basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static hello.geip.web.basic.Key.API_KEY;

@RestController
public class GeipBasicController {


    URL url;
    HttpURLConnection connection;
    String test;
    @GetMapping("/search/{summonerName}")
    public String search(@PathVariable String summonerName) throws IOException {
        String URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+
                summonerName + "?api_key=" + API_KEY;
        url = new URL(URL);
        connection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new
                BufferedReader(new InputStreamReader((connection.getInputStream())));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuffer.append((inputLine));
        }
        bufferedReader.close();

        String response = stringBuffer.toString();
        return response;
    }
}
