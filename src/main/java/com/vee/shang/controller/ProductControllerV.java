package com.vee.shang.controller;

import com.vee.shang.entity.Product;
import com.vee.shang.service.FollowService;
import com.vee.shang.service.ProductService;
import com.vee.shang.util.Page;
import com.vee.shang.vo.ReqProductPraiseVo;
import com.vee.shang.vo.ReqProductVo;
import com.vee.shang.vo.RespCodeConstants;
import com.vee.shang.vo.RespResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 商品
 *
 * @author cgd
 * @date 2017/11/20.
 */
@RestController
@RequestMapping("/product")
@EnableAutoConfiguration
public class ProductControllerV {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private FollowService followService;

    /**
     * 新增商品
     *
     * @param reqProductVo
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RespResult add(@RequestBody ReqProductVo reqProductVo) {
        try {
            if (StringUtils.isEmpty(reqProductVo.getProductName())
                    || StringUtils.isEmpty(reqProductVo.getProductImg())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long productId = productService.save(reqProductVo);
            return new RespResult(RespCodeConstants.SUCCESS, productId);
        } catch (Exception e) {
            logger.info("新增商品异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "新增商品异常");
        }
    }

    /**
     * 修改商品
     *
     * @param reqProductVo
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RespResult update(@RequestBody ReqProductVo reqProductVo) {
        try {
            if (StringUtils.isEmpty(reqProductVo.getProductId())
                    || StringUtils.isEmpty(reqProductVo.getProductName())
                    || StringUtils.isEmpty(reqProductVo.getProductImg())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = productService.update(reqProductVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.FAIL, "修改的商品不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "修改商品成功");
        } catch (Exception e) {
            logger.info("修改商品异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "修改商品异常");
        }
    }

    /**
     * 删除商品
     *
     * @param reqProductVo
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RespResult delete(@RequestBody ReqProductVo reqProductVo) {
        try {
            if (StringUtils.isEmpty(reqProductVo.getProductId())
                    || StringUtils.isEmpty(reqProductVo.getUserId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = productService.delete(reqProductVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.SUCCESS, "清空的数据不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "删除商品成功");
        } catch (Exception e) {
            logger.info("删除商品异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "删除商品异常");
        }
    }

    /**
     * 我的的店铺-新品列表
     *
     * @param userId
     */
    @RequestMapping(value = "/newList", method = RequestMethod.GET)
    public RespResult getNewList(@RequestParam(name = "userId") Long userId,
                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            Page page = productService.getNewList(userId, pageNo, pageSize);
            return new RespResult(RespCodeConstants.SUCCESS, page);
        } catch (Exception e) {
            logger.info("获取商品列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取商品列表异常");
        }
    }

    /**
     * 我的的店铺-全部列表
     *
     * @param userId
     */
    @RequestMapping(value = "/allList", method = RequestMethod.GET)
    public RespResult getList(@RequestParam(name = "userId") Long userId,
                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize,
                              @RequestParam(name = "styleId", required = false) Integer styleId,
                              @RequestParam(name = "styleChildId", required = false) Integer styleChildId) {
        try {
            Page page = productService.getAllList(userId, pageNo, pageSize, styleId, styleChildId);
            return new RespResult(RespCodeConstants.SUCCESS, page);
        } catch (Exception e) {
            logger.info("获取商品列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取商品列表异常");
        }
    }

    /**
     * 商品详情
     *
     * @param productId
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public RespResult detail(@RequestParam(name = "userId", required = false) Long userId,
                             @RequestParam(name = "productId") Long productId) {
        try {
            Product product = productService.getById(productId);
            if (userId != null) {
                product.setIsPraise(productService.checkIsPraise(userId, productId));
                String markName = followService.getFollowNickName(userId,product.getUserId());
                if(markName!=null){
                    product.setNickname(followService.getFollowNickName(userId,product.getUserId()));
                }
            }
            return new RespResult(RespCodeConstants.SUCCESS, product);
        } catch (Exception e) {
            logger.info("获取商品详情异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取商品详情异常");
        }
    }

    /**
     * 我的的店铺-搜索商品
     *
     * @param userId
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public RespResult search(@RequestParam(name = "userId") Long userId,
                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                             @RequestParam(name = "keyWord") String keyWord) {
        try {
            Page page = productService.searchList(userId, pageNo, pageSize, keyWord);
            return new RespResult(RespCodeConstants.SUCCESS, page);
        } catch (Exception e) {
            logger.info("获取商品列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取商品列表异常");
        }
    }

    /**
     * 商品列表
     *
     * @param userId
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RespResult list(@RequestParam(name = "userId") Long userId,
                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            Page page = productService.getList(userId, pageNo, pageSize);
            return new RespResult(RespCodeConstants.SUCCESS, page);
        } catch (Exception e) {
            logger.info("获取商品列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取商品列表异常");
        }
    }

    /**
     * 商品点赞
     *
     * @param reqProductPraiseVo
     */
    @RequestMapping(value = "/praise", method = RequestMethod.POST)
    public RespResult praise(@RequestBody ReqProductPraiseVo reqProductPraiseVo) {
        String msg = "点赞";
        try {
            if (StringUtils.isEmpty(reqProductPraiseVo.getUserId())
                    || StringUtils.isEmpty(reqProductPraiseVo.getProductId())
                    || StringUtils.isEmpty(reqProductPraiseVo.getPraiseType())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            if (reqProductPraiseVo.getPraiseType() == 0) {
                msg = "取消点赞";
            }
            productService.praise(reqProductPraiseVo);

            return new RespResult(RespCodeConstants.SUCCESS, msg + "成功");
        } catch (Exception e) {
            logger.info(msg + "异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, msg + "异常");
        }
    }

    /**
     * 删除商品
     *
     * @param reqProductVo
     */
    @RequestMapping(value = "/editTop", method = RequestMethod.POST)
    public RespResult editTop(@RequestBody ReqProductVo reqProductVo) {
        try {
            if (StringUtils.isEmpty(reqProductVo.getProductId())
                    || StringUtils.isEmpty(reqProductVo.getUserId())
                    || StringUtils.isEmpty(reqProductVo.getIsTop())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            productService.editTop(reqProductVo);
            return new RespResult(RespCodeConstants.SUCCESS, "置顶成功");
        } catch (Exception e) {
            logger.info("置顶商品异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "置顶异常");
        }
    }

}
