package com.vee.shang.mapper;

import com.vee.shang.entity.Color;
import com.vee.shang.vo.ReqColorSizeVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cgd
 * @date 2017/11/20.
 */
public interface ColorMapper {

    @Insert("INSERT INTO vs_color (user_id,name,create_time) " +
            "values (#{userId},#{name},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long save(Color color);

    @Update("UPDATE vs_color set name=#{name} where id=#{id}")
    Long update(ReqColorSizeVo reqColorSizeVo);

    @Select("SELECT id,name FROM vs_color where user_id=#{userId} order by id asc")
    List<Color> getList(@Param("userId") Long userId);

    @Delete("DELETE FROM vs_color where id=#{colorId}")
    Long delete(Long colorId);

    @Update("UPDATE vs_color set user_id=#{userId} where user_id=#{oldUserId}")
    void syncColor(@Param("oldUserId") Long oldUserId,@Param("userId") Long userId);
}
