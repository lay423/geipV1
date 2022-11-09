package hello.geip.service;

import hello.geip.dto.MatchNameAndPlayerListDTO;

public interface TeamBuildingRiotService {
    public int insertTeamBuild(MatchNameAndPlayerListDTO matchNameAndPlayerListDTO) throws Exception;
}
