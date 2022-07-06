package com.zrl.ssi.centralizedledger.service;

import com.zrl.ssi.centralizedledger.entity.RevocationDefinitionEntity;
import com.zrl.ssi.centralizedledger.errors.NotFoundException;
import com.zrl.ssi.centralizedledger.repository.RevocationDefinitionRepository;
import com.zrl.ssi.centralizedledger.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class RevocationDefinitionService {

  RevocationDefinitionRepository revocationDefinitionRepository;
  CustomUtils utils;

  @Autowired
  public RevocationDefinitionService(RevocationDefinitionRepository revocationDefinitionRepository, CustomUtils utils) {
    this.revocationDefinitionRepository = revocationDefinitionRepository;
    this.utils = utils;
  }

  public Object getRevocationDefinitionDoc(String id) {
    Optional<RevocationDefinitionEntity> revocationDefinitionOptional = revocationDefinitionRepository.findById(id);
    if (revocationDefinitionOptional.isEmpty()) {
      throw new NotFoundException("RevocationDefinition " + id + " was not found");
    }
    return utils.readSingleJSON(revocationDefinitionOptional.get().getDocument().getBytes(StandardCharsets.UTF_8));
  }

  public Object save(String id, Map<String, Object> revocationDefinitionDoc) {
    ZonedDateTime now = utils.currentDateTime();
    revocationDefinitionDoc.put("txnTime", now.toEpochSecond());
    String document = utils.writeJSON(revocationDefinitionDoc);
    RevocationDefinitionEntity revocationDefinitionEntity = new RevocationDefinitionEntity(id, document, now);
    revocationDefinitionRepository.save(revocationDefinitionEntity);
    return revocationDefinitionDoc;
  }

  public Object delete(String id) {
    Object didDoc = this.getRevocationDefinitionDoc(id);
    revocationDefinitionRepository.deleteById(id);
    return didDoc;
  }
}
