package com.vee.shang.controller;

import com.vee.shang.entity.Product;
import com.vee.shang.entity.UserDomain;
import com.vee.shang.service.ProductService;
import com.vee.shang.service.UserService;
import com.vee.shang.vo.RedisKeyConstants;
import com.vee.shang.vo.ReqRegisterVo;
import com.vee.shang.vo.RespCodeConstants;
import com.vee.shang.vo.RespResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录注册
 *
 * @author cgd
 * @date 2017/11/20.
 */
@RestController
@EnableAutoConfiguration
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(Logger.class);
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    /**
     * 用户注册接口
     *
     * @param reqRegisterVo
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RespResult register(@RequestBody ReqRegisterVo reqRegisterVo) {
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String smsCode = String.valueOf(valueOperations.get(RedisKeyConstants.USER_SMS_CODE + reqRegisterVo.getMobile()));
            if (reqRegisterVo.getSmsCode() != null && reqRegisterVo.getSmsCode().equals(smsCode)) {
                Long userId = userService.save(reqRegisterVo);
                return new RespResult(RespCodeConstants.SUCCESS, userId);
            } else {
                return new RespResult(RespCodeConstants.FAIL, "验证码不正确");
            }
        } catch (Exception e) {
            logger.info("用户注册异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "用户注册异常");
        }
    }

    /**
     * 用户登录接口
     *
     * @param mobile
     * @param password
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public RespResult login(String mobile, String password) {
        try {
            UserDomain userDomain = userService.getUserInfo(mobile, password);
            if (userDomain != null) {
                return new RespResult(RespCodeConstants.SUCCESS, userDomain);
            }
            return new RespResult(RespCodeConstants.FAIL, "用户名或密码错误");
        } catch (Exception e) {
            logger.info("用户登录异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "用户登录异常");
        }
    }

    /**
     * 发送验证码接口
     *
     * @param mobile
     * @param type:  1:注册  2:忘记密码
     */
    @RequestMapping(value = "/sendCode", method = RequestMethod.GET)
    public RespResult sendCode(String mobile, Integer type) {
        try {
            RespResult respResult = userService.checkIsRegister(mobile, type);
            return respResult;
        } catch (Exception e) {
            logger.info("发送验证码异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "发送验证码异常");
        }
    }

    /**
     * 用户修改密码
     *
     * @param reqRegisterVo
     */
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public RespResult changePwd(@RequestBody ReqRegisterVo reqRegisterVo) {
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String smsCode = String.valueOf(valueOperations.get(RedisKeyConstants.USER_SMS_CODE + reqRegisterVo.getMobile()));
            if (reqRegisterVo.getSmsCode() != null && reqRegisterVo.getSmsCode().equals(smsCode)) {
                userService.changePwd(reqRegisterVo);
                return new RespResult(RespCodeConstants.SUCCESS, "修改成功");
            } else {
                return new RespResult(RespCodeConstants.FAIL, "验证码不正确");
            }
        } catch (Exception e) {
            logger.info("用户修改密码异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "用户修改密码异常");
        }
    }

    /**
     * 用户关注成功后返回被关注者头像
     *
     * @param userId
     */
    @RequestMapping(value = "/getHeadImg", method = RequestMethod.GET)
    public RespResult login(Long userId) {
        try {
            Object headImg = userService.getHeadImg(userId);
            if (headImg != null) {
                return new RespResult(RespCodeConstants.SUCCESS, headImg);
            }
            return new RespResult(RespCodeConstants.SUCCESS);
        } catch (Exception e) {
            logger.info("获取头像异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取头像异常");
        }
    }

    /**
     * 商品详情
     *
     * @param productId
     */
    @RequestMapping(value = "/getProductInfo", method = RequestMethod.GET)
    public RespResult detail(Long productId) {
        try {
            Product product = productService.getById(productId);
            return new RespResult(RespCodeConstants.SUCCESS, product);
        } catch (Exception e) {
            logger.info("获取商品详情异常：{}", e.getMessage());
            return new RespResult(RespCodeConstants.FAIL, "获取商品详情异常");
        }
    }


}
