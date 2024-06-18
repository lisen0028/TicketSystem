package demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @Author lisiwen
 * @Date 2024/3/8 10:55
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class user {
    @TableId(value = "idcard",type = IdType.AUTO)
    private int idcard;

    @Column(name = "address")
    private String address;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "role")
    private Role role;
}

