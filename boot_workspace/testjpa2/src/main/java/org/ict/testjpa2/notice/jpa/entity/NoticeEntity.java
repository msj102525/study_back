package org.ict.testjpa2.notice.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.notice.model.dto.NoticeDto;

import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Reply")
@Entity
public class NoticeEntity {
    @Id
    @Column(name = "NOTICENO", nullable = false)
    private String noticeNo;

    @Column(name = "NOTICETITLE", nullable = false)
    private String noticeTitle;

    @Column(name = "NOTICEDATE")
    private java.util.Date noticeDate;

    @Column(name = "NOTICEWRITER", nullable = false)
    private String noticeWriter;

    @Column(name = "NOTICECONTENT")
    private String noticeContent;

    @Column(name = "ORIGINAL_FILEPATH")
    private String originalFilePath;

    @Column(name = "RENAME_FILEPATH")
    private String renameFilePath;

    @Column(name = "IMPORTANCE", nullable = false, columnDefinition = "N")
    private String importance;

    @Column(name = "IMP_END_DATE", nullable = false)
    private java.util.Date importEndDate;

    @Column(name = "READCOUNT", nullable = false, columnDefinition = "1")
    private int readCount;

    @PrePersist     // jpa 로 가기 전에 작동됨
    public void prePersist() {
        noticeDate = new GregorianCalendar().getGregorianChange();
        importEndDate = new GregorianCalendar().getGregorianChange();
    }

    public NoticeDto toDto() {
        return NoticeDto.builder()
                .noticeNo(this.noticeNo)
                .noticeTitle(this.noticeTitle)
                .noticeDate(this.noticeDate)
                .noticeWriter(this.noticeWriter)
                .noticeContent(this.noticeContent)
                .originalFilePath(this.originalFilePath)
                .renameFilePath(this.renameFilePath)
                .importance(this.importance)
                .importEndDate(this.importEndDate)
                .readCount(this.readCount)
                .build();
    }

}
