package hello.geip.domain;

import lombok.Builder;

public class Match {
    private int id;
    private String gameType;
    //ㅁ일전
    private String win;
    //ㅁ게임시간
    private String championName;
    private String champLevel;
    //스펠, 룬, 아이템,
    private String kills;
    private String deathsByEnemyChamps;
    private String assist;

    public Match(int id, String gameType, String win, String championName, String champLevel, String kills, String deathsByEnemyChamps, String assist) {
        this.id = id;
        this.gameType = gameType;
        this.win = win;
        this.championName = championName;
        this.champLevel = champLevel;
        this.kills = kills;
        this.deathsByEnemyChamps = deathsByEnemyChamps;
        this.assist = assist;
    }
}
