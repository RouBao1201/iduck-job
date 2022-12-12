package com.iduck.sybin.job.service.jobservice;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础Simple定时任务（所有创建的simple类型的任务需继承该类）
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
public abstract class BaseSimpleJob implements SimpleJob {
    private static final Logger log = LoggerFactory.getLogger(BaseSimpleJob.class);


    /**
     * 定时任务执行逻辑
     *
     * @param shardingContext 任务上下文
     */
    protected abstract void process(ShardingContext shardingContext);

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("BaseSimpleJob ==> 执行定时任务:{}, 分片数:{}, 分片项:{}, 分片参数:{}",
                shardingContext.getJobName(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter());
        try {
            process(shardingContext);
        } catch (Exception e) {
            log.error("BaseSimpleJob ==> 定时任务执行异常, JobName:{}", shardingContext.getJobName(), e);
        }
    }
}
