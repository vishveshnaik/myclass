package com.example.baker_street.models

import java.io.Serializable

//POST
//data class MaterialsModel(
//    val courseid: Any? = null,
//    val file :
//            val materials : ArrayList<String>
//    )

data class MaterialModel(
    val url: String? = null,
    val createdAt: String? = null,
//    //TODO
//val publisher:String?=null,
//    val text :String?=null
) : Serializable

data class MaterialsModel(
    val materials: ArrayList<MaterialModel>? = null
)