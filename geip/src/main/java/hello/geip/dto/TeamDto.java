package hello.geip.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamDto {

    private String team;
    private String username;
    private String rank;
    private String groupId;
}
