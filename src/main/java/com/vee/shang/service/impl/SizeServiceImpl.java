package com.vee.shang.service.impl;

import com.vee.shang.entity.Size;
import com.vee.shang.mapper.SizeMapper;
import com.vee.shang.service.SizeService;
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
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeMapper sizeMapper;

    @Override
    @CacheEvict(value = "size", key = "'size:'+#reqColorSizeVo.userId")
    public Long save(ReqColorSizeVo reqColorSizeVo) {
        Size size = new Size();
        size.setUserId(reqColorSizeVo.getUserId());
        size.setName(reqColorSizeVo.getName());
        size.setCreateTime(new Date());
        sizeMapper.save(size);
        return size.getId();
    }

    @Override
    @Cacheable(value = "size", key = "'size:'+#userId")
    public List<Size> getList(Long userId) {
        System.out.println("查询数据库size列表");
        return sizeMapper.getList(userId);
    }

    @Override
    @CacheEvict(value = "size", key = "'size:'+#reqColorSizeVo.userId")
    public Long update(ReqColorSizeVo reqColorSizeVo) {
        return sizeMapper.update(reqColorSizeVo);
    }

    @Override
    @CacheEvict(value = "size", key = "'size:'+#reqColorSizeVo.userId")
    public Long delete(ReqColorSizeVo reqColorSizeVo) {
        return sizeMapper.delete(reqColorSizeVo.getId());
    }

}
