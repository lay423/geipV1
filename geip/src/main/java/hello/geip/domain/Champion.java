package hello.geip.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Champion {

    private Long id;
    private String championName;
    private String role;
    private String line;

    public Champion() {
    }

    public Champion(String championName, String role, String line) {
        this.championName = championName;
        this.role = role;
        this.line = line;
    }
}
