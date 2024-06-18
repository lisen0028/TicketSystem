package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.pojo.event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author lsw
 * @Date 2024/3/26 19:38
 * @Description:
 */
@Mapper
public interface eventsMapper extends BaseMapper<event> {
     List<event> selectUserId(Integer userId);
}
