package hello.geip.service;

import hello.geip.dto.MatchNameAndPlayerListDTO;
import hello.geip.dto.TeamDto;
import hello.geip.mapper.TeamBuildingRiotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamBuildingRiotServiceImpl implements TeamBuildingRiotService{

    @Autowired
    private TeamBuildingRiotMapper buildingRiotMapper;


    @Override
    public int insertTeamBuild(MatchNameAndPlayerListDTO matchNameAndPlayerListDTO) throws Exception {
        int redCnt=0;
        int blueCnt=0;

        String redGroupId="";
        String blueGroupId="";
        for(TeamDto dto :matchNameAndPlayerListDTO.getPlayer()) {
            dto.setTeam(dto.getTeam().replaceAll("team_", ""));

            if(dto.getTeam().equals("red")) {
                if(redCnt==0) {
                    redGroupId= UUID.randomUUID().toString().replace("-", "");
                }
                ++redCnt;

                dto.setGroupId(redGroupId);
            }else {
                if(blueCnt==0) {
                    blueGroupId=UUID.randomUUID().toString().replace("-", "");
                }
                ++blueCnt;

                dto.setGroupId(blueGroupId);
            }

        }
        return buildingRiotMapper.insertTeamBuild(matchNameAndPlayerListDTO);
    }
}
