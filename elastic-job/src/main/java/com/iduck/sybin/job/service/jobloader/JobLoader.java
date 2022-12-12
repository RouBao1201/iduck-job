package com.iduck.sybin.job.service.jobloader;

/**
 * 定时任务加载接口
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
public interface JobLoader {
    /**
     * 加载所有定时任务
     */
    void addAllJob();
}
