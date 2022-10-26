package hello.geip.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.geip.dto.*;
import hello.geip.service.RiotApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class KJYController {
    private final RiotApiService riotApiService;

    @GetMapping("/teamBuilding")
    public String teamBuilding(MultiSearchDto multiSearchDto, Model model){
        model.addAttribute("multiSearchDto", multiSearchDto);
        return "teambuild/teamBuilding";
    }

    @PostMapping("/teamBuilding")
    public String multiSearch(@ModelAttribute("multiSearchDto") MultiSearchDto multiSearchDto, Model model) throws JsonProcessingException {
        String userNicknames = multiSearchDto.getGameNicknames();
        List<TeamBuildingRiotApiDTO> teamBuildingRiotApiDTOS = riotApiService.teamBuildingSearchSummoner(userNicknames);
        for (TeamBuildingRiotApiDTO teamBuildingRiotApiDTO : teamBuildingRiotApiDTOS){
            System.out.println(teamBuildingRiotApiDTO);
        }

        model.addAttribute("multiSearchList", teamBuildingRiotApiDTOS);
        return "teambuild/teamBuilding";
    }

    @GetMapping("/recommendTB")
    public String recommendTeamBuildingGet(MultiSearchDto multiSearchDto, Model model){
        model.addAttribute("multiSearchDto", multiSearchDto);
        return "teambuild/recommendTeamBuilding";
    }

    @PostMapping("/recommendTeamBuilding")
    public String recommendTeamBuilding(@ModelAttribute("multiSearchDto") MultiSearchDto multiSearchDto, Model model) throws JsonProcessingException {
        String userNicknames = multiSearchDto.getGameNicknames();
        RecommendTBAndControllerDTO recommendTBAndControllerDTO = riotApiService.recommendTeamBuilding(userNicknames);
        List<RecommendTeamBuildingDto> redTeam = recommendTBAndControllerDTO.getRedTeam();
        List<RecommendTeamBuildingDto> blueTeam = recommendTBAndControllerDTO.getBlueTeam();
        model.addAttribute("redTeam", redTeam);
        model.addAttribute("blueTeam", blueTeam);
        return "teambuild/recommendTeamBuilding";
    }



}
