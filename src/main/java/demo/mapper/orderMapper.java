package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.DTO.OrderEventDTO;
import demo.pojo.orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lsw
 * @Date 2024/3/26 19:39
 * @Description:
 */
@Mapper
public interface orderMapper extends BaseMapper<orders> {
    @Select("SELECT orderid, idcard, ticketid, orderdate, orderprice, paymentstatus, view, eventid FROM orders WHERE userid = #{userId}")
    List<orders> selectOrdersByUserId(@Param("userId") Integer userId);
    @Select("SELECT o.orderid, o.idcard, o.ticketid, o.orderdate, o.orderprice, o.paymentstatus, o.view, o.eventid " +
            "FROM orders o " +
            "JOIN event e ON o.eventid = e.eventid " +
            "WHERE e.eventname LIKE CONCAT('%', #{eventname}, '%') and  o.idcard = #{userId}")
    List<orders> selectOrdersByName(@Param("eventname") String eventname,@Param("userId") String userId);

    @Select("SELECT o.orderid, o.idcard, o.ticketid, o.orderdate, o.orderprice, o.paymentstatus, o.view, " +
            "e.eventid, e.eventname, e.eventdate, e.venue, e.description, e.imgaddress, e.classify, e.createdby " +
            "FROM orders o " +
            "JOIN event e ON o.eventid = e.eventid " +
            "WHERE o.idcard = #{userId} AND o.view = 1")
    List<OrderEventDTO> getOrdersPage(Page<?> page, @Param("userId") Integer userId);

    @Select("SELECT o.orderid, o.idcard, o.ticketid, o.orderdate, o.orderprice, o.paymentstatus, o.view, " +
            "e.eventid, e.eventname, e.eventdate, e.venue, e.description, e.imgaddress, e.classify, e.createdby " +
            "FROM orders o " +
            "JOIN event e ON o.eventid = e.eventid " +
            "WHERE o.orderid = #{orderId} ")
    OrderEventDTO getOrdersWithEventById(@Param("orderId") Integer orderId);
}
