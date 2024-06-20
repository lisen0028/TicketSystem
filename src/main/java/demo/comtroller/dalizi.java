package demo.comtroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.DTO.OrderEventDTO;
import demo.DTO.TicketEventDTO;
import demo.mapper.*;


import demo.pojo.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author lisiwen
 * @Date 2024/3/5 9:03
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/dalizi")
public class dalizi {
    @Autowired
    userMapper userMapper;
    @Autowired
    eventsMapper eventsMapper;
    @Autowired
    orderMapper orderMapper;
    @Autowired
    ticketsMapper ticketsMapper;
    @Autowired
    adminsMapper adminsMapper;
    @Autowired
    ticketcategoryMapper ticketcategoryMapper;
    @Autowired
    reviewMapper  reviewMapper;
//用户管理：
//修改个人信息
@PutMapping("/updateUser/{id}")
public user updateUser(@PathVariable("id") Integer id, @RequestBody user user) {
    user existingUser = userMapper.selectById(id);
    if (existingUser != null) {
        user.setIdcard(id);
        userMapper.updateById(user);
        return user;
    } else {
        return null;
    }
}
    //查询个人信息
    @GetMapping("/selUser/{id}")
    public user selUser(@PathVariable("id") Integer id) {
        return userMapper.selectById(id);
    }

    //注册添加用户
    @PostMapping("/addUser")
    public int addUser(@RequestBody user user) {
        return userMapper.insert(user);
    }

    //管理员分页查询所有用户信息
    @GetMapping("/getUsersPage/{pageNum}/{pageSize}")
    public List<user> getUsersPage(@PathVariable("pageNum") int pageNum,
                                   @PathVariable("pageSize") int pageSize) {
        Page<user> page = new Page<>(pageNum, pageSize);
        page.addOrder(OrderItem.desc("idcard")); // 降序排序

        IPage<user> users = userMapper.selectPage(page, null);
        return users.getRecords();
    }

    //管理员根据名字模糊查询用户信息
    @GetMapping("/userByName/{name}")
    public List<user> findByName(@PathVariable String name) {
        QueryWrapper<user> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", name);
        return userMapper.selectList(queryWrapper);
    }



//订单管理：


    //生成订单
    @PostMapping("/addOrder/{categoryid}/count")
    public String addOrder(@PathVariable("categoryid") Integer categoryid, @RequestParam("count") int[] count, @RequestBody orders orders) {
         int flag=0;
        for (int i = 0; i < count.length; i++) {

            //票务档次余票减一
            ticketcategory ticketcategory= ticketcategoryMapper.selectById(categoryid);
            if (ticketcategory == null || ticketcategory.getRemainquantity() <= 0) {
                throw new IllegalArgumentException("票务档次不存在或余票不足");
            }
            ticketcategory.setRemainquantity(ticketcategory.getRemainquantity()-1);
            ticketcategoryMapper.updateById(ticketcategory);

            //找到可出售的票
            String ticketid = ticketsMapper.selectNullTickets(categoryid);

            //将票的状态改成“已售出”
            int updatedRows = ticketsMapper.updateTicketStatusToSold(ticketid);
            if (updatedRows == 0) {
                throw new RuntimeException("更新票状态失败");
            }
            //为每个订单插入票
            orders.setTicketid(ticketid);
            //在表中插入订单
            orderMapper.insert(orders);
            flag++;
        }
        return "成功订购"+flag+"张票";
    }

    //修改订单为不可视，修改票务为可售
    @PutMapping("/delOrderView/{OrderId}")
    public boolean delOrderView(@PathVariable("OrderId") Integer OrderId) {
        if (updateOrderStatus(OrderId)&&updateTicketStatus(OrderId)){
            return true;
        }
        else return false;
    }

    //修改订单票务状态为可售
    public Boolean updateTicketStatus(Integer orderId) {
        ticket ticket = ticketsMapper.selectById(orderMapper.selectById(orderId).getTicketid());
        if (ticket != null) {
            ticket.setStatus(Status.AVAILABLE);
            ticketsMapper.updateById(ticket);
            return true;
        } else {
            return false;
        }
    }


//根据订单id，修改订单状态为不可视

