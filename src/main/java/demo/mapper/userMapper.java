package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lisiwen
 * @Date 2024/3/8 11:00
 * @Description:
 */
@Mapper
public interface userMapper extends BaseMapper<user> {
    @Select("SELECT * FROM user WHERE username = #{name}")
    List<user> findByUsername(String name);

    List<user> selectUsers(int offset, int pageSize);

    int countUsers();
}
