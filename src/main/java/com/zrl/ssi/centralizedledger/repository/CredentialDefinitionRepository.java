package com.zrl.ssi.centralizedledger.repository;

import com.zrl.ssi.centralizedledger.entity.CredentialDefinitionEntity;
import com.zrl.ssi.centralizedledger.entity.DIDEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialDefinitionRepository extends CrudRepository<CredentialDefinitionEntity, String> {

}
