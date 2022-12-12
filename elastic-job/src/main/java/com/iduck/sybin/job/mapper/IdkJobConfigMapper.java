package com.iduck.sybin.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iduck.sybin.job.model.po.IdkJobConfigPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务配置mapper
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/10
 **/
@Mapper
public interface IdkJobConfigMapper extends BaseMapper<IdkJobConfigPO> {
}
