package org.ict.first;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@MapperScan(value= {"org.ict.first.board.mapper", "org.ict.first.notice.mapper", "org.ict.first.member.mapper"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BootFirstMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootFirstMybatisApplication.class, args);
	}

}
