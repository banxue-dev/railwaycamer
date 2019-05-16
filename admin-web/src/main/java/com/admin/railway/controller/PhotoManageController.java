package com.admin.railway.controller;

import com.admin.common.annotation.Log;
import com.admin.common.config.Constant;
import com.admin.common.controller.BaseController;
import com.admin.common.utils.R;
import com.admin.common.utils.ShiroUtils;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.domain.vo.ExportExcelVo;
import com.admin.railway.service.*;
import com.admin.system.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: PhotoManageController
 * @Description: 照片管理
 * @Author: luojing
 * @Date: 2019/3/14 9:37
 */
@Controller
@RequestMapping("/railway/photomanage")
public class PhotoManageController extends BaseController {

    @Autowired
    private PhotoManageService photoManageService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PersonService personService;
    @Autowired
    private StationService stationService;

    @RequiresPermissions("railway:photomanage:list")
    @GetMapping()
    public String photoManage() {
        return "railway/photoManage/photoList";
    }

    @RequiresPermissions("railway:photomanage:list")
    @GetMapping("/list")
    @ResponseBody
    public R photoList(@RequestParam Map<String, Object> map) {
		Object stationId=map.get("startStationId");
		if(stationId==null ||com.admin.common.utils.StringUtils.isNullString(stationId.toString())) {
			UserDO user=getUser();
			if(user.getUserStationIds()!=null) {
				String ids="";
				int i=0;
				for(Long id:user.getUserStationIds()) {
					ids+=id;
					i++;
					if(i<user.getUserStationIds().size()) {
						ids+=",";
					}
				}
				map.put("startStationIds", ids);
			}
		}
        return photoManageService.selectPagePhoto(map);
    }

    @RequiresPermissions("railway:photomanage:viewPhoto")
    @GetMapping("/viewPhoto/{id}")
    public String viewPhoto(@PathVariable("id") Long id, Model model) {
        model.addAttribute(OrderDO.ID, id);
        return "railway/photoManage/viewPhoto";
    }

    @RequestMapping("/listPicture")
    @ResponseBody
    public List<PictureDO> listPicture(@RequestParam Map<String, Object> map) {
    	
        return photoManageService.listPicture(map);
    }
    @RequestMapping("/listPicturebyr")
    @ResponseBody
    public R listPicturebyr(@RequestParam Map<String, Object> map) {
    	try {
        	return R.okdata(photoManageService.listPicture(map));
    	}catch(Exception e) {
    		e.printStackTrace();
    		return R.error();
    	}
    }

    @Log("删除图片")
    @PostMapping("/deletePic")
    @ResponseBody
    public R deletePic(@RequestParam("ids[]") Long[] ids) {
        boolean fag = photoManageService.deletePic(ids);
        if (fag) {
            return R.ok(Constant.SuccessInfo.DELETE_SUCCESS.getMsg());
        } else {
            return R.error(Constant.ErrorInfo.DELETE_FAIL.getCode(), Constant.ErrorInfo.DELETE_FAIL.getMsg());
        }
    }

    @GetMapping("/exportPic/{id}")
    public void exportPic(@PathVariable("id") Long id,HttpServletResponse response) {
        photoManageService.exportPic(id,response);
    }

    @RequiresPermissions("railway:photomanage:edit")
    @GetMapping("/edit/{id}")
    public String editUI(@PathVariable("id") Long orderId, Model model) {
        OrderDO order = orderService.get(orderId);
        model.addAttribute("order", order);
        return "railway/photoManage/edit";
    }

    /**
     * 修改
     * @return
     */
    @Log("修改调度任务")
    @RequiresPermissions("railway:photomanage:edit")
    @PostMapping("/update")
    @ResponseBody
    public R update(OrderDO order) {
        UserDO user = ShiroUtils.getUser();
        // 根据前端传的 personIds 查找用户名
        String personIds = order.getPersonIds();
        if(StringUtils.isNoneBlank(personIds)){
            List<Long> ids = Arrays.stream(personIds.split(","))
                    .map(s -> Long.parseLong(s.trim()))
                    .collect(Collectors.toList());
            List<PersonDO> personList = personService.getByIds(ids);
            String personNames = personList.stream().map(PersonDO::getName).collect(Collectors.joining(","));
            order.setPersonNames(personNames);
        }
        // 设置stationName
        StationDO station = null;
        if (order.getStartStationId() != null) {
            station = stationService.get(order.getStartStationId());
            order.setStartStationName(station.getName());
        }
        if (order.getEndStationId() != null) {
            station = stationService.get(order.getEndStationId());
            order.setEndStationName(station.getName());
        }
        order.setModifyTime(new Date());
        order.setModifyUser(user.getName());
        order.setUploadWay(Constant.Number.ZERO.getCode());
        orderService.update(order);
        return R.ok();
    }

    @GetMapping("/exportExcel")
    public void exportExcel(@Validated ExportExcelVo vo, HttpServletResponse response) {
        photoManageService.exportExcel(vo,getUser(),response);
    }
}
