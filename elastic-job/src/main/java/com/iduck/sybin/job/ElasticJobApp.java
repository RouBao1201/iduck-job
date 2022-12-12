package com.iduck.sybin.job;

import com.iduck.sybin.job.service.jobloader.JobLoader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 定时任务服务
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
@SpringBootApplication
@MapperScan("com.iduck.sybin.job.mapper")
public class ElasticJobApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ElasticJobApp.class);
        app.run(args);
    }

    @Autowired
    JobLoader jobLoader;

    @Override
    public void run(String... args) throws Exception {
        jobLoader.addAllJob();
    }
}
