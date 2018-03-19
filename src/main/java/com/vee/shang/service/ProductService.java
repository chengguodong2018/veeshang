package com.vee.shang.service;

import com.vee.shang.entity.Product;
import com.vee.shang.util.Page;
import com.vee.shang.vo.ReqProductPraiseVo;
import com.vee.shang.vo.ReqProductVo;

/**
 * @author cgd
 * @date 2017/12/2.
 */
public interface ProductService {

    Long save(ReqProductVo reqProductVo);

    Long update(ReqProductVo reqProductVo);

    Page getAllList(Long userId,Integer pageNo,Integer pageSize,Integer styleId,Integer styleChildId);

    Product getById(Long productId);

    Page getNewList(Long userId, Integer pageNo, Integer pageSize);

    Page getList(Long userId, Integer pageNo, Integer pageSize);

    Long delete(ReqProductVo reqProductVo);

    Page searchList(Long userId, Integer pageNo, Integer pageSize, String keyWord);

    void praise(ReqProductPraiseVo reqProductPraiseVo);

    Integer checkIsPraise(Long userId, Long productId);

    void editTop(ReqProductVo reqProductVo);
}
