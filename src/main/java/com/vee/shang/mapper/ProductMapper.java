package com.vee.shang.mapper;

import com.vee.shang.entity.Product;
import com.vee.shang.vo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author cgd
 * @date 2017/11/20.
 */
public interface ProductMapper {


    @Insert("INSERT INTO vs_product (user_id,product_name,product_dsc,style_id,style_child_id,source,create_time) " +
            "values (#{userId},#{productName},#{productDsc},#{styleId},#{styleChildId},#{source},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long save(Product product);

    Long update(ReqStyleVo reqStyleVo);

    @Select("SELECT id,product_name,product_dsc,is_top,create_time FROM vs_product where user_id=#{userId} and status>=0 " +
            "and date(create_time)>=DATE_SUB(CURDATE(), INTERVAL 7 DAY) order by is_top desc,create_time desc")
    List<RespProductNewVo> getNewList(@Param("userId") Long userId);

    @Select("<script>" +
            "SELECT vp.id,vp.product_name,vpi.img,is_top FROM vs_product vp left join vs_product_img vpi " +
            "on vp.id=vpi.product_id where user_id=#{userId} and status>=0 " +
            "<if test='styleId!=null'> and style_id=#{styleId}</if>" +
            "<if test='styleChildId!=null'> and style_child_id=#{styleChildId}</if> " +
            "group by product_id order by vp.is_top desc,vp.create_time desc</script>")
    List<RespProductAllVo> getAllList(@Param("userId") Long userId,
                                      @Param("styleId") Integer styleId,
                                      @Param("styleChildId") Integer styleChildId);

    @Select("SELECT vp.id,vp.product_name,vpi.img FROM vs_product vp left join vs_product_img vpi on vp.id=vpi.product_id " +
            "where user_id=#{userId} and vp.status>=0 and instr(vp.product_name,#{keyWord})> 0 group by product_id " +
            "order by vp.is_top desc,vp.create_time desc")
    List<RespProductAllVo> searchList(@Param("userId") Long userId,
                                      @Param("keyWord") String keyWord);

    @Insert("<script>" +
            "insert into vs_product_img(product_id, img, create_time) "
            + "values "
            + "<foreach collection =\"imgList\" item=\"img\" index= \"index\" separator =\",\"> "
            + "(#{productId},#{img},NOW()) "
            + "</foreach > "
            + "</script>")
    void saveImg(@Param("productId") Long productId,
                 @Param("imgList") List<String> imgList);

    @Insert("<script>" +
            "insert into vs_product_color(product_id, color_id, create_time) "
            + "values "
            + "<foreach collection =\"colorList\" item=\"colorId\" index= \"index\" separator =\",\"> "
            + "(#{productId},#{colorId},NOW()) "
            + "</foreach > "
            + "</script>")
    void saveColor(@Param("productId") Long productId,
                   @Param("colorList") List<String> colorList);

    @Insert("<script>" +
            "insert into vs_product_size(product_id, color_id, size_id, create_time) "
            + "values "
            + "<foreach collection =\"sizeList\" item=\"sizeId\" index= \"index\" separator =\",\"> "
            + "(#{productId},#{colorId},#{sizeId},NOW()) "
            + "</foreach > "
            + "</script>")
    void saveSize(@Param("productId") Long productId,
                  @Param("colorId") Long colorId,
                  @Param("sizeList") List<String> sizeList);

    @Select("SELECT vp.id,user_id,product_name,product_dsc,vp.status,vu.nickname,vp.create_time FROM vs_product vp " +
            "join vs_user vu on vp.user_id=vu.id where vp.id=#{productId} and vp.status=1 ")
    Product getById(Long productId);

    @Select("SELECT img FROM vs_product_img where product_id=#{productId} and img_group=1 order by create_time asc")
    List<String> getImgList(Long productId);

    @Select("SELECT vpc.id,vpc.color_id as colorId,vpc.has_goods as hasGoods,vc.name FROM vs_product_color vpc " +
            "join vs_color vc on vpc.color_id=vc.id where product_id=#{productId}")
    List<Map<String, Object>> getColorList(Long productId);

    @Select("SELECT vps.id,vps.size_id as sizeId,vps.has_goods as hasGoods,vs.name FROM vs_product_size vps " +
            "join vs_size vs on vps.size_id=vs.id where vps.product_id=#{productId} and vps.color_id=#{colorId}")
    List<Map<String, Object>> getSizeList(@Param("productId") Long productId,
                                          @Param("colorId") Long colorId);

//    @Select("select vp.id,user_id,product_name,vu.nickname,vu.head_img,vp.create_time from vs_product vp " +
//            "join vs_user vu on vp.user_id=vu.id where (vp.user_id in (select user_id from vs_follow " +
//            "where follow_id=#{userId}) or user_id in (#{userId},1)) and vp.status>=0 order by create_time desc ")

    @Select("select vp.id,vp.user_id,product_name,vu.nickname,vu.head_img,vp.create_time from vs_product vp "
    + "join vs_user vu on vp.user_id=vu.id "
    + "join vs_follow  vf on vf.user_id=vp.user_id "
    + "where (vf.follow_id=#{userId} or vp.user_id in (1,#{userId})) and vp.status>=0 group by vp.id order by create_time desc")
    List<RespProductVo> getList(Long userId);

    @Update("update vs_product set status=-1 where id=#{productId} and user_id=#{userId}")
    Long delete(@Param("userId") Long userId,
                @Param("productId") Long productId);

    @Update("update vs_product set user_id=#{userId} where user_id=#{oldUserId}")
    void syncProduct(@Param("oldUserId") Long oldUserId,
                     @Param("userId") Long userId);

    @Select("SELECT count(1) newProductCount,user_id userId from vs_product where status>=0 and " +
            "date(create_time)>=DATE_SUB(CURDATE(), INTERVAL 7 DAY) group by user_id")
    List<Map<String, Object>> getAllNewProductCountList();

    @Insert("INSERT INTO vs_product_praise (user_id,product_id,create_time) " +
            "values (#{userId},#{productId},now())")
    void savePraise(ReqProductPraiseVo reqProductPraiseVo);

    @Delete("delete from vs_product_praise where user_id=#{userId} and product_id=#{productId}")
    void cancelPraise(ReqProductPraiseVo reqProductPraiseVo);

    @Select("SELECT count(1) from vs_product_praise where user_id=#{userId} and product_id=#{productId}")
    Integer checkIsPraise(@Param("userId") Long userId,@Param("productId") Long productId);

    @Update("update vs_product set is_top=#{isTop} where id=#{productId}")
    void editTop(ReqProductVo reqProductVo);

}
