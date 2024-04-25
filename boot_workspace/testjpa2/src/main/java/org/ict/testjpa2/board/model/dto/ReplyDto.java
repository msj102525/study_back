package org.ict.testjpa2.board.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.ict.testjpa2.board.jpa.entity.ReplyEntity;
import org.springframework.stereotype.Component;

@Data   // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ReplyDto {
    private int replyNum;
    private String replyWriter;
    private String replyTitle;
    private String replyContent;
    private int boardRef;
    private int replyReplyRef;
    private int replyLev;
    private int replySeq;
    private int replyReadCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date replyDate;

    public ReplyEntity toEntity() {
        return ReplyEntity.builder()
                .replyNum(this.replyNum)
                .replyWriter(this.replyWriter)
                .replyTitle(this.replyTitle)
                .replyContent(this.replyContent)
                .boardRef(this.boardRef)
                .replyReplyRef(this.replyReplyRef)
                .replyLev(this.replyLev)
                .replySeq(this.replySeq)
                .replyReadCount(this.replyReadCount)
                .replyDate(this.replyDate)
                .build();
    }

}
