package hello.geip.mapper;

import hello.geip.bean.CompetitionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CompetitionMapper {

    List<CompetitionVo> getCompetitionList();
    List<CompetitionVo> apiCompetitionList(Map<String,Object> param);

    void apiCompetitionUpdate(Map<String, Object> param);

    int matchNameCheck(String matchName);
}
