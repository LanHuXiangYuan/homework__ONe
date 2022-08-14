package com.rdc.myapplication.model.bean


/**
 * 描述一个电影的所有信息
 */
data class MovieBean(

    /**
     * 标识此帖子的唯一字符串
     */
    val id: String,

    /**
     * 电影名
     */
    val movieName: String,

    /**
     * 上映地区
     */
    val area: String,

    /**
     * 上映时间
     */
    val time: String,
    /**
     *导演
     */
    val directorName: String,

    /**
     * 演员（可能为多个）
     */
    val actors: String,

    /**
     * 海报缩略图地址（可为null）
     */
    val pictureUrl: String? = null,


    /**
     * 热度信息
     */
    val heat: HeatBean?=null
)