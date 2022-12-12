package com.iduck.sybin.job.util;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iduck.sybin.job.config.MultiDbConfig;
import com.iduck.sybin.job.constant.MultiSourceConst;
import com.iduck.sybin.job.mapper.IdkJobDatasourceMapper;
import com.iduck.sybin.job.model.po.IdkJobDatasourcePO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多数据源连接持有工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
@Component
public class JdbcMultiHolder {

    private static final Logger log = LoggerFactory.getLogger(JdbcMultiHolder.class);

    private static final String DEFAULT_SOURCE_KEY = "default";

    private static final Map<String, JdbcTemplate> MULTI_JDBC_MAP = new ConcurrentHashMap<>();

    @Autowired
    IdkJobDatasourceMapper idkJobDatasourceMapper;

    @Autowired
    private JdbcTemplate defaultJdbcTemplate;

    @Autowired
    MultiDbConfig multiDbConfig;


    @PostConstruct
    public void init() {
        if (multiDbConfig.isEnable()) {
            initJdbcTemplate();
        }
    }

    public JdbcTemplate getDefault() {
        return MULTI_JDBC_MAP.get(DEFAULT_SOURCE_KEY);
    }

    public JdbcTemplate get(String key) {
        return MULTI_JDBC_MAP.get(key);
    }

    public Map<String, JdbcTemplate> getAll() {
        return MULTI_JDBC_MAP;
    }

    private void initJdbcTemplate() {
        // 设置默认链接
        MULTI_JDBC_MAP.put(DEFAULT_SOURCE_KEY, defaultJdbcTemplate);
        if (MultiSourceConst.Loader.DB_MODE.equals(multiDbConfig.getLoadMode())) {
            loadFromDb();
        } else if (MultiSourceConst.Loader.PROP_MODE.equals(multiDbConfig.getLoadMode())) {
            loadFromProp();
        }
    }

    private void loadFromProp() {
        Map<String, IdkJobDatasourcePO> initDb = multiDbConfig.getInitDb();
        initDb.forEach((key, value) -> {
            MULTI_JDBC_MAP.put(key, createJdbcTemplate(value));
        });
    }

    private void loadFromDb() {
        LambdaQueryWrapper<IdkJobDatasourcePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IdkJobDatasourcePO::getState, MultiSourceConst.State.EFFECT);
        List<IdkJobDatasourcePO> dataSourceList = idkJobDatasourceMapper.selectList(wrapper);
        if (CollUtil.isNotEmpty(dataSourceList)) {
            dataSourceList.forEach(it -> {
                MULTI_JDBC_MAP.put(it.getSourceKey(), createJdbcTemplate(it));
            });
        }
    }

    private JdbcTemplate createJdbcTemplate(IdkJobDatasourcePO config) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(config.getUrl());
        druidDataSource.setDriverClassName(config.getDriverClassName());
        druidDataSource.setUsername(config.getUsername());
        druidDataSource.setPassword(config.getPassword());
        jdbcTemplate.setDataSource(druidDataSource);
        return jdbcTemplate;
    }

    private JdbcMultiHolder() {

    }
}
