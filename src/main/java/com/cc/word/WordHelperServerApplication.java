package com.cc.word;

import com.cc.word.config.TimeAspectConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
//定时任务@EnableScheduling
//异步任务@EnableAsync
@MapperScan("com.cc.word.dao")
@EnableTransactionManagement
public class WordHelperServerApplication {
    static Logger logger= LoggerFactory.getLogger(WordHelperServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(WordHelperServerApplication.class, args);
    }

}
