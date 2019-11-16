package com.alesno.testtaskispring.model.objectbox.entity;

import com.alesno.testtaskispring.model.objectbox.entity.convertor.TopicsConverter;

import java.util.List;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class MetaObj {

    @Id
    public long id;

    public String title;

    @Convert(converter = TopicsConverter.class, dbType = String.class)
    public List<String> topics;

    public String url;

    public ToOne<VideoObj> video;

}
