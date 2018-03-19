package com.vee.shang.service.impl;

import com.vee.shang.mapper.FollowMapper;
import com.vee.shang.service.FollowService;
import com.vee.shang.util.Sort;
import com.vee.shang.vo.ReqFollowVo;
import com.vee.shang.vo.RespFollowDetailVo;
import com.vee.shang.vo.RespFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author cgd
 * @date 2017/11/20.
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "follow", key = "'follow:'+#userId+':2'"),
//            @CacheEvict(value = "follow", key = "'follow:'+#followId+':1'")
//    })
    public void follow(Long userId, Long followId) {
        followMapper.save(userId, followId);
    }

    @Override
    //@Cacheable(value = "follow", key = "'follow:'+#userId+':'+#type")
    public Map<String, Object> getFollowList(Long userId, Integer type) {
        List<RespFollowVo> followList;
        if (type == 2) {
            followList = followMapper.getFansList(userId);
        } else {
            followList = followMapper.getFollowList(userId);
        }
        Sort sort = new Sort();
        Map map = sort.sort(followList);
        return map;
    }

    @Override
    //@CacheEvict(value = "follow", key = "'follow:'+#reqFollowVo.userId+':*'")
    public Long update(ReqFollowVo reqFollowVo) {
        if (reqFollowVo.getTakeOff() == 1) {
            return followMapper.delete(reqFollowVo.getId());
        }
        return followMapper.update(reqFollowVo);
    }

    @Override
    public List<RespFollowVo> searchList(Long userId, Integer type, String keyWord) {
        List<RespFollowVo> followList;
        if (type == 2) {
            followList = followMapper.searchFans(userId, keyWord);
        } else {
            followList = followMapper.searchFollow(userId, keyWord);
        }
        return followList;
    }

    @Override
    public String getFollowNickName(Long followId, Long userId) {
        return followMapper.getFollowNickName(followId,userId);
    }

    @Override
    public RespFollowDetailVo getFollowDetail(Long userId, Long loginUserId,Integer type) {
        if(type==1){
            //获取关注用户备注信息
            return followMapper.getFollowDetail(userId,loginUserId);
        }else{
            //获取粉丝用户备注信息
            return followMapper.getFansDetail(userId,loginUserId);

        }
    }

}
