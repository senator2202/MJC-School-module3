package com.epam.esm.model.entity;

import com.epam.esm.util.DateTimeUtility;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

/**
 * Entity class, representing tag of a gift certificate .
 */
@javax.persistence.Entity
@Table(
        name = "tag",
        uniqueConstraints = {@UniqueConstraint(columnNames = "id"), @UniqueConstraint(columnNames = "name")}
)
public class Tag extends Entity {

    @Column(name = "name")
    private String name;

    @Column(name = "operation")
    private String operation;

    @Column(name = "operation_date")
    private String operationDate;

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    @PrePersist
    public void onPrePersist() {
        audit("INSERT");
    }

    @PreUpdate
    public void onPreUpdate() {
        audit("UPDATE");
    }

    private void audit(String operation) {
        this.operation = operation;
        operationDate = DateTimeUtility.getCurrentDateIso();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tag tag = (Tag) o;

        if (!Objects.equals(id, tag.id)) {
            return false;
        }
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
