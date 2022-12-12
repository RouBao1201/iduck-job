package com.iduck.sybin.job.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 定时任务配置实体类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
@TableName("idk_job_config")
public class IdkJobConfigPO {
    @TableId
    @TableField("job_name")
    private String jobName;

    @TableField("cron")
    private String cron;

    @TableField("sharding_count")
    private Integer shardingCount;

    @TableField("sharding_param")
    private String shardingParam;

    @TableField("job_mode")
    private String jobMode;

    @TableField("bean_name")
    private String beanName;

    @TableField("source_key")
    private String sourceKey;

    @TableField("state")
    private String state;

    @TableField("create_time")
    private Date createTime;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getJobMode() {
        return jobMode;
    }

    public void setJobMode(String jobMode) {
        this.jobMode = jobMode;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public Integer getShardingCount() {
        return shardingCount;
    }

    public void setShardingCount(Integer shardingCount) {
        this.shardingCount = shardingCount;
    }

    public String getShardingParam() {
        return shardingParam;
    }

    public void setShardingParam(String shardingParam) {
        this.shardingParam = shardingParam;
    }

    @Override
    public String toString() {
        return "IdkJobConfigPO{" +
                "jobName='" + jobName + '\'' +
                ", cron='" + cron + '\'' +
                ", shardingCount='" + shardingCount + '\'' +
                ", shardingParam='" + shardingParam + '\'' +
                ", jobMode='" + jobMode + '\'' +
                ", beanName='" + beanName + '\'' +
                ", sourceKey='" + sourceKey + '\'' +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
