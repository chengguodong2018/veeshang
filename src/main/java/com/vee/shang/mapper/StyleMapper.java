package com.vee.shang.mapper;

import com.vee.shang.entity.Style;
import com.vee.shang.entity.StyleChild;
import com.vee.shang.vo.ReqStyleChildVo;
import com.vee.shang.vo.ReqStyleVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cgd
 * @date 2017/11/20.
 */
public interface StyleMapper {


    @Insert("INSERT INTO vs_style (user_id,name,img,create_time) " +
            "values (#{userId},#{name},#{img},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "styleId", keyColumn = "id")
    Long save(Style style);

    Long update(ReqStyleVo reqStyleVo);

    @Select("SELECT id as styleId,name,img,status,sort FROM vs_style " +
            "where user_id=#{userId} order by sort asc")
    List<Style> getList(@Param("userId") Long userId);

    @Insert("INSERT INTO vs_style_child (style_id,name,img,create_time) " +
            "values (#{styleId},#{name},#{img},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "styleChildId", keyColumn = "id")
    void saveChild(StyleChild styleChild);

    Long updateChild(ReqStyleChildVo reqStyleChildVo);

    @Select("SELECT id as styleChildId,style_id,name,img,status,sort " +
            "FROM vs_style_child where style_id=#{styleId} order by sort asc")
    List<StyleChild> getChildList(Long styleId);

    @Select("SELECT count(1) FROM vs_product where style_id=#{styleId} and status=1")
    Long checkHavaProduct(Long styleId);

    @Delete("DELETE FROM vs_style where id=#{styleId}")
    Long delete(Long styleId);

    @Delete("DELETE FROM vs_style_child where id=#{styleChildId}")
    Long deleteChild(Long styleChildId);

    @Select("SELECT count(1) FROM vs_product where style_child_id=#{styleChildId} and status=1")
    Long checkHavaProductChild(Long styleChildId);

    @Update("UPDATE vs_style set user_id=#{userId} where user_id=#{oldUserId}")
    void syncStyle(@Param("oldUserId") Long oldUserId, @Param("userId") Long userId);

    @Select("SELECT count(1) FROM vs_style_child where style_id=#{styleId} and status=1")
    Integer getChildStyleCount(Long styleId);
}
