package hello.geip.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedTeamVo {
    private long id;
    private String competition_name;
    private String lol_nick;
    private String team;
    private String win_lose;
}
