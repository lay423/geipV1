package hello.geip.domain.hjh;

public class Summoner {

    private String summonerName;
    private int iconId;
    private int level;
    private String puUid;

    public Summoner(String summonerName, int iconId, int level, String puuid) {
        this.summonerName = summonerName;
        this.iconId = iconId;
        this.level = level;
        this.puUid = puuid;
    }
    public String getPuUid() {
        return puUid;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public int getIconId() {
        return iconId;
    }

    public int getLevel() {
        return level;
    }



}
