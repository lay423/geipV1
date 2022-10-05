package hello.geip.web.basic;

import hello.geip.domain.Match;
import hello.geip.domain.Summoner;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static hello.geip.web.basic.Key.API_KEY;

@Slf4j
public class Search {
    Summoner summoner;
    URL url;
    HttpURLConnection connection;

    final int NAME_TO_START = 6000;
//    final int NAME_TO_START = 2100;
    String response;
    public Search() {

    }

    public Summoner getSummoner(String summonerName) throws IOException {


        log.info("이름={}", summonerName);
        response = getApiDataByURL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName.replace(" ", "%20") + "?api_key=" + API_KEY);

        summoner = new Summoner(getSource("name", response),
                Integer.parseInt(getSource("profileIconId", response)),
                Integer.parseInt(getSource("summonerLevel", response)),
                getSource("puuid", response));

        return summoner;
    }

    public Match[] getMatchArr() throws IOException, ParseException {

        String[] matchId = new String[20];
        Match[] match = new Match[20];
        String item[] = new String[6];
        String team[] = new String[10];
        String gameMode;
        String winLose;
        String time = null;
        String timeStartTOEnd = null;
        String cs;
        double min = 0;
        double damagePercent;

        //matchId 20개 뽑아오기
        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + summoner.getPuUid() + "/ids?api_key=" + API_KEY);
        extractMatchId(response, matchId);

        //matchId 20개가지고 match20개 뽑아오는 반복문 시작
        for(int i=0; i<20; i++){
            response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                    + matchId[i] + "?api_key=" + API_KEY);

            //게임모드 저장
            gameMode = getGameMode();

            //타임스탬프
            try {
                time = getTimeStamp(response);
                min = getTimeStartTOEnd1(response);
                timeStartTOEnd = getTimeStartTOEnd(response);
            } catch (ParseException e) {
                log.info("타임스탬프 구하기 오류발생");
            }

            //팀 초상화 따오기
            getTeamIcon(team);


            //전체 매치정보에서 한사람 분량의 매치정보만 가져오기
            extractOnlyMatch(matchId[i]);

            //분당 cs 구하기
            cs = getSource("totalMinionsKilled", response);
            String scPerMinute = "("+String.valueOf(Math.round((Integer.parseInt(cs) / (min/60))*10)/10.0)+")";

            //승&패 정보 저장
            winLose = getSource("win", response);
            if(winLose.equals("true"))
                winLose = "승리";
            else if(winLose.equals("false"))
                winLose="패배";
            else
                winLose="오류";

            //아이템아이콘 따오기
            getItemIcon(item);

            //팀 데미지 퍼센트 구하기
            try{
                damagePercent = Double.parseDouble(getSource("damageTakenOnTeamPercentage", response));
                damagePercent = Math.round(damagePercent*1000)/10.0;
            }catch(StringIndexOutOfBoundsException e){
                damagePercent = 0.0;
            }


            //매치정보배열 초기화
            match[i] = new Match(i, gameMode, time, timeStartTOEnd, winLose,
                    "https://ddragon.leagueoflegends.com/cdn/12.18.1/img/champion/" +
                            getSource("championName", response)+".png",
                    getSource("champLevel", response),
                    getSource("\"kills\"", response),
                    getSource("deaths\"", response),
                    getSource("assist", response),
                    cs+scPerMinute, damagePercent+"%",
                    getSource("wardsPlaced", response),
                    item[0], item[1],item[2],item[3],item[4],item[5],
                    team[0], team[1], team[2], team[3], team[4],
                    team[5], team[6], team[7], team[8], team[9]);

            log.info("매치 등록 갯수={}", i);
        }
        //matchId 20개가지고 match20개 뽑아오는 반복문 끝
        return match;
    }

    //게임모드 할당하는 함수
    private String getGameMode() {
        String gameMode = getSource("queueId", response);
        int gameModeInt = Integer.parseInt(gameMode);
        if(gameModeInt==400)
            gameMode = "일반교차선택";
        else if(gameModeInt==420)
            gameMode = "솔랭";
        else if(gameModeInt==430)
            gameMode = "일반";
        else if(gameModeInt==440)
            gameMode = "자유랭크";
        else if(gameModeInt==450)
            gameMode = "무작위총력전";
        else if(gameModeInt==700)
            gameMode = "clash";
        else if(gameModeInt==800)
            gameMode = "입문";
        else if(gameModeInt==810)
            gameMode = "초보";
        else if(gameModeInt==820)
            gameMode = "중급";
        else if(gameModeInt==830)
            gameMode = "ai830";
        else if(gameModeInt==840)
            gameMode = "ai840";
        else if(gameModeInt==850)
            gameMode = "ai850";
        else if(gameModeInt==900)
            gameMode = "URF";
        else if(gameModeInt>=2000)
            gameMode = "튜토리얼";
        else
            gameMode = "미정";

        return gameMode;
    }


    //몇일전 게임인지 반환
    private String getTimeStamp(String response) throws ParseException {
        String time = convertDate("yyyy-MM-dd", getSource("gameEndTimestamp" ,response));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String timeNow = String.valueOf(now);

        Date d1 = format.parse( time );
        Date d2 = format.parse( timeNow );
        long Sec = (d2.getTime() - d1.getTime()) / 1000;
        long Days = Sec / (24*60*60);
        return String.valueOf(Days+"일전");
    }

    //시작to끝 시간 구하기
    private String getTimeStartTOEnd(String response) throws ParseException {

        String endTime = convertDate("YYYY-MM-dd HH:mm:ss", getSource("gameEndTimestamp" ,response));
        String startTime = convertDate("YYYY-MM-dd HH:mm:ss", getSource("gameStartTimestamp", response));


        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        Date d1 = format.parse( endTime );
        Date d2 = format.parse( startTime );

        long Min;
        long Sec;

        if(d1.getTime() > d2.getTime()){
            Min = (d1.getTime() - d2.getTime()) / 60000;
            Sec = ((d1.getTime() - d2.getTime()) / 1000)%60;
        }
        else {
            Min = (d1.getTime()+86400000 - d2.getTime()) / 60000;
            Sec = ((d1.getTime()+86400000 - d2.getTime()) / 1000)%60;
        }
        return String.valueOf(Min+"분 "+Sec+"초");
    }

    //시작to끝 Minute만 반환(분당CS계산용)
    private double getTimeStartTOEnd1(String response) throws ParseException {

        String endTime = convertDate("YYYY-MM-dd HH:mm:ss", getSource("gameEndTimestamp" ,response));
        String startTime = convertDate("YYYY-MM-dd HH:mm:ss", getSource("gameStartTimestamp", response));

        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        Date d1 = format.parse( endTime );
        Date d2 = format.parse( startTime );

        long Min;

        if(d1.getTime() > d2.getTime()){
            Min = (d1.getTime() - d2.getTime()) / 1000;
        }
        else {
            Min = (d1.getTime()+86400000 - d2.getTime()) / 1000;
        }
        return Min;
    }

    //Unix -> YYYY-MM-dd HH:mm:ss 변환
    private String convertDate(String format, String time){
        long timestamp = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(timestamp);
        return sdf.format(date);
    }

    //아이템아이콘 따오기
    private void getItemIcon(String[] item) {
        for(int j = 0; j< item.length; j++){
            String itemNum = getSource("item"+j, response);
            if(itemNum.equals("0"))
                item[j] = "default.png";
            else
                item[j] = "https://ddragon.leagueoflegends.com/cdn/12.18.1/img/item/" +
                    itemNum + ".png";
        }
    }

    //전체 매치정보에서 한사람 분량의 매치정보만 가져오기
    private void extractOnlyMatch(String matchId) throws IOException {
        response = getApiDataByURL("https://asia.api.riotgames.com/lol/match/v5/matches/"
                + matchId + "?api_key=" + API_KEY);
        try{
            response = response.substring(response.indexOf(summoner.getSummonerName()) - NAME_TO_START);
        } catch (StringIndexOutOfBoundsException e){
            //response = response.substring(response.indexOf(summoner.getSummonerName()) - NAME_TO_START);
            log.info("지안오류발생-튜토리얼게임");
        }

        response = response.substring(response.indexOf("assist"));
        response = response.substring(response.indexOf("assist"), response.indexOf("win")+12);
    }

    //팀 초상화 따오는 로직
    private void getTeamIcon(String[] team) {
        int target_num;
        for(int j = 0; j< team.length; j++){
            target_num = response.indexOf("championName");
            try{
                team[j] = "https://ddragon.leagueoflegends.com/cdn/12.18.1/img/champion/" +
                        getSource("championName", response) + ".png";;
            }catch (StringIndexOutOfBoundsException e){
                team[j] = "";
            }

            response = response.substring(target_num+"championName".length()+3);
        }
    }

    //라이엇 API URL을 받아서 String으로 반환
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

    //response 문자열에서 target 문자열에 해당하는 데이터를 반환
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

    //매치ID 뽑아오기
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
