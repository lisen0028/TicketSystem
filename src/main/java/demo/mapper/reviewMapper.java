package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import demo.pojo.review;
import demo.pojo.ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import org.apache.ibatis.annotations.Param;



import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date 2024/6/17 0:37
 * @Description:
 */
@Mapper
public interface reviewMapper extends BaseMapper<review> {
    @Select("SELECT reviewid, reviewerid, rating, comment, reviewdate FROM review WHERE reviewerid = #{userId}")
    List<review> selectByUserId(Integer userId);

    @Select("SELECT rating as 评分, COUNT(*) as 人数 " +
            " From review " +
            "GROUP BY rating")
    List<Map<String, Object>> chartReview();

    @Select("SELECT reviewid, reviewerid, rating, comment, reviewdate FROM review WHERE reviewerid = #{userId}")
    IPage<review> SelectReviewsByUserId(Page<review> page, int userid);
}
