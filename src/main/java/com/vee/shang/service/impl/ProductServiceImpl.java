package com.vee.shang.service.impl;

import com.github.pagehelper.PageHelper;
import com.vee.shang.entity.Product;
import com.vee.shang.mapper.ProductMapper;
import com.vee.shang.service.ProductService;
import com.vee.shang.service.UserService;
import com.vee.shang.util.Page;
import com.vee.shang.util.TimeUtil;
import com.vee.shang.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author cgd
 * @date 2017/11/20.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserService userService;


    @Transactional
    @Override
    //@CacheEvict(value = "product", key = "'product:'+#reqProductVo.userId+'*'")
    public Long save(ReqProductVo reqProductVo) {
        Product product = new Product();
        product.setUserId(reqProductVo.getUserId());
        product.setProductName(reqProductVo.getProductName());
        product.setProductDsc(reqProductVo.getProductDsc());
        product.setSource(reqProductVo.getSource());
        product.setStyleId(reqProductVo.getStyleId());
        product.setStyleChildId(reqProductVo.getStyleChildId());
        product.setCreateTime(new Date());
        productMapper.save(product);
        //保存图片
        String[] images = reqProductVo.getProductImg().split(",");
        List<String> imgList = Arrays.asList(images);
        productMapper.saveImg(product.getId(), imgList);

        if (reqProductVo.getProductModel() != null && reqProductVo.getProductModel().size() > 0) {
            List<ReqProductModelVo> productModel = reqProductVo.getProductModel();
            List<String> colorList = new ArrayList<String>();
            for (ReqProductModelVo rpmv : productModel) {
                colorList.add(rpmv.getColorId().toString());
                //保存尺寸
                if (!StringUtils.isBlank(rpmv.getSizeIds())) {
                    String[] sizes = rpmv.getSizeIds().split(",");
                    List<String> sizeList = Arrays.asList(sizes);
                    productMapper.saveSize(product.getId(), rpmv.getColorId(), sizeList);
                }
            }
            //保存颜色
            productMapper.saveColor(product.getId(), colorList);
        }
        userService.updateProductCount(reqProductVo.getUserId(), "add");
        return product.getId();
    }

    @Override
    public Long update(ReqProductVo reqProductVo) {
        return null;
    }

    @Override
    //@Cacheable(value = "product", key = "'product:'+#userId+':newList'+#pageSize+':'+#pageNo")
    public Page getNewList(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<RespProductNewVo> productList = productMapper.getNewList(userId);

        for (RespProductNewVo product : productList) {
            List<String> imgList = productMapper.getImgList(product.getId());
            List<String> imgResultList = new ArrayList<String>();
            if (imgList.size() >= 4) {
                imgList = imgList.subList(0, 4);
                imgResultList.addAll(imgList);
            } else if (imgList.size() < 4 && imgList.size() > 0) {
                imgList = imgList.subList(0, 1);
                imgResultList.addAll(imgList);
            }
            product.setImgList(imgResultList);
        }
        Page page = new Page(productList);
        return page;
    }

    @Override
    //@Cacheable(value = "product", key = "'product:'+#userId+':list:'+#pageSize+':'+#pageNo")
    public Page getList(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<RespProductVo> productList = productMapper.getList(userId);

        for (RespProductVo product : productList) {
            product.setCreateTimeStr(TimeUtil.getTimeStr(product.getCreateTime()));
            product.setImgList(productMapper.getImgList(product.getId()));
        }
        Page page = new Page(productList);
        return page;
    }

    @Override
    //@CacheEvict(value = "product", key = "'product:'+#reqProductVo.userId+':*'")
    public Long delete(ReqProductVo reqProductVo) {
        Long count = 0L;
        Product product = productMapper.getById(reqProductVo.getProductId());
        if(product!=null){
            count = productMapper.delete(reqProductVo.getUserId(), reqProductVo.getProductId());
            if (count > 0) {
                long dayDiff= TimeUtil.getDayDiff(product.getCreateTime());
                if(dayDiff<=7){
                    userService.updateNewProductCount(reqProductVo.getUserId(), "delete", dayDiff);
                }else{
                    userService.updateProductCount(reqProductVo.getUserId(), "delete");
                }
            }
        }
        return count;
    }


    @Override
    //@Cacheable(value = "product", key = "'product:'+#userId+':allList:'+#pageSize+':'+#pageNo+':'+#styleId+':'+#styleChildId")
    public Page getAllList(Long userId, Integer pageNo, Integer pageSize,Integer styleId,Integer styleChildId) {
        System.out.println("全部商品列表");
        PageHelper.startPage(pageNo, pageSize);
        Page page = new Page(productMapper.getAllList(userId,styleId,styleChildId));
        return page;
    }

    @Override
    public Page searchList(Long userId, Integer pageNo, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNo, pageSize);
        Page page = new Page(productMapper.searchList(userId, keyWord));
        return page;
    }

    @Override
    public Product getById(Long productId) {
        Product product = productMapper.getById(productId);
        product.setImgList(productMapper.getImgList(productId));
        List<Map<String, Object>> colorList = productMapper.getColorList(productId);
        for (Map<String, Object> color : colorList) {
            List<Map<String, Object>> sizeList = productMapper.getSizeList(productId, Long.parseLong(color.get("colorId").toString()));
            color.put("sizeList", sizeList);
        }
        product.setColorList(colorList);
        product.setWebUrl("http://www.veeshang.com/product/detail.html?productId="+productId);
        return product;
    }

    @Override
    public void praise(ReqProductPraiseVo reqProductPraiseVo) {
        if(reqProductPraiseVo.getPraiseType()==1){
            productMapper.savePraise(reqProductPraiseVo);
        }else {
            productMapper.cancelPraise(reqProductPraiseVo);
        }

    }

    @Override
    public Integer checkIsPraise(Long userId, Long productId) {
        return productMapper.checkIsPraise(userId,productId);
    }

    @Override
    //@CacheEvict(value = "product", key = "'product:'+#reqProductVo.userId+':*'")
    public void editTop(ReqProductVo reqProductVo) {
        productMapper.editTop(reqProductVo);
    }


}
