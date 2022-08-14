package com.rdc.myapplication.model.bean

data class VarietyBean (
    val directorName: String,
    val varietyName: String,
    /**
     * 海报封面图URL（多图考虑下可能需要改用set等数据结构）
     */
    val photoUrl: String,

    val heatBean: HeatBean?=null
)