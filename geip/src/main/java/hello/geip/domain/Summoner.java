package hello.geip.domain;

public class Summoner {

    private String summonerName;
    private int iconId;
    private int level;
    private String puuid;

    public String getPuuid() {
        return puuid;
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

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
}
