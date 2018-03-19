package com.vee.shang.service;

import com.vee.shang.entity.Style;
import com.vee.shang.entity.StyleChild;
import com.vee.shang.vo.ReqStyleChildVo;
import com.vee.shang.vo.ReqStyleVo;

import java.util.List;

/**
 * @author cgd
 * @date 2017/12/2.
 */
public interface StyleService {

    Long save(ReqStyleVo reqStyleVo);

    Long update(ReqStyleVo reqStyleVo);

    Long delete(ReqStyleVo reqStyleVo);

    List<Style> getList(Long userId);

    Long saveChild(ReqStyleChildVo reqStyleChildVo);

    Long updateChild(ReqStyleChildVo reqStyleChildVo);

    Long deleteChild(ReqStyleChildVo reqStyleChildVo);

    List<StyleChild> getChildList(Long styleId);


}
