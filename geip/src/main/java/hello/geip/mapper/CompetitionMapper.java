package hello.geip.mapper;

import hello.geip.bean.CompetitionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompetitionMapper {

    List<CompetitionVo> getCompetitionList();
}
