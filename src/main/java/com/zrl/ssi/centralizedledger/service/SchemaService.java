package com.zrl.ssi.centralizedledger.service;

import com.zrl.ssi.centralizedledger.entity.DIDEntity;
import com.zrl.ssi.centralizedledger.entity.SchemaEntity;
import com.zrl.ssi.centralizedledger.errors.NotFoundException;
import com.zrl.ssi.centralizedledger.repository.SchemaRepository;
import com.zrl.ssi.centralizedledger.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class SchemaService {

  SchemaRepository schemaRepository;
  CustomUtils utils;

  @Autowired
  public SchemaService(SchemaRepository schemaRepository, CustomUtils utils) {
    this.schemaRepository = schemaRepository;
    this.utils = utils;
  }

  public Object getSchema(String id) {
    Optional<SchemaEntity> schemaOptional = schemaRepository.findById(id);
    if (schemaOptional.isEmpty()) {
      throw new NotFoundException("Schema " + id + " was not found");
    }
    return utils.readSingleJSON(schemaOptional.get().getDocument().getBytes(StandardCharsets.UTF_8));
  }

  public Object save(String id, Object didDoc) {
    ZonedDateTime now = utils.currentDateTime();
    String document = utils.writeJSON(didDoc);
    SchemaEntity didEntity = new SchemaEntity(id, document, now);
    schemaRepository.save(didEntity);
    return didDoc;
  }

  public Object delete(String id) {
    Object didDoc = this.getSchema(id);
    schemaRepository.deleteById(id);
    return didDoc;
  }
}
