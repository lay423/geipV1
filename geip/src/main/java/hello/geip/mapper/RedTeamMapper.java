package hello.geip.mapper;

import hello.geip.bean.RedTeamVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RedTeamMapper {
    List<RedTeamVo> getRedTeamList();
}
