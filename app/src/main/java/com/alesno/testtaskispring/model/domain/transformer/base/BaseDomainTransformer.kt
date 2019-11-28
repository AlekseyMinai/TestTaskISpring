package com.alesno.testtaskispring.model.domain.transformer.base

import com.alesno.testtaskispring.model.domain.VideoDomain
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

abstract class BaseDomainTransformer<V : VideoDomain> :
    DomainTransformer<V> {


}