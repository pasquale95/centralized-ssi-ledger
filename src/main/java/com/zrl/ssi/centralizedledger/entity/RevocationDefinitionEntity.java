package com.zrl.ssi.centralizedledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity(name = "revocationDefinition")
public class RevocationDefinitionEntity {
  @Id
  @Column(name = "id")
  private String id;

  @Lob
  @Column(name = "document")
  private String document;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "created_at")
  private ZonedDateTime createdAt;

  public RevocationDefinitionEntity() { }

  public RevocationDefinitionEntity(String id, String document, ZonedDateTime createdAt) {
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
    return "RevocationDefinitionEntity{" +
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
    RevocationDefinitionEntity revocationDefinitionEntity = (RevocationDefinitionEntity) o;
    return Objects.equals(id, revocationDefinitionEntity.id) && Objects.equals(document, revocationDefinitionEntity.document) && Objects
        .equals(createdAt, revocationDefinitionEntity.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, document, createdAt);
  }
}
