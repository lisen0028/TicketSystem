package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.pojo.ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lsw
 * @Date 2024/3/26 19:40
 * @Description:
 */
@Mapper
public interface ticketsMapper extends BaseMapper<ticket> {
    List<ticket> selectTicketsByEventId(Integer eventId);

    String  getNullTickets();
}
