package com.alesno.testtaskispring.model.objectbox.entity;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class VideoObj {

    @Id
    public long id;

    @Backlink(to = "video")
    public ToMany<ExpertObj> experts;

    @NameInDb("id_response")
    public String idResponse;

    //@Backlink(to = "video")
    public ToOne<MetaObj> meta;

    @NameInDb("favorite")
    public Boolean isFavorite;

    @NameInDb("progress_time")
    public Long progressTime;
}
