package demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @Author
 * @Date 2024/6/14 23:28
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ticketcategory")
public class ticketcategory {
    @TableId(value = "categoryid",type = IdType.AUTO)
    private int categoryid;

    @Column(name = "eventid")
    private int eventid;

    @Column(name = "categoryname")
    private String categoryname;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "totalquantity")
    private int totalquantity;

    @Column(name = "remainquantity")
    private int remainquantity;
}
