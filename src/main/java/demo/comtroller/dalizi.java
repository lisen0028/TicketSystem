package demo.comtroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.mapper.*;


import demo.pojo.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//添加用户
    @PostMapping("/addUser")
    public int addUser(@RequestBody user user) {
        return userMapper.insert(user);
    }

    @GetMapping("/allUser")
    public List<user> getAll() {
        return userMapper.selectList(null);
    }

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


//修改订单为不可视，修改票务为可售
@PutMapping("/delOrderView/{OrderId}")
public boolean delOrderView(@PathVariable("OrderId") Integer OrderId) {
        if (updateOrderStatus(OrderId)&&updateTicketStatus(OrderId)){
            return true;
        }
        else return false;
}

//修改票务状态为可售

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


//根据票务id查询票务
    @GetMapping("/tickets/{Id}")
    public ticket getTickets(@PathVariable("Id") Integer Id) {
        ticket ticket = ticketsMapper.selectById(Id);
        return ticket;
    }


//分页展示个人所有订单
@GetMapping("/getOrdersPage/{userId}/{pageNum}/{pageSize}")
public List<orders> getOrdersPageByUserId(@PathVariable("userId") Integer userId,
                                          @PathVariable("pageNum") int pageNum,
                                          @PathVariable("pageSize") int pageSize) {
    Page<orders> page = new Page<>(pageNum, pageSize);
    page.addOrder(OrderItem.desc("orderdate")); // 降序排序

    // 构建查询条件，筛选指定用户ID且view为1的订单，分页条件
    QueryWrapper<orders> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("idcard", userId); // 等价于 WHERE idcard = {userId}
    queryWrapper.eq("view", 1); // 可选：如果需要只展示view为1的订单

    // 执行带有条件的分页查询
    IPage<orders> orders = orderMapper.selectPage(page, queryWrapper);

    return orders.getRecords();
}

//根据id修改订单支付状态
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

    //根据订单id查询订单
    @GetMapping("/getOrder/{OrderId}")
    public orders getOrder(@PathVariable("OrderId") String OrderId) {
        orders orders = orderMapper.selectById(OrderId);
        return orders;
    }

    //根据活动名字模糊查询个人订单
    @GetMapping("/getOrdersByName/{name}")
    public List<orders> getOrdersByName(@PathVariable("name") String name) {
        return orderMapper.selectOrdersByName( name);
    }

    //查询个人历史订单
    @GetMapping("/getOrdersByUserId/{UserId}")
    public List<orders> getOrdersByUserId(@PathVariable("UserId") Integer UserId) {
        List<orders> orders = orderMapper.selectOrdersByUserId(UserId);
        return orders;
    }


//查询用户评价信息
    @GetMapping("/GetViewByUser/{userId}")
    public List<review> GetViewByUser(@PathVariable("userId") Integer userId) {
        List<review> reviews= reviewMapper.selectByUserId(userId);
        return reviews;
    }


    //删除评论
    @DeleteMapping("/delView/{reviewId}")
    public boolean delView(@PathVariable("reviewId") Integer reviewId) {
        review review= reviewMapper.selectById(reviewId);
        if (review==null){
            return false;
        }else {
            reviewMapper.deleteById(reviewId);
            return true;
        }
    }

    //批量删除评论
    @DeleteMapping("/delViewsBatch")
    public String delViewsBatch(@RequestBody List<Integer> reviewIds) {
        System.out.println("进入删除方法");

        for (Integer accout : reviewIds) {
            delView(accout);
        }
        return "Deleted " + reviewIds.size() + " users successfully!";
    }


//查询卖家创建的活动返回活动和票务表中价格！！！！！！！！！！！！！！！！！！！！





    //根据活动名称模糊查询卖家创建的活动
    @GetMapping("/getEventsByName/{name}/{userId}")
    public List<event> UsergetEventsByName(@PathVariable("name") String name,@PathVariable("userId") Integer userId) {
        QueryWrapper<event> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("eventname", name);
        queryWrapper.eq("createdby", userId); // 等价于 WHERE idcard = {userId}
        return eventsMapper.selectList(queryWrapper);
    }



    //修改活动
    @PutMapping("/updateEvent/{eventId}")
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


//管理员：