    public Boolean updateOrderStatus(Integer orderId) {
        orders orders = orderMapper.selectById(orderId);
        if (orders != null) {
            orders.setView((byte)0);
            orderMapper.updateById(orders);
            return true;
        } else {
            return false;
        }
    }

    //修改订单为不可视,前后端交互版
    @PutMapping("/OrderIsUnView/{OrderId}")
    public boolean OrderIsUnView(@PathVariable("OrderId") Integer OrderId) {
        if (updateOrderStatus(OrderId)&&updateTicketStatus(OrderId)){
            return true;
        }
        else return false;
    }

    //批量修改订单状态为不可视
    @PutMapping("/delOrdersViewBatch")
    public String delOrdersViewBatch(@RequestBody List<Integer> orderIds) {
        System.out.println("进入删除方法");

        for (Integer accout : orderIds) {
            updateOrderStatus(accout);
        }
        return "Deleted " + orderIds.size() + " users successfully!";
    }

    //根据id修改订单支付状态为已支付
    @PutMapping("/updatePaymentStatus/{OrderId}")
    public float updatePaymentStatus(@PathVariable("OrderId") String OrderId) {
        orders existingOrders = orderMapper.selectById(OrderId);
        if (existingOrders != null) {
            existingOrders.setPaymentstatus(Paymentstatus.COMPLETED);
            orderMapper.updateById(existingOrders);
            return existingOrders.getOrderprice();
        } else {
            return 0;
        }
    }

    //退票
    //根据订单id修改相应票务状态为可售
    @PutMapping("/refundTicket/{OrderId}")
    public Boolean refundTicketById(@PathVariable("OrderId") Integer orderId) {
        ticket ticket = ticketsMapper.selectById(orderMapper.selectById(orderId).getTicketid());
        if (ticket != null) {
            ticket.setStatus(Status.AVAILABLE);
            ticketsMapper.updateById(ticket);
            return true;
        } else {
            return false;
        }
    }

    //退票
    //查询OrderEventDTO详细信息
    @GetMapping("/getOrderEventDTO/{OrderId}")
    public OrderEventDTO getOrderEventDTO(@PathVariable("OrderId") Integer OrderId) {
        return  orderMapper.getOrdersWithEventById(OrderId);
    }

    //取消订单
    @PutMapping("/cancelOrder/{orderId}")
    public Boolean cancelOrder(@PathVariable("orderId") Integer orderId) {
        orders orders = orderMapper.selectById(orderId);
        if (orders != null) {
            orders.setPaymentstatus(Paymentstatus.FAILED);
            orderMapper.updateById(orders);
            //票改成可售
            updateTicketStatus(orderId);
            return true;
        } else {
            return false;
        }
    }

    //据活动名字模糊查询个人订单
    @GetMapping("/getOrdersByName/{name}/{userid}")
    public List<orders> getOrdersByName(@PathVariable("name") String name,
                                        @PathVariable("userid") String userid) {
        return orderMapper.selectOrdersByName(name,userid);
    }

    //分页显示个人所有订单
    @GetMapping("/getOrdersPage/{userId}/{pageNum}/{pageSize}")
    public List<OrderEventDTO> getOrdersPageByUserIdGPT(@PathVariable("userId") Integer userId,
                                                        @PathVariable("pageNum") int pageNum,
                                                        @PathVariable("pageSize") int pageSize) {
        Page<OrderEventDTO> page = new Page<>(pageNum, pageSize);
        return orderMapper.getOrdersPage(page, userId);
    }

