package demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lsw
 * @Date 2024/3/25 20:48
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("admins")
public class admins {
    @TableId(value = "adminid",type = IdType.AUTO)
    private String adminsid;
    private String username;
    private String password;
    private String email;
}
