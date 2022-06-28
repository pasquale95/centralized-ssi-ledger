package com.zrl.ssi.centralizedledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity(name = "revocationEntry")
@IdClass(RevocationEntryId.class)
public class RevocationEntryEntity {
  @Id
  @Column(name = "id")
  private String id;

  @Lob
  @Column(name = "document")
  private String document;

  @Id
  @Column(name = "timestamp")
  private Long timestamp;

  public RevocationEntryEntity() { }

  public RevocationEntryEntity(String id, String document, Long timestamp) {
    this.id = id;
    this.document = document;
    this.timestamp = timestamp;
  }

  public String getId() {
    return id;
  }

  public String getDocument() {
    return document;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "RevocationEntryEntity{" +
        "id='" + id + '\'' +
        ", document='" + document + '\'' +
        ", timestamp=" + timestamp +
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
    RevocationEntryEntity revocationEntryEntity = (RevocationEntryEntity) o;
    return Objects.equals(id, revocationEntryEntity.id) && Objects.equals(document, revocationEntryEntity.document) && Objects
        .equals(timestamp, revocationEntryEntity.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, document, timestamp);
  }
}
