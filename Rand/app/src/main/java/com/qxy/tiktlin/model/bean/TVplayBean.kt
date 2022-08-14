package com.rdc.myapplication.model.bean

data class TVplayBean (

    /**
     * 标识此项的唯一字符串
     */
    val id: String,

    /**
     * 电影名
     */
    val movieName: String,

    /**
     * 电影英文
     */
    val movieEnName: String,

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
     * 帖子的封面图URL（多图考虑下可能需要改用set等数据结构）
     */
    val photoUrl: String,

    /**
     * 热度信息
     */
    val heat: HeatBean?=null

)
{
}