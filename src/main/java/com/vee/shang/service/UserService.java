package com.vee.shang.service;

import com.vee.shang.entity.UserDomain;
import com.vee.shang.vo.ReqRegisterVo;
import com.vee.shang.vo.ReqUserVo;
import com.vee.shang.vo.RespResult;
import com.vee.shang.vo.RespUserVo;

/**
 * @author admin
 * @date 2017/4/20.
 */
public interface UserService {

    Long save(ReqRegisterVo reqRegisterVo);

    RespResult checkIsRegister(String mobile,Integer type);

    UserDomain getUserInfo(String mobile,String password);

    Long changePwd(ReqRegisterVo reqRegisterVo);

    String createQRCode(Long userId) throws Exception;

    Long save(UserDomain user);


    void edit(ReqUserVo reqUserVo);

    String getHeadImg(Long userId);

    String getUpToken();

    RespUserVo getUserInfoById(Long userId);

    void updateProductCount(Long userId, String operation);

    void bindMobile(ReqUserVo reqUserVo);

    Long getUserIdByUnionId(String unionid);

    UserDomain getUserInfoByUnionid(String unionid);

    void syncNewProductCount();

    void updateNewProductCount(Long userId, String delete, Long dayDiff);
}
