package com.zrl.ssi.centralizedledger.repository;

import com.zrl.ssi.centralizedledger.entity.DIDEntity;
import com.zrl.ssi.centralizedledger.entity.SchemaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaRepository extends CrudRepository<SchemaEntity, String> {

}
