package hello.geip.web;

import hello.geip.bean.BlueTeamVo;
import hello.geip.bean.CompetitionVo;
import hello.geip.bean.RedTeamVo;
import hello.geip.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/basic")
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @RequestMapping("/")
    private String competitionList(Model model, HttpServletRequest request){

        List<CompetitionVo> competitionList = new ArrayList<>();
        competitionList = competitionService.getCompetitionList();
        model.addAttribute("competitionList",competitionList);

        List<BlueTeamVo> blueteamList = new ArrayList<>();
        blueteamList = competitionService.getBlueTeamList();
        model.addAttribute("blueteamList",blueteamList);

        List<RedTeamVo> redteamList = new ArrayList<>();
        redteamList = competitionService.getRedTeamList();
        model.addAttribute("redteamList",redteamList);

        return "table";
    }
}
