package com.iduck.sybin.job.config;

import com.iduck.sybin.job.model.po.IdkJobDatasourcePO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/11
 **/
@Component
@ConfigurationProperties(prefix = "multi.datasource")
public class MultiDbConfig {

    /**
     * 开关
     */
    private boolean enable = false;

    /**
     * 加载类型（枚举：db-数据库,prop-配置文件）
     */
    private String loadMode = "prop";

    private Map<String, IdkJobDatasourcePO> initDb = new HashMap<>(16);

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setInitDb(Map<String, IdkJobDatasourcePO> initDb) {
        this.initDb = initDb;
    }

    public String getLoadMode() {
        return loadMode;
    }

    public void setLoadMode(String loadMode) {
        this.loadMode = loadMode;
    }

    public Map<String, IdkJobDatasourcePO> getInitDb() {
        return initDb;
    }

    @Override
    public String toString() {
        return "MultiDbConfig{" +
                "enable=" + enable +
                ", loadMode='" + loadMode + '\'' +
                ", initDb=" + initDb +
                '}';
    }
}
