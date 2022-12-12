package com.iduck.sybin.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iduck.sybin.job.model.po.IdkJobDatasourcePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务多数据源配置
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
@Mapper
public interface IdkJobDatasourceMapper extends BaseMapper<IdkJobDatasourcePO> {
}
