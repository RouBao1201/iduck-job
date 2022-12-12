package com.iduck.sybin.job.service.jobloader.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iduck.sybin.job.config.JobConfig;
import com.iduck.sybin.job.constant.JobConst;
import com.iduck.sybin.job.mapper.IdkJobConfigMapper;
import com.iduck.sybin.job.model.po.IdkJobConfigPO;
import com.iduck.sybin.job.service.jobhandler.ElasticJobHandler;
import com.iduck.sybin.job.service.jobloader.JobLoader;
import com.iduck.sybin.job.service.jobservice.BaseSimpleJob;
import com.iduck.sybin.job.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认定时任务加载类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
@Service
public class DefaultJobLoader implements JobLoader {
    private static final Logger log = LoggerFactory.getLogger(DefaultJobLoader.class);

    @Autowired
    JobConfig jobConfig;

    @Autowired
    IdkJobConfigMapper idkJobConfigMapper;

    @Autowired
    ElasticJobHandler elasticJobHandler;

    /**
     * 添加所有任务
     */
    @Override
    public void addAllJob() {
        String loadMode = jobConfig.getLoadMode();
        log.info("DefaultJobLoader ==> 开始加载所有定时任务, 任务启动模式:{}", loadMode);
        int maxJob = 0;
        int successJobSize = 0;
        if (JobConst.Loader.PROP_MODE.equals(loadMode)) {
            Map<String, IdkJobConfigPO> initJobMap = jobConfig.getInitJob();
            ArrayList<IdkJobConfigPO> propConfigList = new ArrayList<>();
            initJobMap.forEach((key, value) -> {
                value.setJobName(key);
                propConfigList.add(value);
            });
            maxJob = propConfigList.size();
            successJobSize = createJob(propConfigList);
        } else if (JobConst.Loader.DB_MODE.equals(loadMode)) {
            LambdaQueryWrapper<IdkJobConfigPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(IdkJobConfigPO::getState, JobConst.State.EFFECT);
            List<IdkJobConfigPO> dbConfigList = idkJobConfigMapper.selectList(wrapper);
            maxJob = dbConfigList.size();
            successJobSize = createJob(dbConfigList);
        }
        log.info("DefaultJobLoader ==> 定时任务全部加载完毕, 应加载任务数:{}, 加载成功任务数:{}", maxJob, successJobSize);
    }

    /**
     * 创建任务
     *
     * @param jobConfigList 任务配置集合
     * @return 创建成功数量
     */
    private int createJob(List<IdkJobConfigPO> jobConfigList) {
        AtomicInteger successJobSize = new AtomicInteger();
        jobConfigList.forEach(it -> {
            try {
                BaseSimpleJob bean = SpringContextHolder.getBean(it.getBeanName(), BaseSimpleJob.class);
                String jobDetail = JSON.toJSONString(it);
                assert bean != null;
                elasticJobHandler.addJob(it.getJobName(), bean, it.getCron(), jobDetail, it.getShardingCount(), it.getShardingParam());
                successJobSize.getAndIncrement();
            } catch (Exception e) {
                log.error("DefaultJobLoader ==> 定时任务创建失败, 抛弃该任务. JobName:{}", it.getJobName(), e);
            }
        });
        return successJobSize.get();
    }
}
