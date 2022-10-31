package hello.geip.domain;

public class Summoner {

    private String name;
    private int profileIconId;
    private int summonerLevel;
    private String puuid;

    private String accountId;
    private long revisionDate;
    private String id;

    public Summoner() {
    }

    public Summoner(String summonerName, int iconId, int level, String puuid) {
        this.name = summonerName;
        this.profileIconId = iconId;
        this.summonerLevel = level;
        this.puuid = puuid;
    }
    public String getPuuid() {
        return puuid;
    }

    public String getName() {
        return name;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }



}
