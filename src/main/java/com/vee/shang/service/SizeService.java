package com.vee.shang.service;

import com.vee.shang.entity.Size;
import com.vee.shang.vo.ReqColorSizeVo;

import java.util.List;

/**
 * @author cgd
 * @date 2017/12/2.
 */
public interface SizeService {

    Long save(ReqColorSizeVo reqColorSizeVo);

    List<Size> getList(Long userId);

    Long update(ReqColorSizeVo reqColorSizeVo);

    Long delete(ReqColorSizeVo reqColorSizeVo);
}
