package com.vee.shang.service.impl;

import com.vee.shang.entity.Style;
import com.vee.shang.entity.StyleChild;
import com.vee.shang.mapper.StyleMapper;
import com.vee.shang.service.StyleService;
import com.vee.shang.vo.ReqStyleChildVo;
import com.vee.shang.vo.ReqStyleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author cgd
 * @date 2017/11/20.
 */
@Service
public class StyleServiceImpl implements StyleService {

    @Autowired
    private StyleMapper styleMapper;

    @Override
    @CacheEvict(value = "style", key = "'style:'+#reqStyleVo.userId")
    public Long save(ReqStyleVo reqStyleVo) {
        Style style = new Style();
        style.setUserId(reqStyleVo.getUserId());
        style.setName(reqStyleVo.getName());
        style.setImg(reqStyleVo.getImg());
        style.setCreateTime(new Date());
        styleMapper.save(style);
        return style.getStyleId();
    }

    @Override
    @CacheEvict(value = "style", key = "'style:'+#reqStyleVo.userId")
    public Long delete(ReqStyleVo reqStyleVo) {
        Long count = styleMapper.checkHavaProduct(reqStyleVo.getStyleId());
        if (count < 1) {
            return styleMapper.delete(reqStyleVo.getStyleId());
        }
        return -1L;
    }

    @Override
    @CacheEvict(value = "style", key = "'style:'+#reqStyleVo.userId")
    public Long update(ReqStyleVo reqStyleVo) {
        return styleMapper.update(reqStyleVo);
    }

    @Override
    @Cacheable(value = "style", key = "'style:'+#userId")
    public List<Style> getList(Long userId) {
        List<Style> list = styleMapper.getList(userId);
        for(Style style:list){
            style.setChildStyleCount(styleMapper.getChildStyleCount(style.getStyleId()));
        }
        return list;
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = "styleChild", key = "'styleChild:'+#reqStyleChildVo.styleId"),
            @CacheEvict(value = "style", key = "'style:'+#reqStyleChildVo.userId")
    })
    public Long saveChild(ReqStyleChildVo reqStyleChildVo) {
        StyleChild styleChild = new StyleChild();
        styleChild.setStyleId(reqStyleChildVo.getStyleId());
        styleChild.setName(reqStyleChildVo.getName());
        styleChild.setImg(reqStyleChildVo.getImg());
        styleChild.setCreateTime(new Date());
        styleMapper.saveChild(styleChild);
        return styleChild.getStyleChildId();
    }

    @Override
    @CacheEvict(value = "styleChild", key = "'styleChild:'+#reqStyleChildVo.styleId")
    public Long updateChild(ReqStyleChildVo reqStyleChildVo) {
        return styleMapper.updateChild(reqStyleChildVo);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "styleChild", key = "'styleChild:'+#reqStyleChildVo.styleId"),
            @CacheEvict(value = "style", key = "'style:'+#reqStyleChildVo.userId")
    })
    public Long deleteChild(ReqStyleChildVo reqStyleChildVo) {
        Long count = styleMapper.checkHavaProductChild(reqStyleChildVo.getStyleChildId());
        if (count < 1) {
            return styleMapper.deleteChild(reqStyleChildVo.getStyleChildId());
        }
        return -1L;
    }

    @Override
    @Cacheable(value = "styleChild", key = "'styleChild:'+#styleId")
    public List<StyleChild> getChildList(Long styleId) {
        return styleMapper.getChildList(styleId);
    }


}
