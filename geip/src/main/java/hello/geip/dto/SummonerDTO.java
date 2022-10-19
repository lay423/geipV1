package hello.geip.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SummonerDTO {

    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;

    public SummonerDTO() {
    }

    public SummonerDTO(String summonerName, int iconId, int level, String puuid) {
        this.name = summonerName;
        this.profileIconId = iconId;
        this.summonerLevel = level;
        this.puuid = puuid;
    }
}