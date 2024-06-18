package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.pojo.ticket;
import demo.pojo.ticketcategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author
 * @Date 2024/6/16 8:14
 * @Description:
 */
@Mapper
public interface ticketcategoryMapper extends BaseMapper<ticketcategory> {
}
