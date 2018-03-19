package com.vee.shang.controller;

import com.vee.shang.entity.Style;
import com.vee.shang.entity.StyleChild;
import com.vee.shang.service.StyleService;
import com.vee.shang.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类
 *
 * @author cgd
 * @date 2017/11/20.
 */
@RestController
@RequestMapping("/style")
@EnableAutoConfiguration
public class StyleControllerV {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private StyleService styleService;

    /**
     * 新增一级分类
     *
     * @param reqStyleVoVo
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RespResult add(@RequestBody ReqStyleVo reqStyleVoVo) {
        try {
            if (StringUtils.isEmpty(reqStyleVoVo.getName())
                    || StringUtils.isEmpty(reqStyleVoVo.getImg())
                    || StringUtils.isEmpty(reqStyleVoVo.getUserId())) {
                return new RespResult(RespCodeConstants.FAIL, "分类名称或分类图片不能为空");
            }
            Long styleId = styleService.save(reqStyleVoVo);
            return new RespResult(RespCodeConstants.SUCCESS, styleId);
        } catch (Exception e) {
            logger.info("新增分类异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "新增分类异常");
        }
    }

    /**
     * 修改一级分类
     *
     * @param reqStyleVo
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RespResult update(@RequestBody ReqStyleVo reqStyleVo) {
        try {
            if (StringUtils.isEmpty(reqStyleVo.getStyleId())
                    || StringUtils.isEmpty(reqStyleVo.getUserId())
                    || (StringUtils.isEmpty(reqStyleVo.getImg()) && StringUtils.isEmpty(reqStyleVo.getName()))) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = styleService.update(reqStyleVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.FAIL, "修改的分类不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "修改分类成功");
        } catch (Exception e) {
            logger.info("修改一级分类异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "修改一级分类异常");
        }
    }

    /**
     * 删除一级分类
     *
     * @param reqStyleVo
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RespResult deleteSize(@RequestBody ReqStyleVo reqStyleVo) {
        try {
            if (StringUtils.isEmpty(reqStyleVo.getStyleId())
                    || StringUtils.isEmpty(reqStyleVo.getUserId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = styleService.delete(reqStyleVo);
            if (count == -1L) {
                return new RespResult(RespCodeConstants.FAIL, "该分类关联了商品,不能删除");
            } else if (count < 1) {
                return new RespResult(RespCodeConstants.SUCCESS, "清空的数据不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "删除分类成功");
        } catch (Exception e) {
            logger.info("删除分类异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "删除分类异常");
        }
    }

    /**
     * 获取分类列表
     *
     * @param userId
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public RespResult getList(Long userId) {
        try {
            List<Style> list = styleService.getList(userId);
            return new RespResult(RespCodeConstants.SUCCESS, list);
        } catch (Exception e) {
            logger.info("获取分类列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取分类列表异常");
        }
    }

    /**
     * 新增二级分类
     *
     * @param reqStyleChildVo
     */
    @RequestMapping(value = "/addChild", method = RequestMethod.POST)
    public RespResult add(@RequestBody ReqStyleChildVo reqStyleChildVo) {
        try {
            if (StringUtils.isEmpty(reqStyleChildVo.getName())
                    || StringUtils.isEmpty(reqStyleChildVo.getImg())
                    || StringUtils.isEmpty(reqStyleChildVo.getStyleId())) {
                return new RespResult(RespCodeConstants.FAIL, "上级分类、分类名称或分类图片不能为空");
            }
            Long styleChildId = styleService.saveChild(reqStyleChildVo);
            return new RespResult(RespCodeConstants.SUCCESS, styleChildId);
        } catch (Exception e) {
            logger.info("新增二级分类异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "新增二级分类异常");
        }
    }

    /**
     * 修改二级分类
     *
     * @param reqStyleChildVo
     */
    @RequestMapping(value = "/updateChild", method = RequestMethod.POST)
    public RespResult update(@RequestBody ReqStyleChildVo reqStyleChildVo) {
        try {
            if ((StringUtils.isEmpty(reqStyleChildVo.getName())
                    && StringUtils.isEmpty(reqStyleChildVo.getImg())) ||
                    StringUtils.isEmpty(reqStyleChildVo.getStyleChildId())) {
                return new RespResult(RespCodeConstants.FAIL, "分类id、分类名称或分类图片不能为空");
            }
            Long count = styleService.updateChild(reqStyleChildVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.FAIL, "修改的二级分类不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "修改二级分类成功");
        } catch (Exception e) {
            logger.info("修改二级分类异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "修改二级分类异常");
        }
    }


    /**
     * 删除二级分类
     *
     * @param reqStyleChildVo
     */
    @RequestMapping(value = "/deleteChild", method = RequestMethod.POST)
    public RespResult deleteSize(@RequestBody ReqStyleChildVo reqStyleChildVo) {
        try {
            if (StringUtils.isEmpty(reqStyleChildVo.getStyleChildId())
                    || StringUtils.isEmpty(reqStyleChildVo.getStyleId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = styleService.deleteChild(reqStyleChildVo);
            if (count == -1L) {
                return new RespResult(RespCodeConstants.FAIL, "该分类关联了商品,不能删除");
            } else if (count < 1) {
                return new RespResult(RespCodeConstants.SUCCESS, "清空的数据不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "删除分类成功");
        } catch (Exception e) {
            logger.info("删除分类异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "删除分类异常");
        }
    }

    /**
     * 获取二级分类列表
     *
     * @param styleId
     */
    @RequestMapping(value = "/getChildList", method = RequestMethod.GET)
    public RespResult getChildList(Long styleId) {
        try {
            List<StyleChild> list = styleService.getChildList(styleId);
            return new RespResult(RespCodeConstants.SUCCESS, list);
        } catch (Exception e) {
            logger.info("获取二级分类列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取二级分类列表异常");
        }
    }
}
