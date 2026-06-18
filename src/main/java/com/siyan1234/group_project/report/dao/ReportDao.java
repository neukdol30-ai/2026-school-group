package com.siyan1234.group_project.report.dao;

import com.siyan1234.group_project.report.dto.ReportDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportDao {

    int insertReport(ReportDto reportDto);

    int countDuplicateReport(ReportDto reportDto);

    List<ReportDto> searchAdminReportList(ReportDto reportDto);

    int countAdminReportList(ReportDto reportDto);

    ReportDto findAdminReportByNo(Integer no);

    int updateReportDone(Integer no);
}