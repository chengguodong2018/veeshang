package com.vee.shang.service.impl;

import com.vee.shang.entity.Color;
import com.vee.shang.mapper.ColorMapper;
import com.vee.shang.service.ColorService;
import com.vee.shang.vo.ReqColorSizeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author cgd
 * @date 2017/11/20.
 */
@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorMapper colorMapper;

    @Override
    @CacheEvict(value = "color", key = "'color:'+#reqColorSizeVo.userId")
    public Long save(ReqColorSizeVo reqColorSizeVo) {
        Color color = new Color();
        color.setUserId(reqColorSizeVo.getUserId());
        color.setName(reqColorSizeVo.getName());
        color.setCreateTime(new Date());
        colorMapper.save(color);
        return color.getId();
    }

    @Override
    @Cacheable(value = "color", key = "'color:'+#userId")
    public List<Color> getList(Long userId) {
        System.out.println("查询数据库");
        return colorMapper.getList(userId);
    }

    @Override
    @CacheEvict(value = "color", key = "'color:'+#reqColorSizeVo.userId")
    public Long update(ReqColorSizeVo reqColorSizeVo) {
        return colorMapper.update(reqColorSizeVo);
    }

    @Override
    @CacheEvict(value = "color", key = "'color:'+#reqColorSizeVo.userId")
    public Long delete(ReqColorSizeVo reqColorSizeVo) {
        return colorMapper.delete(reqColorSizeVo.getId());
    }


}
