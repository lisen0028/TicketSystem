package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.DTO.TicketEventDTO;
import demo.pojo.event;
import demo.pojo.orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.sql.Date;
import java.util.List;

/**
 * @Author lsw
 * @Date 2024/3/26 19:38
 * @Description:
 */
@Mapper
public interface eventsMapper extends BaseMapper<event> {
     List<event> selectUserId(Integer userId);
     @Select("SELECT e.eventid, e.eventname, e.eventdate, e.venue, e.description, e.imgaddress, e.classify, e.createdby, " +
             "t.ticketid, t.status, t.categoryid ,t.seatnumber " +
             "FROM event e " +
             "JOIN ticket t ON e.eventid = t.eventid " +
             "ORDER BY e.eventdate DESC")
     IPage<TicketEventDTO> selectEventsWithTickets(Page<?> page);

     @Select("SELECT e.eventid, e.eventname, e.eventdate, e.venue, e.description, e.imgaddress, e.classify, e.createdby, " +
             "t.ticketid, t.status, t.categoryid ,t.seatnumber " +
             "FROM event e " +
             "JOIN ticket t ON e.eventid = t.eventid " +
             "where e.createdby= #{userId} " +
             "ORDER BY e.eventdate DESC")
     IPage<TicketEventDTO> UselectEventsWithTickets(Page<?> page,int userId);

     @Select("SELECT e.eventid, e.eventname, e.eventdate, e.venue, e.description, e.imgaddress, e.classify, e.createdby, " +
             "t.ticketid, t.status, t.categoryid ,t.seatnumber " +
             "FROM event e " +
             "JOIN ticket t ON e.eventid = t.eventid  " +
             "WHERE e.eventname LIKE CONCAT('%', #{eventname}, '%') and  e.createdby=#{userId}")
     List<TicketEventDTO> selectEventsByName(@Param("eventname") String eventname, @Param("userId") String userId);

     List<event> searchEvents(String eventName, Date eventDate, String category);
}
