package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.pojo.ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author lsw
 * @Date 2024/3/26 19:40
 * @Description:
 */
@Mapper
public interface ticketsMapper extends BaseMapper<ticket> {
    List<ticket> selectTicketsByEventId(Integer eventId);

    @Update("UPDATE ticket " +
            "SET status = 'SOLD' " +
            "WHERE ticketid = #{ticketid}")
    int updateTicketStatusToSold(@Param("ticketid") String ticketid);
    @Select("SELECT ticketid " +
            "FROM ticket " +
            "WHERE status = 'AVAILABLE'  and categoryid = #{categoryid} "+
            "LIMIT 1")

    String selectNullTickets(Integer categoryid);
}
