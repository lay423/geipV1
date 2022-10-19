package hello.geip.dao;

import hello.geip.domain.Match;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class MatchDao {
    ConnectionMaker connectionMaker = new AWSConnectionMaker();
    private String db_name = "`geip-match-db`.`match`";

    public void add(Match match, String summonerName) throws SQLException, ClassNotFoundException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO "+db_name+"\n" +
                "(`id`,`gameMode`,\n" +
                "`time`,`timeStartToEnd`,`win`,\n" +
                "`championName`,\n" +
                "`champLevel`,\n" +
                "`kills`,\n" +
                "`deaths`,\n" +
                "`assist`,\n" +
                "`cs`,\n" +
                "`damagePercent`,\n" +
                "`wards`,`item1`,`item2`,`item3`,`item4`,`item5`,`item6`,\n" +
                "`team1`,`team2`,`team3`,`team4`,`team5`,`team6`,`team7`,`team8`,`team9`,`team10`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        ps.setString(1, summonerName);
        ps.setString(2, match.getGameMode());
        ps.setString(3, match.getTime());
        ps.setString(4, match.getTimeStartToEnd());
        ps.setString(5, match.getWin());
        ps.setString(6, match.getChampionName());
        ps.setString(7, match.getChampLevel());
        ps.setString(8, match.getKills());
        ps.setString(9, match.getDeaths());
        ps.setString(10, match.getAssist());
        ps.setString(11, match.getCs());
        ps.setString(12, match.getDamagePercent());
        ps.setString(13, match.getWards());
        ps.setString(14, match.getItem1());
        ps.setString(15, match.getItem2());
        ps.setString(16, match.getItem3());
        ps.setString(17, match.getItem4());
        ps.setString(18, match.getItem5());
        ps.setString(19, match.getItem6());
        ps.setString(20, match.getTeam1());
        ps.setString(21, match.getTeam2());
        ps.setString(22, match.getTeam3());
        ps.setString(23, match.getTeam4());
        ps.setString(24, match.getTeam5());
        ps.setString(25, match.getTeam6());
        ps.setString(26, match.getTeam7());
        ps.setString(27, match.getTeam8());
        ps.setString(28, match.getTeam9());
        ps.setString(29, match.getTeam10());


        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public List<Match> get(String summonerName) throws SQLException, ClassNotFoundException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT Match_id,id,gameMode,time,timeStartToEnd,win,championName,champLevel,kills,deaths,assist,cs,damagePercent,wards,item1,item2,item3,item4,item5,item6,team1,team2,team3,team4,team5,team6,team7,team8,team9,team10 FROM "+db_name+" WHERE id=?");
        ps.setString(1, summonerName);
        ResultSet rs = ps.executeQuery();
        List<Match> matchs = new ArrayList<>();
        int i=0;
        while (rs.next()) {
            matchs.add(new Match(
                    i,
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
                    rs.getString("team10")));
            i++;
        }
        return matchs;
    }

    public void update(Match[] matches, String summonerName) throws SQLException, ClassNotFoundException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement ps1 = conn.prepareStatement("SELECT Match_id FROM "+db_name+" WHERE id=?");
        ps1.setString(1, summonerName);
        ResultSet rs = ps1.executeQuery();
        String[] match_ids = new String[20];
        int i=0;

        PreparedStatement ps = conn.prepareStatement("Update "+db_name+
                "(`id`,`gameMode`,\n" +
                "`time`,`timeStartToEnd`,`win`,\n" +
                "`championName`,\n" +
                "`champLevel`,\n" +
                "`kills`,\n" +
                "`deaths`,\n" +
                "`assist`,\n" +
                "`cs`,\n" +
                "`damagePercent`,\n" +
                "`wards`,`item1`,`item2`,`item3`,`item4`,`item5`,`item6`,\n" +
                "`team1`,`team2`,`team3`,`team4`,`team5`,`team6`,`team7`,`team8`,`team9`,`team10`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) WHERE Match_id=?;");
        while (rs.next()) {
            match_ids[i] = rs.getString("Match_id");
            ps.setString(1, summonerName);
            ps.setString(2, matches[i].getGameMode());
            ps.setString(3, matches[i].getTime());
            ps.setString(4, matches[i].getTimeStartToEnd());
            ps.setString(5, matches[i].getWin());
            ps.setString(6, matches[i].getChampionName());
            ps.setString(7, matches[i].getChampLevel());
            ps.setString(8, matches[i].getKills());
            ps.setString(9, matches[i].getDeaths());
            ps.setString(10, matches[i].getAssist());
            ps.setString(11, matches[i].getCs());
            ps.setString(12, matches[i].getDamagePercent());
            ps.setString(13, matches[i].getWards());
            ps.setString(14, matches[i].getItem1());
            ps.setString(15, matches[i].getItem2());
            ps.setString(16, matches[i].getItem3());
            ps.setString(17, matches[i].getItem4());
            ps.setString(18, matches[i].getItem5());
            ps.setString(19, matches[i].getItem6());
            ps.setString(20, matches[i].getTeam1());
            ps.setString(21, matches[i].getTeam2());
            ps.setString(22, matches[i].getTeam3());
            ps.setString(23, matches[i].getTeam4());
            ps.setString(24, matches[i].getTeam5());
            ps.setString(25, matches[i].getTeam6());
            ps.setString(26, matches[i].getTeam7());
            ps.setString(27, matches[i].getTeam8());
            ps.setString(28, matches[i].getTeam9());
            ps.setString(29, matches[i].getTeam10());
            ps.setString(30, match_ids[i]);
            i++;
        }
    }
}
