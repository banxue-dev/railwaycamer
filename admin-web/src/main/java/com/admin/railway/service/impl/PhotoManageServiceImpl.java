package com.admin.railway.service.impl;

import com.admin.common.config.Constant;
import com.admin.common.exception.BDException;
import com.admin.common.utils.Constants;
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.common.utils.StringUtils;
import com.admin.railway.dao.PhotoManageDao;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.domain.vo.ExportExcelVo;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PhotoManageService;
import com.admin.railway.service.PictureService;
import com.admin.system.domain.UserDO;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName: PhotoManageServiceImpl
 * @Description: 照片管理
 * @Author: luojing
 * @Date: 2019/3/14 10:25
 */
@Service
public class PhotoManageServiceImpl implements PhotoManageService {

    @Autowired
    private PhotoManageDao photoManageDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PictureService pictureService;

    @Override
    public R selectPagePhoto(Map<String, Object> map) {
        QueryParam query = new QueryParam(map);
        // 查询未删除
        query.put(OrderDO.DEL_STATE, Constants.NO);
        Map<String, Object> result = new HashMap<>();
        Integer count = orderService.count(query);
        if (count > 0) {
            List<Map<String, Object>> resultList = photoManageDao.list(query);
            result.put(Constant.DATA, resultList);
            result.put(Constant.COUNT, count);
        }
        return R.ok(result);
    }

    @Override
    public boolean deletePic(Long[] ids) {
        return pictureService.batchRemove(ids);
    }

    private List<PictureDO> listPic(Long id) {
        List<PictureDO> list = new ArrayList<>();
        //查询是否存在续拍
        OrderDO order = orderService.get(id);
        if (order != null) {
            //不为0时续拍
            if (order.getContinueShot().intValue() != Constant.Number.ZERO.getCode()) {
                Map<String, Object> map = new HashMap<>();
                map.put(PictureDO.ORDER_ID, order.getContinueShot());
                list.addAll(pictureService.list(map));
                this.listPic(order.getContinueShot());
            }
        }
        return list;
    }

