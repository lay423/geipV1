package hello.geip.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class WarNameDTO {

    private String groupname;
    private List<TeamDto> teams;
}
