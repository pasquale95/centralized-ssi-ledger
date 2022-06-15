package com.zrl.ssi.centralizedledger.service;

import com.zrl.ssi.centralizedledger.entity.CredentialDefinitionEntity;
import com.zrl.ssi.centralizedledger.entity.SchemaEntity;
import com.zrl.ssi.centralizedledger.errors.NotFoundException;
import com.zrl.ssi.centralizedledger.repository.CredentialDefinitionRepository;
import com.zrl.ssi.centralizedledger.repository.SchemaRepository;
import com.zrl.ssi.centralizedledger.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CredentialDefinitionService {

  CredentialDefinitionRepository credentialDefinitionRepository;
  CustomUtils utils;

  @Autowired
  public CredentialDefinitionService(CredentialDefinitionRepository credentialDefinitionRepository, CustomUtils utils) {
    this.credentialDefinitionRepository = credentialDefinitionRepository;
    this.utils = utils;
  }

  public Object getCredentialDefinition(String id) {
    Optional<CredentialDefinitionEntity> credentialDefinitionOptional = credentialDefinitionRepository.findById(id);
    if (credentialDefinitionOptional.isEmpty()) {
      throw new NotFoundException("Credential definition " + id + " was not found");
    }

    return utils.readSingleJSON(credentialDefinitionOptional.get().getDocument().getBytes(StandardCharsets.UTF_8));
  }

  public Object save(String id, Object didDoc) {
    ZonedDateTime now = utils.currentDateTime();
    String document = utils.writeJSON(didDoc);
    CredentialDefinitionEntity didEntity = new CredentialDefinitionEntity(id, document, now);
    credentialDefinitionRepository.save(didEntity);

    return didDoc;
  }

  public Object delete(String id) {
    Object didDoc = this.getCredentialDefinition(id);

    credentialDefinitionRepository.deleteById(id);

    return didDoc;
  }
}
