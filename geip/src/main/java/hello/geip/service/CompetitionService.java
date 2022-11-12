package hello.geip.service;

import hello.geip.bean.CompetitionVo;

import java.util.List;
import java.util.Map;

public interface CompetitionService {

    List<CompetitionVo> getCompetitionList();
    List<CompetitionVo> apiCompetitionList(Map<String,Object> param);

    void apiCompetitionUpdate(String winGroupId, String loseGroupId);

    int matchNameCheck(String competitionName);
}
