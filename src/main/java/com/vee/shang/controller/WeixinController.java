package com.vee.shang.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vee.shang.entity.UserDomain;
import com.vee.shang.service.FollowService;
import com.vee.shang.service.UserService;
import com.vee.shang.util.HttpClientUtil;
import com.vee.shang.util.MD5Util;
import com.vee.shang.vo.RedisKeyConstants;
import com.vee.shang.vo.RespCodeConstants;
import com.vee.shang.vo.RespResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 微信相关服务
 *
 * @author cgd
 * @date 2017/12/07.
 */
@RestController
@RequestMapping("/weixin")
@EnableAutoConfiguration
public class WeixinController {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    private static String AppID = "wxc11d0078737dab8b";

    private static String AppSecret = "7acc81ef19f3d922bc6f26342c428590";

    private static String Login_AppID = "wx0b9a6028e0cc21b2";

    private static String Login_AppSecret = "b4ffeec4f44c1f422587effe8607d41d";

    private String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${follow.url}")
    private String followUrl;

    /**
     * 获取微信用户信息
     *
     * @param code
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public RedirectView getUserInfo(String code, Long state) {
        logger.info("code:{}", code);
        Long userId;
        //通过code换取网页授权access_token
        String getTokenUrl = String.format(tokenUrl, AppID, AppSecret, code);
        String accessTokenResult = HttpClientUtil.httpGetRequest(getTokenUrl);
        if (StringUtils.isEmpty(accessTokenResult) || accessTokenResult.contains("errcode")) {
            logger.info("获取微信accessToken异常:{}", accessTokenResult);
            return new RedirectView("http://www.baidu.com");
        } else {
            JSONObject accessTokenJson = JSON.parseObject(accessTokenResult);
            logger.info("通过code换取网页授权access_token:{}", accessTokenResult);
            String accessToken = accessTokenJson.getString("access_token");
            String openId = accessTokenJson.getString("openid");

            //通过openid获取用户信息
            String getUserInfoUrl = String.format(userInfoUrl, accessToken, openId);
            String userInfoResult = HttpClientUtil.httpGetRequest(getUserInfoUrl);
            try {
                userInfoResult = new String(userInfoResult.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(userInfoResult) || userInfoResult.contains("errcode")) {
                logger.info("获取微信用户信息异常:{}", accessTokenResult);
                return new RedirectView("http://www.baidu.com");
            } else {
                JSONObject userInfoJson = JSON.parseObject(userInfoResult);
                logger.info("通过accessToken获取用户信息:{}", userInfoResult);
                String unionId = userInfoJson.getString("unionid");
                //查询该unionid是否已注册
                userId = userService.getUserIdByUnionId(unionId);
                if (userId == null) {
                    //保存用户信息
                    UserDomain user = new UserDomain();
                    user.setNickname(userInfoJson.getString("nickname"));
                    user.setHeadImg(userInfoJson.getString("headimgurl"));
                    user.setUnionid(unionId);
                    user.setOpenid(userInfoJson.getString("openid"));
                    user.setChannel("wechat");
                    user.setCreateTime(new Date());
                    userId = userService.save(user);
                }
            }
        }
        //保存用户关注记录
        try {
            if(state!=userId && state!=1L && userId!=2L){
                followService.follow(state, userId);
            }
        } catch (Exception e) {
            logger.info("用户重复关注userId:{},followId:{}", state, userId);
        }

        return new RedirectView("http://www.veeshang.com/follow/index.html?userId="+state);
    }

    /**
     * 授权微信登录接口
     *
     * @param code
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public RespResult login(String code) {
        logger.info("code:{}", code);
        UserDomain user;
        //通过code换取网页授权access_token
        String getTokenUrl = String.format(tokenUrl, Login_AppID, Login_AppSecret, code);
        String accessTokenResult = HttpClientUtil.httpGetRequest(getTokenUrl);
        if (StringUtils.isEmpty(accessTokenResult) || accessTokenResult.contains("errcode")) {
            logger.info("获取微信accessToken异常:{}", accessTokenResult);
            return new RespResult(RespCodeConstants.FAIL, "获取微信accessToken异常");
        } else {
            JSONObject accessTokenJson = JSON.parseObject(accessTokenResult);
            logger.info("通过code换取网页授权access_token:{}", accessTokenResult);
            String accessToken = accessTokenJson.getString("access_token");
            String openid = accessTokenJson.getString("openid");

            //通过openid获取用户信息
            String getUserInfoUrl = String.format(userInfoUrl, accessToken, openid);
            String userInfoResult = HttpClientUtil.httpGetRequest(getUserInfoUrl);
            try {
                userInfoResult = new String(userInfoResult.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(userInfoResult) || userInfoResult.contains("errcode")) {
                logger.info("获取微信用户信息异常:{}", accessTokenResult);
                return new RespResult(RespCodeConstants.FAIL, "获取微信用户信息异常异常");
            } else {
                JSONObject userInfoJson = JSON.parseObject(userInfoResult);
                logger.info("通过accessToken获取用户信息:{}", userInfoResult);
                //查询该openid是否已注册
                String unionId = userInfoJson.getString("unionid");
                user = userService.getUserInfoByUnionid(unionId);
                if (user == null) {
                    //保存用户信息
                    user = new UserDomain();
                    user.setNickname(userInfoJson.getString("nickname"));
                    user.setHeadImg(userInfoJson.getString("headimgurl"));
                    user.setUnionid(unionId);
                    user.setOpenid(userInfoJson.getString("openid"));
                    user.setChannel("wechat");
                    user.setCreateTime(new Date());
                    userService.save(user);
                }
            }
        }
        if (user != null) {
            String token = MD5Util.getMD5String(user.getUserId() + "vee_shang");
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(RedisKeyConstants.USER_TOKEN + user.getUserId(), token, 30, TimeUnit.DAYS);
            user.setToken(token);
            return new RespResult(RespCodeConstants.SUCCESS, user);
        } else {
            return new RespResult(RespCodeConstants.FAIL, "微信授权登录异常");
        }
    }

    /**
     * 用户分享二维码跳转
     *
     * @param userId
     */
    @RequestMapping(value = "/shareUrl", method = RequestMethod.GET)
    public RedirectView getUserInfo(Long userId) {
        String longUrl = String.format(followUrl, userId);
        return new RedirectView(longUrl);
    }

}
