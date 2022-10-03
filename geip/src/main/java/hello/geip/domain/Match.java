package hello.geip.domain;

import lombok.Getter;

@Getter
public class Match {
    private String cs;
    private int id;
    private String gameMode;
    private String time;
    private String timeStartToEnd;
    private String win;
    private String championName;
    private String champLevel;
    //스펠, 룬, 아이템,
    private String kills;
    private String deaths;
    private String assist;
    private String wards;
    private String damagePercent;
    private String[] item;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;
    private String team1;
    private String team2;
    private String team3;
    private String team4;
    private String team5;
    private String team6;
    private String team7;
    private String team8;
    private String team9;
    private String team10;


    public Match(int id, String gameMode, String time, String timeStartToEnd, String win, String championName, String champLevel, String kills, String deaths, String assist, String cs, String damagePercent, String wards, String item1, String item2, String item3, String item4, String item5, String item6, String team1, String team2, String team3, String team4, String team5, String team6, String team7, String team8, String team9, String team10) {
        this.id = id;
        this.gameMode = gameMode;
        this.time = time;
        this.timeStartToEnd = timeStartToEnd;
        this.win = win;
        this.championName = championName;
        this.champLevel = champLevel;
        this.kills = kills;
        this.deaths = deaths;
        this.assist = assist;
        this.cs = cs;
        this.damagePercent = damagePercent;
        this.wards = wards;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.item6 = item6;
        this.team1 = team1;
        this.team2 = team2;
        this.team3 = team3;
        this.team4 = team4;
        this.team5 = team5;
        this.team6 = team6;
        this.team7 = team7;
        this.team8 = team8;
        this.team9 = team9;
        this.team10 = team10;
    }
}
