package hello.geip.web.basic;

import hello.geip.domain.Match;
import hello.geip.domain.MatchRepository;
import hello.geip.domain.Summoner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static hello.geip.web.basic.Key.API_KEY;

@Controller
@Slf4j
public class HtmlController {
    Summoner summoner;
    URL url;
    HttpURLConnection connection;
    private final MatchRepository matchRepository;
    final int NAME_TO_START = 6000;


    @Autowired
    public HtmlController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    @GetMapping("/search1")
    public String search1() {

        return "index";
    }

    @GetMapping("/search2")
    public String search2(Model model) throws IOException {


        String summonerName = "jytfuih";

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);
        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));

//        summoner 모델 addAttribute
        model.addAttribute("summoner", summoner);

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


            String winLose = "default";
            if(getSource("win", response).equals("true"))
                winLose = "승리";
            else if(getSource("win", response).equals("false"))
                winLose="패배";
            else
                winLose="오류";


//            match[i] = new Match(i, winLose,
//                    "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/champion/"
//                            +getSource("championName", response)+".png"
//                    , getSource("champLevel", response),
//                    getSource("\"kills\"", response), getSource("deaths\"", response),
//                    getSource("assist", response), "1");

            matchRepository.save(match[i]);
//            model.addAttribute("match["+i+"]", match[i]);

        }
//        model.addAttribute("match", match[0]);
//        model.addAttribute("match1", match[1]);
//        model.addAttribute("match2", match[2]);
        List<Match> matches = matchRepository.findAll();
        model.addAttribute("matches", matches);




        return "index";
    }

    @GetMapping("/search")
    public String search(Model model) throws IOException {


        String summonerName = "jytfuih";

        String response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + API_KEY);
        summoner = new Summoner(summonerName,
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));

//        summoner 모델 addAttribute


        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);

        String[] matchId = new String[20];

        extractMatchId(response, matchId);


        String result = "승패 | 챔피언이름 | 챔피언레벨 | K/D/A<br>";

        Match[] match = new Match[20];
        String item[] = new String[6];
        String team[] = new String[10];
        String teamVal;
        String target = "championName";
        int target_num;


        for(int i=0; i<20; i++){
            response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                    + matchId[i] + "?api_key=" + API_KEY);


            //팀 초상화 따오는 로직
            for(int j=0; j<team.length;j++){
                target_num = response.indexOf("championName");
                team[j] = "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/champion/" +
                        getSource(target, response) + ".png";;
                response = response.substring(target_num+target.length()+3);
            }

            response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                    + matchId[i] + "?api_key=" + API_KEY);

            response = response.substring(response.indexOf(summonerName) - NAME_TO_START);
            response = response.substring(response.indexOf("assist"));
            response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);


            String winLose = "default";
            if(getSource("win", response).equals("true"))
                winLose = "승리";
            else if(getSource("win", response).equals("false"))
                winLose="패배";
            else
                winLose="오류";

            //아이템 따오는 로직
            for(int j=0; j<item.length;j++){
                item[j] = "https://ddragon.leagueoflegends.com/cdn/12.18.1/img/item/" +
                        getSource("item"+j, response) + ".png";
            }



            match[i] = new Match(i, winLose,
                    "https://ddragon.leagueoflegends.com/cdn/12.18.1/img/champion/"
                            +getSource("championName", response)+".png"
                    , getSource("champLevel", response),
                    getSource("\"kills\"", response), getSource("deaths\"", response),
                    getSource("assist", response), getSource("wardsPlaced", response), item, team);


            matchRepository.save(match[i]);
//            model.addAttribute("match["+i+"]", match[i]);

        }
//        model.addAttribute("match", match[0]);
//        model.addAttribute("match1", match[1]);
//        model.addAttribute("match2", match[2]);
        List<Match> matches = matchRepository.findAll();
        model.addAttribute("matches", matches);
        model.addAttribute("summoner", summoner);



        return "basic/search";
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
}
