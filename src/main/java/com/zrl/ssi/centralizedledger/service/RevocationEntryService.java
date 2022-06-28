package com.zrl.ssi.centralizedledger.service;

import com.zrl.ssi.centralizedledger.entity.RevocationDefinitionEntity;
import com.zrl.ssi.centralizedledger.entity.RevocationEntryEntity;
import com.zrl.ssi.centralizedledger.entity.RevocationEntryId;
import com.zrl.ssi.centralizedledger.errors.NotFoundException;
import com.zrl.ssi.centralizedledger.repository.RevocationEntryRepository;
import com.zrl.ssi.centralizedledger.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RevocationEntryService {

  RevocationEntryRepository revocationEntryRepository;
  CustomUtils utils;

  @Autowired
  public RevocationEntryService(RevocationEntryRepository revocationEntryRepository, CustomUtils utils) {
    this.revocationEntryRepository = revocationEntryRepository;
    this.utils = utils;
  }

  public Object getRevocationEntryDoc(RevocationEntryId id) {
    Optional<RevocationEntryEntity> revocationDefinitionOptional = revocationEntryRepository.findById(id);
    if (revocationDefinitionOptional.isEmpty()) {
      throw new NotFoundException("RevocationDefinition " + id + " was not found");
    }

    return utils.readSingleJSON(revocationDefinitionOptional.get().getDocument().getBytes(StandardCharsets.UTF_8));
  }

  public Object getRevocationEntryDelta(String id, Long timestamp) {
    ZonedDateTime timestampDate = utils.currentDateTime();
    //if (timestampDate == null) {
    //  timestampDate = ZonedDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(timestamp)), ZoneOffset.UTC);
    //}
    if (timestamp == null) {
      timestamp = timestampDate.toEpochSecond();
    }
    List<RevocationEntryEntity> revocationEntryEntities = revocationEntryRepository.findByIdAndTimestampBeforeOrderByTimestampDesc(id, timestamp);
    if (revocationEntryEntities.isEmpty()) {
      throw new NotFoundException("RevocationEntry " + id + " was not found");
    }
    // return most recent revocationEntryDoc
    RevocationEntryEntity mostRecentRevocationEntry = revocationEntryEntities.get(0);
    Map<String, Object> revocationEntryDoc = utils.readSingleJSON(mostRecentRevocationEntry.getDocument().getBytes(StandardCharsets.UTF_8));
    revocationEntryDoc.put("timestamp", mostRecentRevocationEntry.getTimestamp());
    return revocationEntryDoc;
  }

  public Object save(String id, Object revocationEntryDoc) {
    ZonedDateTime now = utils.currentDateTime();
    String document = utils.writeJSON(revocationEntryDoc);
    RevocationEntryEntity revocationEntryEntity = new RevocationEntryEntity(id, document, now.toEpochSecond());
    revocationEntryRepository.save(revocationEntryEntity);

    return revocationEntryDoc;
  }

  public Object delete(String id, String timestamp) {
    RevocationEntryId revId = new RevocationEntryId(id, Long.parseLong(timestamp));
    Object didDoc = this.getRevocationEntryDoc(revId);
    revocationEntryRepository.deleteById(revId);

    return didDoc;
  }
}
