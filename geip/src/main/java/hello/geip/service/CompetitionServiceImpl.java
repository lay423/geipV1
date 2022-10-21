package hello.geip.service;

import hello.geip.bean.BlueTeamVo;
import hello.geip.bean.CompetitionVo;
import hello.geip.bean.RedTeamVo;
import hello.geip.mapper.BlueTeamMapper;
import hello.geip.mapper.CompetitionMapper;
import hello.geip.mapper.RedTeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService{

    @Autowired
    CompetitionMapper competitionMapper;
    @Autowired
    RedTeamMapper redTeamMapper;
    @Autowired
    BlueTeamMapper blueTeamMapper;

    @Override
    public List<CompetitionVo> getCompetitionList() {
        return competitionMapper.getCompetitionList();
    }

    @Override
    public List<RedTeamVo> getRedTeamList() {
        return redTeamMapper.getRedTeamList();
    }

    @Override
    public List<BlueTeamVo> getBlueTeamList() {
        return blueTeamMapper.getBlueTeamList();
    }
}
