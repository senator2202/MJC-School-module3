package com.epam.esm.model.entity;

import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiftCertificate extends RepresentationModel<GiftCertificate> implements GiftEntity {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<Tag> tags;

    public GiftCertificate() {
    }

    public GiftCertificate(GiftCertificate certificate) {
        id = certificate.id;
        name = certificate.name;
        description = certificate.description;
        price = certificate.price;
        duration = certificate.duration;
        createDate = certificate.createDate;
        lastUpdateDate = certificate.lastUpdateDate;
        if (certificate.getTags() != null) {
            tags = new ArrayList<>();
            certificate.getTags().forEach(t -> tags.add(new Tag(t.getId(), t.getName())));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags != null ? new ArrayList<>(tags) : null;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void clearAllTags() {
        tags = new ArrayList<>();
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(name, that.name)) {
            return false;
        }
        if (!Objects.equals(description, that.description)) {
            return false;
        }
        if (!Objects.equals(price, that.price)) {
            return false;
        }
        if (!Objects.equals(duration, that.duration)) {
            return false;
        }
        return Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
