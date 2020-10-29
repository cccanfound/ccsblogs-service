package com.cc.word.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liyc
 * @date 2020/9/24 15:39
 */
@Configuration
@ComponentScan("com.cc.word")
@EnableAspectJAutoProxy
public class TimeAspectConfig {
}
