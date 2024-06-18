package demo.pojo;
import javax.persistence.Column;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lsw
 * @Date 2024/3/25 20:52
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("event")
public class event {
    @TableId(value = "eventid",type = IdType.AUTO)
    private int eventid;

    @Column(name = "eventname")
    private String eventname;

    @Column(name = "eventdate")
    private Data eventdate;

    @Column(name = "venue")
    private String venue;

    @Column(name = "description")
    private String description;

    @Column(name = "createdby")
    private String createdby;

    @Column(name = "imgaddress")
    private String imgaddress;

    @Column(name = "classify")
    private classify classify;
}
