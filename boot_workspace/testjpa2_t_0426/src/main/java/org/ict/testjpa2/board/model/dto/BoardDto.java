package org.ict.testjpa2.board.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.ict.testjpa2.board.jpa.entity.BoardEntity;
import org.springframework.stereotype.Component;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class BoardDto {
    //게시글 원글
    //프로퍼티(property, == 멤버변수, field)
    private int boardNum;
    private String boardWriter;
    private String boardTitle;
    private String boardContent;
    private String boardOriginalFileName;
    private String boardRenameFileName;
    private int boardReadCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date boardDate;

    //dto --> entity 로 변환하는 메소드 추가함
    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .boardNum(this.boardNum)
                .boardWriter(this.boardWriter)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardOriginalFileName(this.boardOriginalFileName)
                .boardRenameFileName(this.boardRenameFileName)
                .boardReadCount(this.boardReadCount)
                .boardDate(this.boardDate)
                .build();
    }
}
