package com.vee.shang.service.impl;

import com.vee.shang.entity.UserDomain;
import com.vee.shang.mapper.*;
import com.vee.shang.service.UserService;
import com.vee.shang.util.MD5Util;
import com.vee.shang.util.QRCodeUtil;
import com.vee.shang.util.SDKSendTemplateSMS;
import com.vee.shang.util.UploadUtil;
import com.vee.shang.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cgd
 * @date 2017/11/20.
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private SizeMapper sizeMapper;

    @Autowired
    private StyleMapper styleMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${qiniu.url}")
    private String qiniuUrl;

    private String followUrl = "http://api.veeshang.com/api/weixin/shareUrl?userId=%s";

    @Override
    public Long save(UserDomain user) {
        userMapper.save(user);
        return user.getUserId();
    }

    @Override
    public void edit(ReqUserVo reqUserVo) {
        userMapper.edit(reqUserVo);
    }

    @Override
    public String getHeadImg(Long userId) {
        return userMapper.getHeadImg(userId);
    }

    @Override
    public Long save(ReqRegisterVo reqRegisterVo) {
        UserDomain user = new UserDomain();
        user.setNickname(reqRegisterVo.getNickname());
        user.setMobile(reqRegisterVo.getMobile());
        user.setPassword(reqRegisterVo.getPassword());
        user.setChannel(reqRegisterVo.getChannel());
        user.setCreateTime(new Date());
        userMapper.save(user);
        return user.getUserId();
    }

    @Override
    public UserDomain getUserInfo(String mobile, String password) {
        UserDomain userDomain = userMapper.getUserInfo(mobile, password);
        if (userDomain != null) {
            String token = MD5Util.getMD5String(userDomain.getUserId() + "vee_shang");
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(RedisKeyConstants.USER_TOKEN + userDomain.getUserId(), token, 30, TimeUnit.DAYS);
            userDomain.setToken(token);
        }
        return userDomain;
    }


    @Override
    public RespResult checkIsRegister(String mobile, Integer type) {
        Long userId = userMapper.selectUserIdByMobile(mobile);

        if (userId != null && type != null && type == 1) {
            return new RespResult(RespCodeConstants.FAIL, "该手机号已注册，请返回登录");
        } else if (userId == null && type != null && type==2) {
            return new RespResult(RespCodeConstants.FAIL, "该手机号未注册");
        } else {

            Random random = new Random();
            int code = random.nextInt(899999);
            code += 100000;

            //调用容联云发送短信验证码
            Map<String, Object> result = SDKSendTemplateSMS.send(mobile, code + "");
            if ("000000".equals(result.get("statusCode"))) {
                ValueOperations valueOperations = redisTemplate.opsForValue();
                valueOperations.set(RedisKeyConstants.USER_SMS_CODE + mobile, code + "", 3, TimeUnit.MINUTES);
            } else {
                return new RespResult(RespCodeConstants.FAIL, result.get("statusMsg"));
            }
            return new RespResult(RespCodeConstants.SUCCESS, "发送成功");
        }
    }

    @Override
    public Long changePwd(ReqRegisterVo reqRegisterVo) {
        Long count = userMapper.changePwd(reqRegisterVo);
        return count;
    }

    @Override
    public String createQRCode(Long userId) {
        String shareImg = "";
        //生成分享二维码
        String url = String.format(followUrl, userId);
        try {
            QRCodeUtil.encode(url, "e://tmp", userId.toString());
        } catch (Exception e) {
            logger.info("生成二维码异常：{}", e.getMessage());
        }
        String imgPath = "e://tmp/" + userId.toString() + ".jpg";

        //上传文件到七牛
        String fileKey = UploadUtil.upload(imgPath, this.getUpToken());
        if (!StringUtils.isEmpty(fileKey)) {
            shareImg = qiniuUrl + fileKey;
        }
        userMapper.updateShareImg(userId, shareImg);
        File file = new File(imgPath);
        file.delete();
        return shareImg;
    }

    @Override
    public String getUpToken() {
        //获取七牛上传token
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String upToken = (String) valueOperations.get(RedisKeyConstants.QINIU_UPLOAD_TOKEN);
        if (StringUtils.isEmpty(upToken)) {
            upToken = UploadUtil.getUploadToken();
            valueOperations.set(RedisKeyConstants.QINIU_UPLOAD_TOKEN, upToken, 1, TimeUnit.DAYS);
        }
        return upToken;
    }

    @Override
    public RespUserVo getUserInfoById(Long userId) {
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public void updateProductCount(Long userId, String operation) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("operation", operation);
        userMapper.updateProductCount(params);
    }

    @Override
    public void updateNewProductCount(Long userId, String operation,Long dayDiff) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("operation", operation);
        params.put("dayDiff", dayDiff);
        userMapper.updateProductCount(params);
    }

    @Transactional
    @Override
    public void bindMobile(ReqUserVo reqUserVo) {

        //查询是否已有存在的手机账号
        Long oldUserId = userMapper.selectUserIdByMobile(reqUserVo.getMobile());
        if(oldUserId!=null){
            //如果有，同步以前账号的数据到微信账号
            //1.同步商品信息
            productMapper.syncProduct(oldUserId,reqUserVo.getUserId());

            //2.同步分类信息
            styleMapper.syncStyle(oldUserId, reqUserVo.getUserId());

            //3.同步颜色信息
            colorMapper.syncColor(oldUserId, reqUserVo.getUserId());

            //4.同步尺寸信息
            sizeMapper.syncSize(oldUserId, reqUserVo.getUserId());

            //5.同步关注粉丝信息
            followMapper.syncFollow(oldUserId, reqUserVo.getUserId());
            followMapper.syncFans(oldUserId, reqUserVo.getUserId());

            //6.将老用户停用
            ReqUserVo oldUser = new ReqUserVo();
            oldUser.setUserId(oldUserId);
            oldUser.setStatus(-1);
            userMapper.edit(oldUser);
        }
        userMapper.edit(reqUserVo);
    }

    @Override
    public Long getUserIdByUnionId(String unionid) {
        return userMapper.getUserIdByUnionId(unionid);
    }

    @Override
    public UserDomain getUserInfoByUnionid(String unionid) {
        return userMapper.getUserInfoByUnionid(unionid);
    }

    @Override
    public void syncNewProductCount() {
        logger.info("-----------开始同步用户新品数量------------");
        //1.清空所有新品数量
        userMapper.cleanAllNewProductCount();
        //2.查询有7天内新品的用户数量
        List<Map<String,Object>> list =  productMapper.getAllNewProductCountList();
        for(Map<String,Object> map:list){
            userMapper.syncNewProductCount(map);
        }
        logger.info("-----------同步用户新品数量结束------------");
    }

}
