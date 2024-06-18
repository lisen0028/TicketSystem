package demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @Author lsw
 * @Date 2024/3/25 21:05
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class orders {
    @TableId(value = "orderid",type = IdType.AUTO)
    private String orderid;

    @Column(name = "idcard")
    private int idcard;

    @Column(name = "ticketid")
    private String ticketid;

    @Column(name = "orderdate")
    private Data orderdate;

    @Column(name = "orderprice")
    private float orderprice;

    @Column(name = "paymentstatus")
    private Paymentstatus paymentstatus;

    @Column(name = "view")
    private byte view;

    @Column(name = "eventid")
    private int eventid;

    public orders(int idcard, String ticketid, Data orderdate, float orderprice, Paymentstatus paymentstatus, byte view, int eventid) {
        this.idcard = idcard;
        this.ticketid = ticketid;
        this.orderdate = orderdate;
        this.orderprice = orderprice;
        this.paymentstatus = paymentstatus;
        this.view = view;
        this.eventid = eventid;
    }
}
