package com.iduck.sybin.job.constant;

/**
 * 多数据源常量
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/11
 **/
public class MultiSourceConst {

    /**
     * 多数据源加载配置
     */
    public static class Loader {
        /**
         * 数据源类型加载
         */
        public static final String DB_MODE = "db";

        /**
         * 配置文件方式加载
         */
        public static final String PROP_MODE = "prop";
    }

    /**
     * 状态
     */
    public static class State {
        /**
         * 生效
         */
        public static final String EFFECT = "E";

        /**
         * 失效
         */
        public static final String FAILURE = "F";
    }

    private MultiSourceConst() {

    }
}
