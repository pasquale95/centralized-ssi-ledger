package com.zrl.ssi.centralizedledger.repository;

import com.zrl.ssi.centralizedledger.entity.RevocationEntryEntity;
import com.zrl.ssi.centralizedledger.entity.RevocationEntryId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface RevocationEntryRepository extends CrudRepository<RevocationEntryEntity, RevocationEntryId> {

  List<RevocationEntryEntity> findByIdAndTimestampBeforeOrderByTimestampDesc(String id, Long timestamp);
}
