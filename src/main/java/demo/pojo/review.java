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
 * @Date 2024/6/14 23:59
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("review")
public class review {
    @TableId(value = "reviewid",type = IdType.AUTO)
    private int reviewid;

    @Column(name = "reviewerid")
    private int reviewerid;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "reviewdate")
    private java.sql.Date reviewdate;
}
