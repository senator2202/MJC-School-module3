package com.epam.esm.model.entity;

import javax.persistence.*;

/**
 * Entity class, representing  abstract project entity.
 */
@MappedSuperclass
public abstract class GiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
