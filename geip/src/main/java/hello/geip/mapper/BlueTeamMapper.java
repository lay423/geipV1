package hello.geip.mapper;

import hello.geip.bean.BlueTeamVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlueTeamMapper {
    List<BlueTeamVo> getBlueTeamList();
}