    //活动管理：


//根据活动名称模糊查询卖家创建的活动返回活动和票务表中价格
@GetMapping("/getEventsByName/{name}/{userId}")
public List<event> UsergetEventsByName(@PathVariable("name") String name,@PathVariable("userId") Integer userId) {
    QueryWrapper<event> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("eventname", name);
    queryWrapper.eq("createdby", userId); // 等价于 WHERE idcard = {userId}
    return eventsMapper.selectList(queryWrapper);
}

//管理员分页查询所有活动信息和票务表信息
    @GetMapping("/getEventsWithTickets/{pageNum}/{pageSize}")
public IPage<TicketEventDTO> getEventsWithTickets(@PathVariable("pageNum")int pageNum, @PathVariable("pageSize") int pageSize) {
    Page<TicketEventDTO> page = new Page<>(pageNum, pageSize);
    return eventsMapper.selectEventsWithTickets(page);
}

// 分页查询卖家创建的活动信息和票务表信息
@GetMapping("/getEventsWithTickets/{pageNum}/{pageSize}/{userid}")
public IPage<TicketEventDTO> UsergetEventsWithTickets(@PathVariable("pageNum")int pageNum
        , @PathVariable("pageSize") int pageSize
        ,@PathVariable("userid") int userid) {
    Page<TicketEventDTO> page = new Page<>(pageNum, pageSize);
    return eventsMapper.UselectEventsWithTickets(page,userid);
}

//按条件查询活动
@GetMapping("/search")
public ResponseEntity<?> searchEvents(
        @RequestParam(required = false) String eventName,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate,
        @RequestParam(required = false) String category) {

    List<event> events = eventsMapper.searchEvents(eventName, eventDate, category);
    return ResponseEntity.ok(events);
}

    //根据活动名称模糊查询卖家创建的活动
    @GetMapping("/getEventsWithTickets/{eventname}/{userid}")
    public List<TicketEventDTO> selectEventsTicketByName(@PathVariable("eventname")String eventname,
            @PathVariable("userid") String userid) {
        return eventsMapper.selectEventsByName(eventname,userid);
    }

    //修改活动：时间，地点，名字，描述
    @PutMapping("/updateEvent/{eventId}/{event}")
    public event updateEvent(@PathVariable("eventId") Integer eventId, @RequestBody event event) {
        event existingEvent = eventsMapper.selectById(eventId);
        if (existingEvent != null) {
            event.setEventid(eventId);
            eventsMapper.updateById(event);
            return event;
        } else {
            return null;
        }
    }

    //根据id删除活动
    @DeleteMapping("/delEvent/{eventId}")
    public boolean delEvent(@PathVariable("eventId") Integer eventId) {
        event event= eventsMapper.selectById(eventId);
        if (event==null){
            return false;
        }else {
            eventsMapper.deleteById(eventId);
            return true;
        }
    }
    //批量删除活动
    @DeleteMapping("/delEventsBatch")
    public String delEventsBatch(@RequestBody List<Integer> eventIds) {
        System.out.println("进入删除方法");
        for (Integer accout : eventIds) {
            delEvent(accout);
        }
        return "Deleted " + eventIds.size() + " users successfully!";
    }

    //活动名称模糊查询
    @GetMapping("/getEventsByName/{name}")
    public List<event> getEventsByName(@PathVariable("name") String name) {
        QueryWrapper<event> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", name);
        return eventsMapper.selectList(queryWrapper);
    }



//评价管理：

    //分页查询用户个人评价
    @GetMapping("/SelectReviewsByUserId/{pageNum}/{pageSize}/{userid}")
    public IPage<review> SelectReviewsByUserId(@PathVariable("pageNum")int pageNum
            , @PathVariable("pageSize") int pageSize
            ,@PathVariable("userid") int userid) {
        Page<review> page = new Page<>(pageNum, pageSize);
        return reviewMapper.SelectReviewsByUserId(page,userid);
    }
    //分页查询所有评价
    @GetMapping("/getReviewsPage/{pageNum}/{pageSize}")
    public IPage<review> getReviewsPage(@PathVariable("pageNum")int pageNum, @PathVariable("pageSize") int pageSize) {
        Page<review> page = new Page<>(pageNum, pageSize);
        return reviewMapper.selectPage(page, null);
    }


    //添加评价
    @PostMapping("/addReview")
    public int addReview(@RequestBody review review) {
        return reviewMapper.insert(review);
    }

    //评价统计
    @GetMapping("/chartReview")
    public List<Map<String, Object>> chartReview() {
        return reviewMapper.chartReview();
    }

}





