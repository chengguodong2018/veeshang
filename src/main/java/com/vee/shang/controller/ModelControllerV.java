package com.vee.shang.controller;

import com.vee.shang.entity.Color;
import com.vee.shang.entity.Size;
import com.vee.shang.service.ColorService;
import com.vee.shang.service.SizeService;
import com.vee.shang.vo.ReqColorSizeVo;
import com.vee.shang.vo.RespCodeConstants;
import com.vee.shang.vo.RespResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品型号
 *
 * @author cgd
 * @date 2017/11/20.
 */
@RestController
@RequestMapping("/model")
@EnableAutoConfiguration
public class ModelControllerV {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;


    /**
     * 获取型号基本信息
     *
     * @param userId
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RespResult getList(Long userId) {
        try {
            Map<String, Object> result = new HashMap<>();
            List<Color> colorList = colorService.getList(userId);
            List<Size> sizeList = sizeService.getList(userId);
            result.put("sizeList", sizeList);
            result.put("colorList", colorList);
            return new RespResult(RespCodeConstants.SUCCESS, result);
        } catch (Exception e) {
            logger.info("获取尺寸列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取尺寸列表异常");
        }
    }

    /**
     * 新增颜色
     *
     * @param reqColorSizeVo
     */
    @RequestMapping(value = "/addColor", method = RequestMethod.POST)
    public RespResult addColor(@RequestBody ReqColorSizeVo reqColorSizeVo) {
        try {
            if (StringUtils.isEmpty(reqColorSizeVo.getName())
                    || StringUtils.isEmpty(reqColorSizeVo.getUserId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long ColorId = colorService.save(reqColorSizeVo);
            return new RespResult(RespCodeConstants.SUCCESS, ColorId);
        } catch (Exception e) {
            logger.info("新增颜色异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "新增颜色异常");
        }
    }

    /**
     * 修改颜色
     *
     * @param reqColorSizeVo
     */
    @RequestMapping(value = "/updateColor", method = RequestMethod.POST)
    public RespResult updateColor(@RequestBody ReqColorSizeVo reqColorSizeVo) {
        try {
            if (StringUtils.isEmpty(reqColorSizeVo.getId())
                    || StringUtils.isEmpty(reqColorSizeVo.getName())
                    || StringUtils.isEmpty(reqColorSizeVo.getUserId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = colorService.update(reqColorSizeVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.FAIL, "修改的颜色不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "修改颜色成功");
        } catch (Exception e) {
            logger.info("修改颜色异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "修改颜色异常");
        }
    }

    /**
     * 删除颜色
     *
     * @param reqColorSizeVo
     */
    @RequestMapping(value = "/deleteColor", method = RequestMethod.POST)
    public RespResult deleteColor(@RequestBody ReqColorSizeVo reqColorSizeVo) {
        try {
            if (StringUtils.isEmpty(reqColorSizeVo.getId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = colorService.delete(reqColorSizeVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.SUCCESS, "清空的数据不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "删除颜色成功");
        } catch (Exception e) {
            logger.info("删除颜色异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "删除颜色异常");
        }
    }

    /**
     * 新增尺寸
     *
     * @param reqColorSizeVo
     */
    @RequestMapping(value = "/addSize", method = RequestMethod.POST)
    public RespResult addSize(@RequestBody ReqColorSizeVo reqColorSizeVo) {
        try {
            if (StringUtils.isEmpty(reqColorSizeVo.getName())
                    || StringUtils.isEmpty(reqColorSizeVo.getUserId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long ColorId = sizeService.save(reqColorSizeVo);
            return new RespResult(RespCodeConstants.SUCCESS, ColorId);
        } catch (Exception e) {
            logger.info("新增尺寸异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "新增尺寸异常");
        }
    }

    /**
     * 修改尺寸
     *
     * @param reqColorSizeVo
     */
    @RequestMapping(value = "/updateSize", method = RequestMethod.POST)
    public RespResult updateSize(@RequestBody ReqColorSizeVo reqColorSizeVo) {
        try {
            if (StringUtils.isEmpty(reqColorSizeVo.getId())
                    || StringUtils.isEmpty(reqColorSizeVo.getName())
                    || StringUtils.isEmpty(reqColorSizeVo.getUserId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = sizeService.update(reqColorSizeVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.FAIL, "修改的尺寸不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "修改尺寸成功");
        } catch (Exception e) {
            logger.info("修改尺寸异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "修改尺寸异常");
        }
    }

    /**
     * 删除尺寸
     *
     * @param reqColorSizeVo
     */
    @RequestMapping(value = "/deleteSize", method = RequestMethod.POST)
    public RespResult deleteSize(@RequestBody ReqColorSizeVo reqColorSizeVo) {
        try {
            if (StringUtils.isEmpty(reqColorSizeVo.getId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = sizeService.delete(reqColorSizeVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.SUCCESS, "清空的数据不存在");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "删除尺寸成功");
        } catch (Exception e) {
            logger.info("删除尺寸异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "删除尺寸异常");
        }
    }
}
