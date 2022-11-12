package hello.geip.service;

import hello.geip.bean.CompetitionVo;
import hello.geip.mapper.CompetitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    CompetitionMapper competitionMapper;

    @Override
    public List<CompetitionVo> getCompetitionList() {
        return competitionMapper.getCompetitionList();
    }

    @Override
    public List<CompetitionVo> apiCompetitionList(Map<String,Object> param) {
        return competitionMapper.apiCompetitionList(param);
    }

    @Transactional
    @Override
    public void apiCompetitionUpdate(String winGroupId, String loseGroupId) {
        Map<String, Object> param=new HashMap<>();

        //승리팀 업데이트
        param.put("status", "WIN");
        param.put("groupId", winGroupId);
        competitionMapper.apiCompetitionUpdate(param);

        //패배팀 업데이트
        param.put("status", "LOSE");
        param.put("groupId", loseGroupId);
        competitionMapper.apiCompetitionUpdate(param);

    }


    @Override
    public int matchNameCheck(String competitionName) {
        return competitionMapper.matchNameCheck(competitionName);
    }

}
