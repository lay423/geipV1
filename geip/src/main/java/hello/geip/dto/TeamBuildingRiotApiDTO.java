package hello.geip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamBuildingRiotApiDTO {
    private String name;
    private String tier;
    private String rank;
    private int wins;
    private int losses;
    private int leaguePoints;
    private String tierImage;

    @Builder
    public TeamBuildingRiotApiDTO(LeagueEntryDTO leagueEntryDTO){
        this.name = leagueEntryDTO.getSummonerName();
        this.tier = leagueEntryDTO.getTier();
        this.rank = leagueEntryDTO.getRank();
        this.wins = leagueEntryDTO.getWins();
        this.losses = leagueEntryDTO.getLosses();
        this.leaguePoints = leagueEntryDTO.getLeaguePoints();
    }
}
