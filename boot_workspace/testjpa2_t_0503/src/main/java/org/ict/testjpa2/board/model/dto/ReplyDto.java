package org.ict.testjpa2.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.board.jpa.entity.ReplyEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ReplyDto {
    //게시글 댓글, 대댓글
    private int replyNum;
    private String replyWriter;
    private String replyTitle;
    private String replyContent;
    private int boardRef;
    private int replyReplyRef;
    private int replyLev;
    private int replySeq;
    private int replyReadCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date replyDate;

    //dto -> entity 로 변환하는 메서드 추가함
    public ReplyEntity toEntity(){
        return  ReplyEntity.builder()
                .replyNum(replyNum)
                .replyTitle(replyTitle)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .boardRef(boardRef)
                .replyLev(replyLev)
                .replyReplyRef(replyReplyRef)
                .replySeq(replySeq)
                .replyReadCount(replyReadCount)
                .replyDate(replyDate)
                .build();
    }
}
