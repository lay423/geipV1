package hello.geip.dao;

import hello.geip.domain.Match;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Getter
@Repository
public class MatchDao {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    public MatchDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String DB_NAME = "`geip-match-db`.`match`";

    RowMapper<Match> rowMapper = (rs, rowNum)-> {
        return new Match(
                Integer.parseInt(rs.getString("Match_id")),
                rs.getString("gameMode"),
                rs.getString("time"),
                rs.getString("timeStartToEnd"),
                rs.getString("win"),
                rs.getString("championName"),
                rs.getString("champLevel"),
                rs.getString("kills"),
                rs.getString("deaths"),
                rs.getString("assist"),
                rs.getString("cs"),
                rs.getString("damagePercent"),
                rs.getString("wards"),
                rs.getString("item1"),
                rs.getString("item2"),
                rs.getString("item3"),
                rs.getString("item4"),
                rs.getString("item5"),
                rs.getString("item6"),
                rs.getString("team1"),
                rs.getString("team2"),
                rs.getString("team3"),
                rs.getString("team4"),
                rs.getString("team5"),
                rs.getString("team6"),
                rs.getString("team7"),
                rs.getString("team8"),
                rs.getString("team9"),
                rs.getString("team10"));
    };

    public int add(Match match, String summonerName) {
        return this.jdbcTemplate.update("INSERT INTO match(`id`,`gameMode`," +
                "`time`,`timeStartToEnd`,`win`," +
                "`championName`," +
                "`champLevel`," +
                "`kills`," +
                "`deaths`," +
                "`assist`," +
                "`cs`," +
                "`damagePercent`," +
                "`wards`,`item1`,`item2`,`item3`,`item4`,`item5`,`item6`," +
                "`team1`,`team2`,`team3`,`team4`,`team5`,`team6`,`team7`,`team8`,`team9`,`team10`)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                summonerName
                , match.getGameMode(), match.getTime(), match.getTimeStartToEnd(), match.getWin()
                , match.getChampionName(), match.getChampLevel(), match.getKills(), match.getDeaths()
                , match.getAssist(), match.getCs(), match.getDamagePercent(), match.getWards()
                , match.getItem1(), match.getItem2(), match.getItem3(), match.getItem4()
                , match.getItem5(), match.getItem6(), match.getTeam1(), match.getTeam2()
                , match.getTeam3(), match.getTeam4(), match.getTeam5(), match.getTeam6()
                , match.getTeam7(), match.getTeam8(), match.getTeam9(), match.getTeam10()
                );

    }
    public List<Match> get(String summonerName) {
        List<Match> matches = this.jdbcTemplate.query("SELECT Match_id,id,gameMode,time,timeStartToEnd,win,championName,champLevel,kills,deaths,assist,cs,damagePercent,wards,item1,item2,item3,item4,item5,item6,team1,team2,team3,team4,team5,team6,team7,team8,team9,team10 FROM "+ DB_NAME +" WHERE id=?", rowMapper, summonerName);
        return !matches.isEmpty() ? matches : null;
    }

    public List<Integer> getMatch_ids(String summonerName) {
        List<Integer> match_ids;
        match_ids = this.jdbcTemplate.query("SELECT Match_id FROM "+ DB_NAME +" WHERE id=?",
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                },  summonerName);
        return match_ids;
    }

    public int update(Match match, String summonerName, int match_id){
        return jdbcTemplate.update("Update "+ DB_NAME +" SET `gameMode`=?," +
                "`time`=?,`timeStartToEnd`=?,`win`=?," +
                "`championName`=?," +
                "`champLevel`=?," +
                "`kills`=?," +
                "`deaths`=?," +
                "`assist`=?," +
                "`cs`=?," +
                "`damagePercent`=?," +
                "`wards`=?,`item1`=?,`item2`=?,`item3`=?,`item4`=?,`item5`=?,`item6`=?," +
                "`team1`=?,`team2`=?,`team3`=?,`team4`=?,`team5`=?,`team6`=?,`team7`=?,`team8`=?,`team9`=?,`team10`=? WHERE Match_id=?"
                , match.getGameMode(), match.getTime(), match.getTimeStartToEnd(), match.getWin()
                , match.getChampionName(), match.getChampLevel(), match.getKills(), match.getDeaths()
                , match.getAssist(), match.getCs(), match.getDamagePercent(), match.getWards()
                , match.getItem1(), match.getItem2(), match.getItem3(), match.getItem4()
                , match.getItem5(), match.getItem6(), match.getTeam1(), match.getTeam2()
                , match.getTeam3(), match.getTeam4(), match.getTeam5(), match.getTeam6()
                , match.getTeam7(), match.getTeam8(), match.getTeam9(), match.getTeam10(), match_id);
    }
}
