package com.zrl.ssi.centralizedledger.entity;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

public class RevocationEntryId implements Serializable {
  private String id;
  private Long timestamp;

  public RevocationEntryId() {}

  public RevocationEntryId(String id, Long timestamp) {
    this.id = id;
    this.timestamp = timestamp;
  }
}
