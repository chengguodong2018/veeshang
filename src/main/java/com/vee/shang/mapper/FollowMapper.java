package com.vee.shang.mapper;

import com.vee.shang.vo.ReqFollowVo;
import com.vee.shang.vo.RespFollowDetailVo;
import com.vee.shang.vo.RespFollowVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cgd
 * @date 2017/11/20.
 */
public interface FollowMapper {

    @Insert("INSERT INTO vs_follow (user_id,follow_id,create_time) " +
            "values (#{userId},#{followId},now())")
    void save(@Param("userId") Long userId, @Param("followId") Long followId);

    @Select("select vf.id,vu.id as userId,vu.introduction,IFNULL(vf.remark_follow_name,vu.nickname) as nickname," +
            "vu.new_product_count as newProductCount,vu.product_count as productCount," +
            "vu.head_img headImg from vs_follow vf join vs_user vu on vf.user_id=vu.id where vf.follow_id in (#{userId},2)")
    List<RespFollowVo> getFollowList(Long userId);

    @Select("select vf.id,vu.id as userId,vu.introduction,IFNULL(vf.remark_fans_name,vu.nickname) as nickname," +
            "vu.new_product_count as newProductCount,vu.product_count as productCount," +
            "vu.head_img headImg from vs_follow vf join vs_user vu on vf.follow_id=vu.id where vf.user_id in (#{userId},1)")
    List<RespFollowVo> getFansList(Long userId);

    @Delete("DELETE FROM vs_follow where id=#{id}")
    Long delete(Long id);

    Long update(ReqFollowVo reqFollowVo);

    @Select("select vf.id,vu.id as userId,vu.introduction,IFNULL(vf.remark_fans_name,vu.nickname) as nickname," +
            "vu.new_product_count as newProductCount,vu.product_count as productCount," +
            "vu.head_img headImg from vs_follow vf join vs_user vu on vf.follow_id=vu.id " +
            "where vf.user_id in (#{userId},1) and instr(nickname,#{keyWord})> 0 ")
    List<RespFollowVo> searchFans(@Param("userId") Long userId, @Param("keyWord") String keyWord);

    @Select("select vf.id,vu.id as userId,vu.introduction,IFNULL(vf.remark_follow_name,vu.nickname) as nickname," +
            "vu.new_product_count as newProductCount,vu.product_count as productCount," +
            "vu.head_img headImg from vs_follow vf join vs_user vu on vf.user_id=vu.id " +
            "where vf.follow_id in (#{userId},2) and instr(nickname,#{keyWord})> 0")
    List<RespFollowVo> searchFollow(@Param("userId") Long userId, @Param("keyWord") String keyWord);

    @Update("UPDATE vs_follow set user_id=#{userId} where user_id=#{oldUserId}")
    void syncFollow(@Param("oldUserId") Long oldUserId,@Param("userId") Long userId);

    @Update("UPDATE vs_follow set follow_id=#{userId} where follow_id=#{oldUserId}")
    void syncFans(@Param("oldUserId") Long oldUserId, @Param("userId") Long userId);

    @Select("select remark_follow_name from vs_follow where follow_id=#{followId} and user_id=#{userId}")
    String getFollowNickName(@Param("followId") Long followId, @Param("userId") Long userId);

    @Select("select vf.id,dynamic_status,block_status," +
            "IFNULL(vf.remark_follow_name,vu.nickname) as nickname" +
            " from vs_follow vf join vs_user vu on vf.user_id=vu.id " +
            "where vf.follow_id=#{loginUserId} and vf.user_id=#{userId}")
    RespFollowDetailVo getFollowDetail(@Param("userId") Long userId,@Param("loginUserId") Long loginUserId);

    @Select("select vf.id,dynamic_status,fans_block_status," +
            "IFNULL(vf.remark_fans_name,vu.nickname) as nickname" +
            " from vs_follow vf join vs_user vu on vf.follow_id=vu.id " +
            "where vf.follow_id=#{userId} and vf.user_id=#{loginUserId}")
    RespFollowDetailVo getFansDetail(@Param("userId") Long userId,@Param("loginUserId") Long loginUserId);
}
