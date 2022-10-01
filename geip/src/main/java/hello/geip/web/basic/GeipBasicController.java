package hello.geip.web.basic;

import hello.geip.domain.Match;
import hello.geip.domain.Summoner;
import lombok.extern.slf4j.Slf4j;
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


    Summoner summoner;
    URL url;
    HttpURLConnection connection;
    final int NAME_TO_START = 6000;

    @GetMapping("/test")
    public String Test(){
        String time = "12분 32초";
        String min = time.substring(0, time.indexOf("분"));
        log.info("min={}", min);
        return "ok";
    }


    @GetMapping("/search/{summonerName}")
    public String search(@PathVariable String summonerName, Model model) throws IOException {




        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));

//        summoner 모델 addAttribute
        model.addAttribute("summoner", summoner);
//        return "basic/index";
        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);
        String[] matchId = new String[20];
        extractMatchId(response, matchId);



//        puuid로 match 정보 불러오기
        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[0] + "?api_key=" + API_KEY);
        response = response.substring(response.indexOf(summonerName) - NAME_TO_START);
        response = response.substring(response.indexOf("assist"));
        response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);






        response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        log.info("length={}", "puuid".length());



        //match getSource 테스트 용
//        return getSource("gameType", response);
//                return " "+ getSource("win", response);
//                        +" "+ getSource("championName", response)+" "+ getSource("championLevel", response)+" "+
//                getSource("kills", response)+" "+getSource("deathsByEnemyChamps", response)+" "+
//                getSource("assist", response);
        return response;
//

//        @Controller로 바꾸고
//        return "basic/search";


//        이건 프로필 이미지 불러올때 쓸거
//        String result1 = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/"
//                + level_int + ".png";
//        String iconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/2074.png";



//        이건 getSource 잘 작동하는지 확인핳때

    }

    @GetMapping("/search/{summonerName}/clear")
    public String searchClear(@PathVariable String summonerName) throws IOException {

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));

        return "닉네임 | 아이콘아이디 | 레벨 | puuid <br>" + summoner.getSummonerName() + " | " +
                summoner.getIconId()+" | "+ summoner.getLevel()+" | "+ summoner.getPuUid();
    }



    @GetMapping("/search/{summonerName}/matchIds")
    public String search(@PathVariable String summonerName) throws IOException {

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));

        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);

        return response;
    }



    @GetMapping("/search/{summonerName}/matchAll")
    public String searchMatchAll(@PathVariable String summonerName) throws IOException {


        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];
        extractMatchId(response, matchId);


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[5] + "?api_key=" + API_KEY);

        return response;
    }

    @GetMapping("/search/{summonerName}/matchOnly")
    public String searchMatchOnly(@PathVariable String summonerName) throws IOException {


        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];
        extractMatchId(response, matchId);


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[6] + "?api_key=" + API_KEY);
        response = response.substring(response.indexOf(summonerName) - NAME_TO_START);
        log.info("summonerName={}", summonerName);
        response = response.substring(response.indexOf("assist"));
        response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);



//         return getSource("win", response)+" " +getSource("championName", response) +" " +
//                getSource("championLevel", response)+" " +
//                getSource("kills", response)+ getSource("deathsByEnemyChamps", response)+" " +
//                getSource("assist", response);

        return response;
    }
    @GetMapping("/search/{summonerName}/matchDetail")
    public String searchMatchDetail(@PathVariable String summonerName) throws IOException {


        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];
        extractMatchId(response, matchId);


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId[0] + "?api_key=" + API_KEY);
        response = response.substring(response.indexOf(summonerName) - NAME_TO_START);
        response = response.substring(response.indexOf("assist"));
        response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);

        //        Match 객체 생성
//        Match match = new Match(1, getSource("win", response),
//                getSource("championName", response), getSource("champLevel", response),
//                getSource("\"kills\"", response), getSource("deaths\"", response),
//                getSource("assist", response), "1");


//        return "승패 | 챔피언이름 | 챔피언레벨 | K/D/A<br>"+match.getWin()+" | "+match.getChampionName()+" | "
//                +match.getChampLevel() +" | "+match.getKills()+"/"+match.getDeaths()+"/"+ match.getAssist();
        return "ok";
    }

    @GetMapping("/search/{summonerName}/matchList")
    public String searchMatchList(@PathVariable String summonerName) throws IOException {


        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];
        extractMatchId(response, matchId);


        String result = "승패 | 챔피언이름 | 챔피언레벨 | K/D/A<br>";

        Match[] match = new Match[20];
        //        Match 객체 생성
        for(int i=0; i<20; i++){
            response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                    + matchId[i] + "?api_key=" + API_KEY);
            response = response.substring(response.indexOf(summonerName) - NAME_TO_START);
            response = response.substring(response.indexOf("assist"));
            response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);

//            match[i] = new Match(1, getSource("win", response),
//                    getSource("championName", response), getSource("champLevel", response),
//                    getSource("\"kills\"", response), getSource("deaths\"", response),
//                    getSource("assist", response), "1");

            if(match[i].getWin().equals("true"))
                result += "승리";
            else
                result += "패배";
            result += match[i].getWin() + " | "+match[i].getChampionName()+" | "
                    +match[i].getChampLevel() +" | "+match[i].getKills()+"/"+match[i].getDeaths()+"/"+
                    match[i].getAssist() + "<br>";
        }



        return result;
//        return "ok";
    }

    @GetMapping("/search/{summonerName}/matchIntro")
    public String searchMatchIntro(@PathVariable String summonerName) throws IOException {



        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);

        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);

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

        if(target=="summonerLevel" || target == "win") //데이터 바로뒤에 }가 있고, ,가 없을 경우
            return response.substring(target_num+target.length()+2,(response.substring(target_num).indexOf("}")+target_num));
        else if(target == "\"kills\"" || target == "deaths\"")
            return response.substring(target_num+target.length()+1,(response.substring(target_num).indexOf(",")+target_num));
        else if(target=="puuid" || target=="championName")    //데이터와 ,사이에 "가 있을 경우
            return response.substring(target_num+target.length()+3,(response.substring(target_num).indexOf(",")+target_num-1));
        else if(target=="assist")
            return response.substring(target_num+target.length()+3,(response.substring(target_num).indexOf(",")+target_num));
        else    //(target=="profileIconId") //데이터 바로뒤에 ,가 있을 경우
            return response.substring(target_num+target.length()+2,(response.substring(target_num).indexOf(",")+target_num));
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
