package demo.DTO;

import demo.pojo.Paymentstatus;
import demo.pojo.classify;
import demo.pojo.event;
import demo.pojo.orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author
 * @Date 2024/6/19 9:23
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {
    private String orderid;
    private int idcard;
    private String ticketid;
    private Date orderdate;
    private float orderprice;
    private Paymentstatus paymentstatus;
    private byte view;
    private int eventid;
    private String eventname;
    private Date eventdate;
    private String venue;
    private String description;
    private String imgaddress;
    private classify classify;
    private String createdby;

    public OrderEventDTO(orders orders, event event) {
        this.orderid = orders.getOrderid();
        this.idcard = orders.getIdcard();
        this.ticketid = orders.getTicketid();
        this.orderdate = orders.getOrderdate();
        this.orderprice = orders.getOrderprice();
        this.paymentstatus = orders.getPaymentstatus();
        this.view = orders.getView();
        this.eventid = event.getEventid();
        this.eventname = event.getEventname();
        this.eventdate = event.getEventdate();
        this.venue = event.getVenue();
        this.description = event.getDescription();
        this.imgaddress = event.getImgaddress();
        this.classify = event.getClassify();
        this.createdby = event.getCreatedby();
    }
}
