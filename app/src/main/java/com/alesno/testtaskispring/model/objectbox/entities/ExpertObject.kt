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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpertObject

        if (avatar != other.avatar) return false
        if (firstName != other.firstName) return false
        if (idResponse != other.idResponse) return false
        if (secondName != other.secondName) return false
        if (speciality != other.speciality) return false
        if (video != other.video) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatar?.hashCode() ?: 0
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + idResponse
        result = 31 * result + (secondName?.hashCode() ?: 0)
        result = 31 * result + (speciality?.hashCode() ?: 0)
        result = 31 * result + video.hashCode()
        return result
    }


}