package com.alesno.testtaskispring.model.objectbox.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.relation.ToOne;

@Entity
public class ExpertObj {

    @Id
    public long id;

    public String avatar;

    @NameInDb("first_name")
    public String firstName;

    @NameInDb("id_response")
    public int idResponse;

    @NameInDb("second_name")
    public String secondName;

    public String speciality;

    public ToOne<VideoObj> video;

}
