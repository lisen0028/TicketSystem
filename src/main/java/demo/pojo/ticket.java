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
 * @Date 2024/3/26 19:41
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ticket")
public class ticket {
    @TableId(value = "ticketid",type = IdType.AUTO)
    private String ticketid;

    @Column(name = "eventid")
    private String eventid;

    @Column(name = "categoryid")
    private String categoryid;

    @Column(name = "seatnumber")
    private String seatnumber;

    @Column(name = "status")
    private Status status;
}
