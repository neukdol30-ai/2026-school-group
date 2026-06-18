package com.siyan1234.group_project.report.service;

import com.siyan1234.group_project.report.dao.ReportDao;
import com.siyan1234.group_project.report.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportDao reportDao;

    public int insertReport(ReportDto reportDto) {

        if (reportDto == null
                || reportDto.getReporterNo() == null
                || reportDto.getTargetType() == null
                || reportDto.getTargetNo() == null
                || reportDto.getReason() == null) {
            return 0;
        }

        int duplicateCount =
                reportDao.countDuplicateReport(reportDto);

        if (duplicateCount > 0) {
            return -1;
        }

        return reportDao.insertReport(reportDto);
    }

    public List<ReportDto> searchAdminReportList(ReportDto reportDto) {

        if (reportDto.getPage() == null || reportDto.getPage() < 1) {
            reportDto.setPage(1);
        }

        if (reportDto.getSize() == null || reportDto.getSize() < 1) {
            reportDto.setSize(10);
        }

        int offset =
                (reportDto.getPage() - 1) * reportDto.getSize();

        reportDto.setOffset(offset);

        return reportDao.searchAdminReportList(reportDto);
    }

    public int countAdminReportList(ReportDto reportDto) {
        return reportDao.countAdminReportList(reportDto);
    }

    public ReportDto findAdminReportByNo(Integer no) {
        return reportDao.findAdminReportByNo(no);
    }

    public int updateReportDone(Integer no) {
        return reportDao.updateReportDone(no);
    }
}