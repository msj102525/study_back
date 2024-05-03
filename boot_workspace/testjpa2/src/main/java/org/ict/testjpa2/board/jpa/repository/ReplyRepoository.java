package org.ict.testjpa2.board.jpa.repository;

import org.ict.testjpa2.board.jpa.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepoository extends JpaRepository<ReplyEntity, Integer> {
}
