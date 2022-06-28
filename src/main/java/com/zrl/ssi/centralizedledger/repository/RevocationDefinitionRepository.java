package com.zrl.ssi.centralizedledger.repository;

import com.zrl.ssi.centralizedledger.entity.DIDEntity;
import com.zrl.ssi.centralizedledger.entity.RevocationDefinitionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevocationDefinitionRepository extends CrudRepository<RevocationDefinitionEntity, String> {

}
