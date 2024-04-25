package org.ict.testjpa2.notice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.notice.jpa.entity.NoticeEntity;
import org.springframework.stereotype.Component;

@Data   // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class NoticeDto {
    private String noticeNo;
    private String noticeTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date noticeDate;
    private String noticeWriter;
    private String noticeContent;
    private String originalFilePath;
    private String renameFilePath;
    private String importance;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date importEndDate;
    private int readCount;

    public NoticeEntity toEntity() {
        return NoticeEntity.builder()
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
