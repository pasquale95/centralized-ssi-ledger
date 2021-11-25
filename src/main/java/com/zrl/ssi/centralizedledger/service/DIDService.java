package com.zrl.ssi.centralizedledger.service;

import com.zrl.ssi.centralizedledger.entity.DIDEntity;
import com.zrl.ssi.centralizedledger.errors.NotFoundException;
import com.zrl.ssi.centralizedledger.repository.DIDRepository;
import com.zrl.ssi.centralizedledger.utils.CustomUtils;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DIDService {

  DIDRepository didRepository;
  CustomUtils utils;

  @Autowired
  public DIDService(DIDRepository didRepository, CustomUtils utils) {
    this.didRepository = didRepository;
    this.utils = utils;
  }

  public Object getDIDDoc(String id) {
    Optional<DIDEntity> didOptional = didRepository.findById(id);
    if (didOptional.isEmpty()) {
      throw new NotFoundException("DID " + id + " was not found");
    }

    return utils.readSingleJSON(didOptional.get().getDocument().getBytes(StandardCharsets.UTF_8));
  }

  public Object save(String id, Object didDoc) {
    ZonedDateTime now = utils.currentDateTime();
    String document = utils.writeJSON(didDoc);
    DIDEntity didEntity = new DIDEntity(id, document, now);
    didRepository.save(didEntity);

    return didDoc;
  }

  public Object delete(String id) {
    Object didDoc = this.getDIDDoc(id);

    didRepository.deleteById(id);

    return didDoc;
  }
}
