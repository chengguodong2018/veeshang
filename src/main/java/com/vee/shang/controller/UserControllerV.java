package com.vee.shang.controller;

import com.vee.shang.service.FollowService;
import com.vee.shang.service.UserService;
import com.vee.shang.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 *
 * @author cgd
 * @date 2017/11/20.
 */
@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserControllerV {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 生成二维码接口
     *
     * @param userId
     */
    @RequestMapping(value = "/getQRCode", method = RequestMethod.GET)
    public RespResult getQRCode(Long userId) {
        try {
            Object qrCodeUrl = userService.createQRCode(userId);
            return new RespResult(RespCodeConstants.SUCCESS, qrCodeUrl);
        } catch (Exception e) {
            logger.info("获取二维码异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取二维码异常");
        }
    }

    /**
     * 编辑店铺信息
     *
     * @param reqUserVo
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RespResult edit(@RequestBody ReqUserVo reqUserVo) {
        try {
            userService.edit(reqUserVo);
            return new RespResult(RespCodeConstants.SUCCESS, "编辑店铺信息成功");
        } catch (Exception e) {
            logger.info("编辑店铺信息异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "编辑店铺信息异常");
        }
    }

    /**
     * 获取上传图片token
     */
    @RequestMapping(value = "/getUpToken", method = RequestMethod.GET)
    public RespResult getUpToken() {
        try {
            Object upToken = userService.getUpToken();
            return new RespResult(RespCodeConstants.SUCCESS, upToken);
        } catch (Exception e) {
            logger.info("获取上传图片token异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取上传图片token异常");
        }
    }

    /**
     * 店铺主页用户信息接口
     *
     * @param userId 店铺用户id
     * @param loginUserId 当前登录用户id
     * @paramtype 1.查询的关注信息 2.查询粉丝信息
     */
    @RequestMapping(value = "/getShopInfo", method = RequestMethod.GET)
    public RespResult getUserInfo(@RequestParam(name = "userId") Long userId,
                                  @RequestParam(name = "loginUserId", required = false) Long loginUserId,
                                  @RequestParam(name = "type", required = false) Integer type) {
        try {
            RespUserVo respUserVo = userService.getUserInfoById(userId);
            if (respUserVo != null) {
                if(loginUserId!=null && loginUserId!=userId){
                    RespFollowDetailVo respFollowVo = followService.getFollowDetail(userId, loginUserId,type);
                    if(respFollowVo!=null){
                        respUserVo.setFollowDetail(respFollowVo);
                    }else{
                        respUserVo.setFollowDetail("");
                    }
                }else{
                    respUserVo.setFollowDetail("");
                }
                return new RespResult(RespCodeConstants.SUCCESS, respUserVo);
            }
            return new RespResult(RespCodeConstants.FAIL, "用户不存在");
        } catch (Exception e) {
            logger.info("获取店铺信息异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取店铺信息异常");
        }
    }


    /**
     * 微信用户登录绑定手机号
     *
     * @param reqUserVo
     */
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public RespResult bindMobile(@RequestBody ReqUserVo reqUserVo) {
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String smsCode = String.valueOf(valueOperations.get(RedisKeyConstants.USER_SMS_CODE + reqUserVo.getMobile()));
            if (reqUserVo.getSmsCode() != null && reqUserVo.getSmsCode().equals(smsCode)) {
                userService.bindMobile(reqUserVo);
            } else {
                return new RespResult(RespCodeConstants.FAIL, "验证码不正确");
            }
            return new RespResult(RespCodeConstants.SUCCESS, "绑定手机号成功");
        } catch (Exception e) {
            logger.info("绑定手机号异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "绑定手机号异常");
        }
    }

    /**
     * 定时同步用户新品数量
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    @RequestMapping(value = "/sync/newProductCount")
    public void syncNewProductCount() {
        try {
            userService.syncNewProductCount();
        } catch (Exception e) {
            logger.info("同步用户新品数量异常：{}", e.getMessage());
        }
    }
}
