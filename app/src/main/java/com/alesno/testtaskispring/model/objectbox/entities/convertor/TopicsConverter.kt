package com.alesno.testtaskispring.model.objectbox.entities.convertor

import android.text.TextUtils
import io.objectbox.converter.PropertyConverter

class TopicsConverter : PropertyConverter<List<String>, String> {

    override fun convertToDatabaseValue(topics: List<String>?): String {
        return if (topics != null && topics.isNotEmpty()) {
            val stringBuilder = StringBuilder()
            for (i in topics.indices) {
                if (i > 0) stringBuilder.append(",")
                stringBuilder.append(topics[i])
            }
            stringBuilder.toString()
        } else ""
    }

    override fun convertToEntityProperty(databaseValue: String?): List<String> {
        return if (!TextUtils.isEmpty(databaseValue)) {
            databaseValue!!.split(",")
        } else listOf("")
    }


}