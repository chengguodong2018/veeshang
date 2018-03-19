package com.vee.shang.controller;

import com.vee.shang.interceptor.TokenAnnotation;
import com.vee.shang.service.FollowService;
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
import java.util.Map;

/**
 * 关注
 *
 * @author cgd
 * @date 2017/12/10.
 */
@RestController
@RequestMapping("/follow")
@EnableAutoConfiguration
public class FollowControllerV {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private FollowService followService;

    /**
     * 我的关注列表
     *
     * @param userId
     * @param type   1:我的关注 2:我的粉丝
     */
    @TokenAnnotation
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RespResult getFollowList(Long userId, Integer type) {
        try {
            Map<String, Object> map = followService.getFollowList(userId, type);
            return new RespResult(RespCodeConstants.SUCCESS, map);
        } catch (Exception e) {
            logger.info("获取关注列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取关注列表异常");
        }
    }

    /**
     * 搜索我的关注 我的粉丝
     *
     * @param userId
     * @param type   1:我的关注 2:我的粉丝
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public RespResult search(Long userId, Integer type, String keyWord) {
        try {
            List<RespFollowVo> result = followService.searchList(userId, type, keyWord);
            return new RespResult(RespCodeConstants.SUCCESS, result);
        } catch (Exception e) {
            logger.info("获取关注列表异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取关注列表异常");
        }
    }


    /**
     * 关注明细
     *
     * @param userId
     * @param beFollowUserId
     * @param type 1.查询的关注信息 2.查询粉丝信息
     */
//    @TokenAnnotation
//    @RequestMapping(value = "/detail", method = RequestMethod.GET)
//    public RespResult getFollowDetail(Long userId, Long beFollowUserId,Integer type) {
//        try {
//            RespFollowDetailVo respFollowVo = followService.getFollowDetail(userId, beFollowUserId,type);
//            return new RespResult(RespCodeConstants.SUCCESS, respFollowVo);
//        } catch (Exception e) {
//            logger.info("获取关注明细异常：{}", e.getMessage());
//            return new RespResult(RespCodeConstants.FAIL, "获取关注明细异常");
//        }
//    }

    /**
     * 编辑关注信息
     *
     * @param reqFollowVo
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RespResult updateSize(@RequestBody ReqFollowVo reqFollowVo) {
        try {
            if (StringUtils.isEmpty(reqFollowVo.getId())) {
                return new RespResult(RespCodeConstants.FAIL, "缺少参数");
            }
            Long count = followService.update(reqFollowVo);
            if (count < 1) {
                return new RespResult(RespCodeConstants.FAIL, "修改关注用户不存在");
            }
            if (reqFollowVo.getTakeOff() == 1) {
                return new RespResult(RespCodeConstants.SUCCESS, "取关成功");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "修改关注用户成功");
        } catch (Exception e) {
            logger.info("修改关注用户异常:{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "修改关注用户异常");
        }
    }

}
