package com.admin.railway.domain.vo;

import lombok.Data;

@Data
public class ExportExcelVo {
    private String startStationId;
    private String endStationId;
    private String beginTime;
    private String endTime;
    private String trainNo;
}
