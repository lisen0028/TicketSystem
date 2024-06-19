package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
            "WHERE e.eventname LIKE CONCAT('%', #{eventname}, '%')")
    List<orders> selectOrdersByName(@Param("eventname") String eventname);
}
