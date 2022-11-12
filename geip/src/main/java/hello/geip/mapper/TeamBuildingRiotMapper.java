package hello.geip.mapper;

import hello.geip.dto.MatchNameAndPlayerListDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamBuildingRiotMapper {
    public int insertTeamBuild(MatchNameAndPlayerListDTO matchNameAndPlayerListDTO) throws Exception;
}
