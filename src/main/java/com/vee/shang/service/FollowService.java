package com.vee.shang.service;

import com.vee.shang.vo.ReqFollowVo;
import com.vee.shang.vo.RespFollowDetailVo;
import com.vee.shang.vo.RespFollowVo;

import java.util.List;
import java.util.Map;

/**
 * @author cgd
 * @date 2017/12/2.
 */
public interface FollowService {

    void follow(Long userId,Long followId);

    Map<String,Object> getFollowList(Long userId,Integer type);

    Long update(ReqFollowVo reqFollowVo);

    List<RespFollowVo> searchList(Long userId, Integer type,String keyWord);

    String getFollowNickName(Long userId, Long userId1);

    RespFollowDetailVo getFollowDetail(Long userId, Long loginUserId,Integer type);
}
