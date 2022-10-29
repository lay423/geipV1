package hello.geip.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class MatchNameAndPlayerListDTO {
    private String matchName;
    private List<TeamDto> player;
}
