package hello.geip.service;

import hello.geip.bean.BlueTeamVo;
import hello.geip.bean.CompetitionVo;
import hello.geip.bean.RedTeamVo;

import java.util.List;

public interface CompetitionService {

    List<CompetitionVo> getCompetitionList();
    List<RedTeamVo> getRedTeamList();
    List<BlueTeamVo> getBlueTeamList();
}
