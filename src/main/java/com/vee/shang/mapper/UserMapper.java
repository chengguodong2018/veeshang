package com.vee.shang.mapper;

import com.vee.shang.entity.UserDomain;
import com.vee.shang.vo.ReqRegisterVo;
import com.vee.shang.vo.ReqUserVo;
import com.vee.shang.vo.RespUserVo;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * @author cgd
 * @date 2017/11/20.
 */
public interface UserMapper {

    @Insert("INSERT INTO vs_user (nickname,mobile,password,head_img,openid,unionid,channel,create_time) " +
            "values (#{nickname},#{mobile},#{password},#{headImg},#{openid},#{unionid},#{channel},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "id")
    Long save(UserDomain userDomain);

    @Select("SELECT id FROM vs_user where mobile=#{mobile} and status=1")
    Long selectUserIdByMobile(String mobile);

    @Select("SELECT id as userId,nickname,head_img,cover_img,wechat_img,share_img,introduction,contact_phone " +
            "FROM vs_user where mobile=#{mobile} and password=#{password} and status=1")
    UserDomain getUserInfo(@Param("mobile") String mobile, @Param("password") String password);

    @Update("UPDATE vs_user SET password=#{password} where mobile=#{mobile}")
    Long changePwd(ReqRegisterVo reqRegisterVo);

    @Update("UPDATE vs_user SET share_img=#{shareImg} where id=#{userId}")
    Long updateShareImg(@Param("userId") Long userId, @Param("shareImg") String shareImg);

    void edit(ReqUserVo reqUserVo);

    @Select("SELECT head_img FROM vs_user where id=#{userId}")
    String getHeadImg(Long userId);

    @Select("SELECT nickname,head_img,wechat,introduction,new_product_count,cover_img," +
            "product_count,contact_phone FROM vs_user where id=#{userId}")
    RespUserVo getUserInfoById(Long userId);

    void updateProductCount(Map<String, Object> params);

    @Select("SELECT id FROM vs_user where unionid=#{unionid}")
    Long getUserIdByUnionId(String unionid);

    @Select("SELECT id as userId,nickname,head_img,cover_img,wechat_img,share_img,mobile " +
            "FROM vs_user where unionid=#{unionid}")
    UserDomain getUserInfoByUnionid(String unionid);

    void syncNewProductCount(Map<String, Object> map);

    @Update("UPDATE vs_user SET new_product_count=0")
    void cleanAllNewProductCount();

}