    @Override
    public void exportPic(Long id, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put(PictureDO.ORDER_ID, id);
            List<PictureDO> listPic = pictureService.list(map);
            listPic.addAll(this.listPic(id));
            if (!listPic.isEmpty()) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ZipOutputStream zip = new ZipOutputStream(outputStream);
                for (PictureDO pic : listPic) {
                    File file = new File(pic.getUrl());
                    FileInputStream zipSource = new FileInputStream(file);
                    byte[] bytes = new byte[1024 * 20];
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zip.putNextEntry(zipEntry);
                    BufferedInputStream bufferStream = new BufferedInputStream(zipSource, 1024 * 20);
                    int read = 0;
                    while ((read = bufferStream.read(bytes, 0, 1024 * 20)) != -1) {
                        zip.write(bytes, 0, read);
                    }
                }
                String fileName = listPic.get(0).getTrainNo() + ".zip";
                IOUtils.closeQuietly(zip);
                byte[] bytes = outputStream.toByteArray();
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                response.addHeader("Content-Length", "" + bytes.length);
                response.setContentType("application/octet-stream; charset=UTF-8");
                IOUtils.write(bytes, response.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PictureDO> listPicture(Map<String, Object> map) {
        Long id = Long.valueOf(map.get("orderId").toString());
        List<PictureDO> listPic = pictureService.list(map);
        listPic.addAll(this.listPic(id));
        return listPic;
    }

    @Override
    public void exportExcel(ExportExcelVo vo, UserDO user, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        map.put(OrderDO.DEL_STATE, Constants.NO);
        if (StringUtils.isNotEmpty(vo.getStartStationId())) {
            if (user.getUserStationIds() != null) {
                String ids = "";
                int i = 0;
                for (Long id : user.getUserStationIds()) {
                    ids += id;
                    i++;
                    if (i < user.getUserStationIds().size()) {
                        ids += ",";
                    }
                }
                map.put("startStationIds", ids);
            }
            map.put("startStationId", vo.getStartStationId());
        }
        if (StringUtils.isNotEmpty(vo.getEndStationId())){
            map.put("endStationId", vo.getEndStationId());
        }if (StringUtils.isNotEmpty(vo.getBeginTime())){
            map.put("beginTime", vo.getBeginTime());
        }if (StringUtils.isNotEmpty(vo.getEndTime())){
            map.put("endTime", vo.getEndTime());
        }if (StringUtils.isNotEmpty(vo.getTrainNo())){
            map.put("trainNo", vo.getTrainNo());
        }

        List<Map<String, Object>> list = photoManageDao.list(map);
        if (!list.isEmpty()) {
            try {
                //表头
                String[] headers = {"序号", "车号", "日期", "发站", "到站", "货人", "收货人", "品名", "车型", "方案编号", "装车线路", "照片数量"};
                //声明工作簿
                HSSFWorkbook workbook = new HSSFWorkbook();
                //生成表格
                HSSFSheet sheet = workbook.createSheet("车号信息");
                sheet.setDefaultColumnWidth(18);

                //设置为居中加粗
                HSSFCellStyle style = workbook.createCellStyle();
                HSSFFont font = workbook.createFont();
                font.setBold(true);
                font.setFontHeightInPoints((short) 12);
                style.setFont(font);
                int rowNum = 0;
                //设置表头
                HSSFRow row = sheet.createRow(rowNum);
                for (int i = 0; i < headers.length; i++) {
                    HSSFCell cell1 = row.createCell(i);
                    cell1.setCellValue(headers[i]);
                    cell1.setCellStyle(style);
                }
                HSSFCellStyle style1 = workbook.createCellStyle();
                HSSFFont font1 = workbook.createFont();
                font1.setFontHeightInPoints((short) 14);
                style1.setFont(font1);
                for (int i = 0; i < list.size(); i++) {
                    rowNum++;
                    row = sheet.createRow(rowNum);
                    row.createCell(0).setCellValue(i + 1);
                    Object trainNo = list.get(i).get("trainNo");
                    if (trainNo != null) {
                        row.createCell(1).setCellValue(trainNo.toString());
                    }
                    Object createTime = list.get(i).get("createTime");
                    if (createTime != null) {
                        row.createCell(2).setCellValue(createTime.toString());
                    }
                    Object startStationName = list.get(i).get("startStationName");
                    if (startStationName != null) {
                        row.createCell(3).setCellValue(String.valueOf(startStationName.toString()));
                    }
                    Object endStationName = list.get(i).get("endStationName");
                    if (endStationName != null) {
                        row.createCell(4).setCellValue(endStationName.toString());
                    }
                    Object consignor = list.get(i).get("consignor");
                    if (consignor != null) {
                        row.createCell(5).setCellValue(consignor.toString());
                    }
                    Object consignee = list.get(i).get("endStationName");
                    if (consignee != null) {
                        row.createCell(6).setCellValue(consignee.toString());
                    }
                    Object productName = list.get(i).get("productName");
                    if (productName != null) {
                        row.createCell(7).setCellValue(productName.toString());
                    }
                    Object trainType = list.get(i).get("trainType");
                    if (trainType != null) {
                        row.createCell(8).setCellValue(trainType.toString());
                    }
                    Object projectNo = list.get(i).get("projectNo");
                    if (projectNo != null) {
                        row.createCell(9).setCellValue(projectNo.toString());
                    }
                    Object loadingLine = list.get(i).get("loadingLine");
                    if (loadingLine != null) {
                        row.createCell(10).setCellValue(loadingLine.toString());
                    }
                    Object photoNumber = list.get(i).get("photoNumber");
                    if (photoNumber != null) {
                        row.createCell(11).setCellValue(photoNumber.toString());
                    }
                    row.setRowStyle(style1);
                }
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                // 下载文件的默认名称
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("车号信息.xls", "utf-8"));
                response.flushBuffer();
                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new BDException("暂无数据");
        }
    }
}
