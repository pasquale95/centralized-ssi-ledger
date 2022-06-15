package com.zrl.ssi.centralizedledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity(name = "schema")
public class SchemaEntity {
  @Id
  @Column(name = "id")
  private String id;

  @Lob
  @Column(name = "document")
  private String document;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "created_at")
  private ZonedDateTime createdAt;

  public SchemaEntity() { }

  public SchemaEntity(String id, String document, ZonedDateTime createdAt) {
    this.id = id;
    this.document = document;
    this.createdAt = createdAt;
  }

  public String getId() {
    return id;
  }

  public String getDocument() {
    return document;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public String toString() {
    return "SchemaEntity{" +
        "id='" + id + '\'' +
        ", document='" + document + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchemaEntity schemaEntity = (SchemaEntity) o;
    return Objects.equals(id, schemaEntity.id) && Objects.equals(document, schemaEntity.document) && Objects
        .equals(createdAt, schemaEntity.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, document, createdAt);
  }
}
