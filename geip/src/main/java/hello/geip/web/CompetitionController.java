package hello.geip.web;

import hello.geip.bean.CompetitionVo;
import hello.geip.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @RequestMapping("/record/table")
    private String competitionList(Model model, HttpServletRequest request) {
        return "record/table";
    }


    @ResponseBody
    @RequestMapping("/api/record/table/{nick}")
    private ResponseEntity<?> apiCompetitionList(@PathVariable String nick, Model model, HttpServletRequest request) {
        Map<String,Object> parmMap=new HashMap<>();
        parmMap.put("nick", nick);
        parmMap.put("team", "red");
        //레드팀 가져오기
        List<CompetitionVo> redTeamList = competitionService.apiCompetitionList(parmMap);

        //블루팀 가져오기
        parmMap.put("team", "blue");
        List<CompetitionVo> blueTeamList = competitionService.apiCompetitionList(parmMap);

        parmMap.put("redTeamList", redTeamList);
        parmMap.put("blueTeamList", blueTeamList);
        return ResponseEntity.status(HttpStatus.OK).body(parmMap);
    }



    @ResponseBody
    @PutMapping("/api/record/update")
    private ResponseEntity<?> apiCompetitionUpdate(String winGroupId, String loseGroupId) {
        competitionService.apiCompetitionUpdate(winGroupId, loseGroupId);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }


    @ResponseBody
    @GetMapping("/api/record/matchName")
    private ResponseEntity<?> matchNameCheck(String competitionName) {
        int cnt=competitionService.matchNameCheck(competitionName);
        return ResponseEntity.status(HttpStatus.OK).body(cnt);
    }


}
