package com.alesno.testtaskispring.model.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToOne

@Entity
data class ExpertObject(

    var avatar: String? = null,

    @NameInDb("first_name")
    var firstName: String? = null,

    @NameInDb("id_response")
    var idResponse: Int = 0,

    @NameInDb("second_name")
    var secondName: String? = null,

    var speciality: String? = null,

    @Id var id: Long = 0
){

    lateinit var video: ToOne<VideoObject>
}