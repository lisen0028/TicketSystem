package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.pojo.review;
import demo.pojo.ticket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author
 * @Date 2024/6/17 0:37
 * @Description:
 */
@Mapper
public interface reviewMapper extends BaseMapper<review> {
    List<review> selectByUserId(Integer userId);
}
