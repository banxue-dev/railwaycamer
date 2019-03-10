package com.admin.railway.service;

import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.vo.UploadImgVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName: ApiService
 * @Description: AppServie
 * @Author: luojing
 * @Date: 2019/3/9 9:54
 */
public interface ApiService {

    boolean uploadImg(UploadImgVo vo);

}