//分页查询所有用户信息
    @GetMapping("/getUsersPage/{pageNum}/{pageSize}")
    public List<user> getUsersPage(@PathVariable("pageNum") int pageNum,
                                   @PathVariable("pageSize") int pageSize) {
        Page<user> page = new Page<>(pageNum, pageSize);
        page.addOrder(OrderItem.desc("idcard")); // 降序排序


        // 执行没条件的分页查询
        IPage<user> users = userMapper.selectPage(page, null);
        return users.getRecords();
    }


//模糊查询用户信息
@GetMapping("/userByName/{name}")
public List<user> findByName(@PathVariable String name) {
    QueryWrapper<user> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("username", name);
    return userMapper.selectList(queryWrapper);
}

//查询所有活动信息，返回活动表
    @GetMapping("/getEvents")
    public List<event> getEvents() {
        return eventsMapper.selectList(null);
    }



//
//    //添加活动，票务表要多相应数量的票
//    @PostMapping("/addEvent")
//    public String  addEvent(@RequestBody event event) {
//
//
//        return "";
//    }
////添加票务档次
//@PostMapping("/addTicketCategory/{eventid}/{ticketCate}")
//public boolean  addTicketCategory(@RequestBody ticketcategory ticketcategory,
//                                    @PathVariable("eventid") Integer eventid
//                                ){
//
//
//    return ;
//}





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


//查询所有评价
    @GetMapping("/getAllReviews")
    public List<review> getAllReviews() {
        List<review> reviews = reviewMapper.selectList(null);
        return reviews;
    }

//添加评价
    @PostMapping("/addReview")
    public int addReview(@RequestBody review review) {
        return reviewMapper.insert(review);
    }



//按条件查询活动





    //添加订单
    @PostMapping("/addOrder/{eventid}/{userid}/{date}")
    public String addOrder(
            @PathVariable("eventid") int eventid,
            @PathVariable("userid") int userid,
            @PathVariable("date") java.sql.Date date) {
        //添加订单
        //找一个为可售状态的票
        ticket ticket=ticketsMapper.selectById(ticketsMapper.getNullTickets());

        //当票已经售罄，ticket对象为空抛出错误

        //将该票状态该为不可出售
        ticket.setStatus(Status.SOLD);
        //更新票
        ticketsMapper.updateById(ticket);

        //查找该票的票务档次，余票减一,得到价格
        ticketcategory ticketcategory=ticketcategoryMapper.selectById(ticket.getCategoryid());
        ticketcategory.setRemainquantity(ticketcategory.getRemainquantity()-1);
        ticketcategoryMapper.updateById(ticketcategory);
        float price=ticketcategory.getPrice();

        //创建一个订单对象
        orders orders =new orders(userid,ticket.getTicketid(),date,price,Paymentstatus.PENDING,(byte) 1,eventid);

        //把订单插入数据库
        orderMapper.insert(orders) ;
        return orders.getOrderid();
    }


//添加活动
























    @GetMapping("/xiangqing")
    public String XiangQing() {

        return "详细信息";
    }

    @PostMapping("/add")
    public String add(String something) {

        return something + "添加座位";
    }



    @GetMapping("/allEvents")
    public List<event> getAllEvents() {
        return eventsMapper.selectList(null);
    }

    @GetMapping("/allOrders")
    public List<orders> getAllOrders() {
        return orderMapper.selectList(null);
    }

    @PostMapping("/addEvents")
    public int addEvents(@RequestBody event event) {
        return eventsMapper.insert(event);
    }




    @DeleteMapping("/delUser/{id}")
    public int delUser(@PathVariable("id") Integer id) {
        return userMapper.deleteById(id);
    }

    @GetMapping("/user/findByPage")
    public IPage getUserList(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize) {
        Page<user> page = new Page<>(pageNum, pageSize);
        QueryWrapper<user> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        IPage ipage = userMapper.selectPage(page, null);
        return ipage;
    }

    @DeleteMapping("/delUsersBatch")
    public String deleteUsersBatch(@RequestBody List<Integer> accouts) {
        System.out.println("进入删除方法");
        for (Integer accout : accouts) {
            userMapper.deleteById(accout);
        }
        return "Deleted " + accouts.size() + " users successfully!";
    }



}
