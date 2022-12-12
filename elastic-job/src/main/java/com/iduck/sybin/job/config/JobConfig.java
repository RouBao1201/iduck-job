package com.iduck.sybin.job.config;


import com.iduck.sybin.job.model.po.IdkJobConfigPO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/11
 **/
@Component
@ConfigurationProperties(prefix = "elastic-job")
public class JobConfig {

    /**
     * 加载类型（枚举：db-数据库加载,prop-配置文件加载）
     */
    private String loadMode = "prop";

    private Map<String, IdkJobConfigPO> initJob = new HashMap<>(16);

    public String getLoadMode() {
        return loadMode;
    }

    public void setLoadMode(String loadMode) {
        this.loadMode = loadMode;
    }

    public Map<String, IdkJobConfigPO> getInitJob() {
        return initJob;
    }

    public void setInitJob(Map<String, IdkJobConfigPO> initJob) {
        this.initJob = initJob;
    }

    @Override
    public String toString() {
        return "JobConfig{" +
                "loadMode='" + loadMode + '\'' +
                ", initJob=" + initJob +
                '}';
    }
}
