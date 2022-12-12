package com.iduck.sybin.job.service.jobservice.impl;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.iduck.sybin.job.service.jobservice.BaseSimpleJob;
import org.springframework.stereotype.Service;

/**
 * 测试的定时任务
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/11
 **/
@Service
public class TestJob extends BaseSimpleJob {
    @Override
    protected void process(ShardingContext shardingContext) {
        System.out.println("songyanbin");
    }
}
