package com.vee.shang.service;

import com.vee.shang.entity.Color;
import com.vee.shang.vo.ReqColorSizeVo;

import java.util.List;

/**
 * @author cgd
 * @date 2017/12/2.
 */
public interface ColorService {

    Long save(ReqColorSizeVo reqColorSizeVo);

    List<Color> getList(Long userId);

    Long update(ReqColorSizeVo reqColorSizeVo);

    Long delete(ReqColorSizeVo reqColorSizeVo);
}
