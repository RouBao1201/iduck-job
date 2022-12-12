package com.iduck.sybin.job.service.jobhandler;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.JobRegistry;
import com.dangdang.ddframe.job.lite.internal.schedule.JobScheduleController;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * elastic-job操作类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
@Service
public class ElasticJobHandler {
    private static final Logger log = LoggerFactory.getLogger(ElasticJobHandler.class);

    @Resource
    private ZookeeperRegistryCenter regCenter;

    /**
     * 添加定时任务
     *
     * @param jobName                任务名
     * @param simpleJob              定时任务实现类
     * @param cron                   cron表达式
     * @param shardingTotalCount     分片数
     * @param shardingItemParameters 分片参数
     */
    public void addJob(final String jobName,
                       final SimpleJob simpleJob,
                       final String cron,
                       final String jobParameter,
                       final int shardingTotalCount,
                       final String shardingItemParameters) {
        log.info("ElasticJobHandler ==> 注册定时任务. jobName:{}, cron:{}, shardingTotalCount:{}, shardingItemParameters:{}",
                jobName, cron, shardingTotalCount, shardingItemParameters);
        LiteJobConfiguration liteJobConfiguration = getLiteJobConfiguration(jobName, simpleJob.getClass(), cron,
                jobParameter, shardingTotalCount, shardingItemParameters);
        SpringJobScheduler scheduler = new SpringJobScheduler(simpleJob, regCenter, liteJobConfiguration);
        scheduler.init();
    }

    /**
     * 修改任务
     *
     * @param jobName 任务名
     * @param cron    cron表达式
     */
    public void updateJob(String jobName, String cron) {
        JobScheduleController jobScheduleController = JobRegistry.getInstance().getJobScheduleController(jobName);
        if (jobScheduleController != null) {
            jobScheduleController.rescheduleJob(cron);
        }
    }

    /**
     * 移除任务
     *
     * @param jobName 任务名
     */
    public void removeJob(String jobName) {
        JobScheduleController jobScheduleController = JobRegistry.getInstance().getJobScheduleController(jobName);
        if (jobScheduleController != null) {
            // 暂停任务
            jobScheduleController.pauseJob();

            // 关闭调度器
            jobScheduleController.shutdown();

            // 删除节点
            regCenter.remove("/" + jobName);
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName 任务名
     */
    public void pauseJob(String jobName) {
        JobScheduleController jobScheduleController = JobRegistry.getInstance().getJobScheduleController(jobName);
        if (jobScheduleController != null) {
            jobScheduleController.pauseJob();
        }
    }

    /**
     * 获取定时任务配置
     *
     * @param jobName                任务名
     * @param jobClass               任务逻辑类
     * @param cron                   cron表达式
     * @param shardingTotalCount     分片数
     * @param shardingItemParameters 分片参数
     * @return LiteJobConfiguration
     */
    public LiteJobConfiguration getLiteJobConfiguration(final String jobName,
                                                        final Class<? extends SimpleJob> jobClass,
                                                        final String cron,
                                                        final String jobParameter,
                                                        final int shardingTotalCount,
                                                        final String shardingItemParameters) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .jobParameter(jobParameter).build();
        JobTypeConfiguration jobTypeConfiguration =
                new SimpleJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName());
        return LiteJobConfiguration.newBuilder(jobTypeConfiguration).overwrite(true).build();
    }
}
