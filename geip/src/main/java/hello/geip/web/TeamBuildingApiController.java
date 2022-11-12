package hello.geip.web;

import hello.geip.dto.MatchNameAndPlayerListDTO;
import hello.geip.service.TeamBuildingRiotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TeamBuildingApiController {

    private  final TeamBuildingRiotService teamBuildingRiotService;

    @PostMapping("/api/teambuilding")
    public HttpStatus teamBuild(@RequestBody MatchNameAndPlayerListDTO matchNameAndPlayerListDTO) throws Exception{
        System.out.println("/api/teambuilding  저장 :" +matchNameAndPlayerListDTO.toString());

        teamBuildingRiotService.insertTeamBuild(matchNameAndPlayerListDTO);
        return HttpStatus.OK;
    }

    @PostMapping("/api")
    public void postTest(Map<String, Object> requestData){
        requestData.forEach((key, value) -> {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
        });
    }

}
