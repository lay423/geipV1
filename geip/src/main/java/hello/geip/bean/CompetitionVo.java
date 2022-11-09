package hello.geip.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompetitionVo {

    private long id;
    private String competition_name;
    private String lol_nick;
    private String team;
    private String win_lose;
    private String groupId;
}
