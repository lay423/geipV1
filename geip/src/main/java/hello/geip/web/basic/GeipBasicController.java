package hello.geip.web.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static hello.geip.web.basic.Key.API_KEY;

@Controller
public class GeipBasicController {


//    @GetMapping("/basic/search/{summonerName")
//    public String searchV1(@PathVariable String summonerName, Model model) {
//        model.addAttribute(summonerName);
//        return "basic.search";
//    }


    URL url;
    HttpURLConnection connection;
    String test;
    @GetMapping("/search/{summonerName}")
    public String search(@PathVariable String summonerName) throws IOException {
        String URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+
                summonerName + "?api_key=" + API_KEY;
        String iconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/2074.jpg";
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
        String number1;

        String response = stringBuffer.toString();

        String target = "name";
        int target_num = response.indexOf(target);
        String result;
        result = response.substring(target_num+7,(response.substring(target_num).indexOf(",")+target_num-1));

        target = "profileIconId";
        target_num =response.indexOf(target);
        result = response.substring(target_num+15,(response.substring(target_num).indexOf(",")+target_num));
        String result1 = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/" + result + ".jpg";
        return "html/search.html";
    }
}
