package hello.geip.web.basic;

import hello.geip.domain.Match;
import hello.geip.domain.Summoner;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@Slf4j
public class GeipBasicController {


    Summoner summoner = new Summoner();
    URL url;
    HttpURLConnection connection;

    @GetMapping("/search/{summonerName}")
    public String search(@PathVariable String summonerName, Model model) throws IOException {

        int fromNameToStart = 10850;


        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner.setSummonerName(summonerName);
        summoner.setIconId(Integer.parseInt(getSource("profileIconId", response)));
        summoner.setLevel(Integer.parseInt(getSource("summonerLevel", response)));
        summoner.setPuuid(getSource("puuid", response));

//        summoner 모델 addAttribute
        model.addAttribute("summoner", summoner);
//        return "basic/index";
        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuuid() + "/ids?api_key=" + API_KEY);
        String[] matchId = new String[20];
        extractMatchId(response, matchId);



//        puuid로 match 정보 불러오기
        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[0] + "?api_key=" + API_KEY);
        response = response.substring(response.indexOf(summonerName) - fromNameToStart);
        response = response.substring(response.indexOf("assist"));
        response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);


//        Match 객체 생성
//        Match match = new Match(1, getSource("gameType", response), getSource("win", response),
//                getSource("championName", response), getSource("championLevel", response),
//                getSource("kills", response), getSource("deathsByEnemyChamps", response),
//                getSource("assist", response));
//        model.addAttribute("match", match);



        response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);




        //match getSource 테스트 용
//        return getSource("gameType", response);
//                return " "+ getSource("win", response);
//                        +" "+ getSource("championName", response)+" "+ getSource("championLevel", response)+" "+
//                getSource("kills", response)+" "+getSource("deathsByEnemyChamps", response)+" "+
//                getSource("assist", response);
        return response;


//        @Controller로 바꾸고
//        return "basic/search";


//        이건 프로필 이미지 불러올때 쓸거
//        String result1 = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/"
//                + level_int + ".png";
//        String iconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/2074.png";



//        이건 getSource 잘 작동하는지 확인핳때
//        return (getSource("profileIconId", response)) +" "+
//                (getSource("summonerLevel", response))+" "+
//                        getSource("puuid", response)+" "+summonerName;
    }
    @GetMapping("/search/{summonerName}/matchIds")
    public String search(@PathVariable String summonerName) throws IOException {

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner.setSummonerName(summonerName);
        summoner.setIconId(Integer.parseInt(getSource("profileIconId", response)));
        summoner.setLevel(Integer.parseInt(getSource("summonerLevel", response)));
        summoner.setPuuid(getSource("puuid", response));

        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuuid() + "/ids?api_key=" + API_KEY);

        return response;
    }



    @GetMapping("/search/{summonerName}/matchAll")
    public String searchMatchAll(@PathVariable String summonerName) throws IOException {
        int fromNameToStart = 10850;

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner.setSummonerName(summonerName);
        summoner.setIconId(Integer.parseInt(getSource("profileIconId", response)));
        summoner.setLevel(Integer.parseInt(getSource("summonerLevel", response)));
        summoner.setPuuid(getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuuid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];
        extractMatchId(response, matchId);


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[0] + "?api_key=" + API_KEY);

        return response;
    }

    @GetMapping("/search/{summonerName}/matchOnly")
    public String searchMatchOnly(@PathVariable String summonerName) throws IOException {
        int fromNameToStart = 10850;

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner.setSummonerName(summonerName);
        summoner.setIconId(Integer.parseInt(getSource("profileIconId", response)));
        summoner.setLevel(Integer.parseInt(getSource("summonerLevel", response)));
        summoner.setPuuid(getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuuid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];
        extractMatchId(response, matchId);


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[0] + "?api_key=" + API_KEY);
        response = response.substring(response.indexOf(summonerName) - fromNameToStart);
        response = response.substring(response.indexOf("assist"));
        response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);

        return response;
    }

    @GetMapping("/search/{summonerName}/matchIntro")
    public String searchMatchIntro(@PathVariable String summonerName) throws IOException {
        int fromNameToStart = 10850;

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner.setSummonerName(summonerName);
        summoner.setIconId(Integer.parseInt(getSource("profileIconId", response)));
        summoner.setLevel(Integer.parseInt(getSource("summonerLevel", response)));
        summoner.setPuuid(getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuuid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];
        extractMatchId(response, matchId);


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[0] + "?api_key=" + API_KEY);
        response = response.substring(0, response.indexOf("assist"));

        return response;
    }

    private static void extractMatchId(String response, String[] matchId) {
        int target_num = response.indexOf("KR");
        response = response.substring(1);
        target_num = response.indexOf("KR");
        int i=0;
        while(i<20){
            matchId[i] = response.substring(target_num,14);
            if(response.charAt(15) != ']')
                response = response.substring(response.indexOf(",")+1);
            else
                break;
            i++;
        }
    }

    private String getSource(String target, String response) {
        int target_num =response.indexOf(target);
        if(target=="profileIconId")
            return response.substring(target_num+15,(response.substring(target_num).indexOf(",")+target_num));
        else if(target=="summonerLevel" || target == "win")
            return response.substring(target_num+15,(response.substring(target_num).indexOf("}")+target_num));
        else if(target=="puuid")
            return response.substring(target_num+8,(response.substring(target_num).indexOf(",")+target_num-1));
        else
            return response.substring(target_num+target.length()+2,(response.substring(target_num).indexOf(",")+target_num-1));
    }

    private String getApiDataByURL(String getURL) throws IOException {
        String URL = getURL;
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

        return stringBuffer.toString();
    }

    private String getMatchInfo(String puuid){
        return null;
    }
}
