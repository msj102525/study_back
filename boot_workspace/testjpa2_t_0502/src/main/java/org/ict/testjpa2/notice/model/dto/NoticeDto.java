package org.ict.testjpa2.notice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.notice.jpa.entity.NoticeEntity;
import org.springframework.stereotype.Component;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class NoticeDto {
    private int noticeNo;
    private String noticeTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date noticeDate;
    private String noticeWriter;
    private String noticeContent;
    private String originalFilePath;
    private String renameFilePath;
    private String importance;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String  impEndDate;
    private int readCount;

    public NoticeEntity toEntity(){
        return NoticeEntity.builder()
                .noticeNo(noticeNo)
                .noticeTitle(noticeTitle)
                .noticeWriter(noticeWriter)
                .noticeContent(noticeContent)
                .noticeDate(noticeDate)
                .readCount(readCount)
                .originalFilePath(originalFilePath)
                .renameFilePath(renameFilePath)
                .importance(importance)
                .impEndDate(java.sql.Date.valueOf(impEndDate))
                .build();
    }
}
