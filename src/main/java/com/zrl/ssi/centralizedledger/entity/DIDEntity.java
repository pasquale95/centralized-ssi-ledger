package com.zrl.ssi.centralizedledger.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name = "did")
public class DIDEntity {
  @Id
  @Column(name = "id")
  private String id;

  @Lob
  @Column(name = "document")
  private String document;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "created_at")
  private ZonedDateTime createdAt;

  public DIDEntity() { }

  public DIDEntity(String id, String document, ZonedDateTime createdAt) {
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
    return "DIDEntity{" +
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
    DIDEntity didEntity = (DIDEntity) o;
    return Objects.equals(id, didEntity.id) && Objects.equals(document, didEntity.document) && Objects
        .equals(createdAt, didEntity.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, document, createdAt);
  }
}
