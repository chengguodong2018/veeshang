package com.vee.shang.mapper;

import com.vee.shang.entity.Size;
import com.vee.shang.vo.ReqColorSizeVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cgd
 * @date 2017/11/20.
 */
public interface SizeMapper {

    @Insert("INSERT INTO vs_size (user_id,name,create_time) " +
            "values (#{userId},#{name},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long save(Size size);

    @Update("UPDATE vs_size set name=#{name} where id=#{id}")
    Long update(ReqColorSizeVo reqColorSizeVo);

    @Select("SELECT id,name FROM vs_size where user_id=#{userId} order by id asc")
    List<Size> getList(@Param("userId") Long userId);

    @Delete("DELETE FROM vs_size where id=#{sizeId}")
    Long delete(Long sizeId);

    @Update("UPDATE vs_size set user_id=#{userId} where user_id=#{oldUserId}")
    void syncSize(@Param("oldUserId") Long oldUserId,@Param("userId") Long userId);
}
