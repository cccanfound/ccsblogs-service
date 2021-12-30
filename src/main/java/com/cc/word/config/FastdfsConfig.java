package com.cc.word.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author liyc
 * @date 2021/9/29 15:16
 */
public class FastdfsConfig {

    @Configuration
    @Import(FdfsClientConfig.class) // 导入FastDFS-Client组件
    @EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING) // 解决jmx重复注册bean的问题
    public class FasfdfsConfig {

    }


}
