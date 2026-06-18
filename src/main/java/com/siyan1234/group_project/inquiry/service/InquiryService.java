package com.siyan1234.group_project.inquiry.service;

import com.siyan1234.group_project.inquiry.dao.InquiryDao;
import com.siyan1234.group_project.inquiry.dto.InquiryAnswerDto;
import com.siyan1234.group_project.inquiry.dto.InquiryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryDao inquiryDao;

    public int insertInquiry(InquiryDto inquiryDto) {
        return inquiryDao.insertInquiry(inquiryDto);
    }

    public List<InquiryDto> findMyInquiryList(Integer memberNo) {
        return inquiryDao.findMyInquiryList(memberNo);
    }

    public InquiryDto findByNo(Integer no) {
        return inquiryDao.findByNo(no);
    }

    public List<InquiryDto> findAllInquiry() {
        return inquiryDao.findAllInquiry();
    }

    public List<InquiryDto> searchInquiryList(InquiryDto inquiryDto) {

        if (inquiryDto.getPage() == null || inquiryDto.getPage() < 1) {
            inquiryDto.setPage(1);
        }

        if (inquiryDto.getSize() == null || inquiryDto.getSize() < 1) {
            inquiryDto.setSize(10);
        }

        int offset = (inquiryDto.getPage() - 1) * inquiryDto.getSize();

        inquiryDto.setOffset(offset);

        return inquiryDao.searchInquiryList(inquiryDto);
    }

    public int countSearchInquiryList(InquiryDto inquiryDto) {
        return inquiryDao.countSearchInquiryList(inquiryDto);
    }

    @Transactional
    public int insertAnswer(InquiryAnswerDto answerDto) {

        InquiryAnswerDto answer =
                inquiryDao.findAnswerByInquiryNo(answerDto.getInquiryNo());

        if (answer != null) {
            return 0;
        }

        int updateResult =
                inquiryDao.updateInquiryStatus(answerDto.getInquiryNo());

        if (updateResult <= 0) {
            throw new RuntimeException("문의 상태 변경 실패");
        }

        int insertResult =
                inquiryDao.insertAnswer(answerDto);

        if (insertResult <= 0) {
            throw new RuntimeException("답변 등록 실패");
        }

        return insertResult;
    }

    public InquiryAnswerDto findAnswerByInquiryNo(Integer inquiryNo) {
        return inquiryDao.findAnswerByInquiryNo(inquiryNo);
    }

    public InquiryDto findMyInquiry(Integer no, Integer memberNo) {
        return inquiryDao.findMyInquiry(no, memberNo);
    }

    public List<InquiryDto> findMyInquiryPage(InquiryDto inquiryDto) {

        if (inquiryDto.getPage() == null || inquiryDto.getPage() < 1) {
            inquiryDto.setPage(1);
        }

        if (inquiryDto.getSize() == null || inquiryDto.getSize() < 1) {
            inquiryDto.setSize(10);
        }

        int offset = (inquiryDto.getPage() - 1) * inquiryDto.getSize();

        inquiryDto.setOffset(offset);

        return inquiryDao.findMyInquiryPage(inquiryDto);
    }

    public int countMyInquiryList(Integer memberNo) {
        return inquiryDao.countMyInquiryList(memberNo);
    }

    public int updateAnswer(InquiryAnswerDto answerDto) {

        if (answerDto == null || answerDto.getNo() == null) {
            return 0;
        }

        if (answerDto.getContent() == null
                || answerDto.getContent().trim().isEmpty()) {
            return 0;
        }

        return inquiryDao.updateAnswer(answerDto);
    }

    @Transactional
    public int adminDeleteInquiry(Integer no) {

        if (no == null) {
            return 0;
        }

        inquiryDao.adminDeleteAnswerByInquiryNo(no);

        return inquiryDao.adminDeleteInquiry(no);
    }
}