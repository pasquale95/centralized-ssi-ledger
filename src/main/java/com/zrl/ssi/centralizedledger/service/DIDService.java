package com.zrl.ssi.centralizedledger.service;

import com.zrl.ssi.centralizedledger.entity.DIDEntity;
import com.zrl.ssi.centralizedledger.errors.NotFoundException;
import com.zrl.ssi.centralizedledger.repository.DIDRepository;
import com.zrl.ssi.centralizedledger.utils.CustomUtils;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Optional;
import org.hyperledger.indy.sdk.did.Did;
import org.hyperledger.indy.sdk.did.DidJSONParameters;
import org.hyperledger.indy.sdk.did.DidResults.CreateAndStoreMyDidResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hyperledger.indy.sdk.wallet.Wallet;
import javax.annotation.PreDestroy;

@Service
public class DIDService {

  protected static final String WALLET = "Wallet1";
  protected static final String TYPE = "default";
  protected static final String WALLET_CONFIG = "{ \"id\":\"" + WALLET + "\", \"storage_type\":\"" + TYPE + "\"}";
  protected static final String WALLET_CREDENTIALS = "{\"key\":\"8dvfYSt5d1taSd6yJdpjq4emkwsPDDLYxkNFysFD2cZY\", \"key_derivation_method\":\"RAW\"}";

  DIDRepository didRepository;
  CustomUtils utils;
  Wallet wallet;

  /**
   * IMPORTANT: To let it run correctly it is necessary to compile the
   * "indy-sdk" library and then let it point correctly through the
   * variable DYLD_LIBRARY_PATH=<path_where_libindy.so_is_located>
   * (if you build from source it would be under indy-sdk/libindy/target/debug/)
   */
  @Autowired
  public DIDService(DIDRepository didRepository, CustomUtils utils) {
    try {
      this.didRepository = didRepository;
      this.utils = utils;
      Wallet.createWallet(WALLET_CONFIG, WALLET_CREDENTIALS).get();
      this.wallet = Wallet.openWallet(WALLET_CONFIG, WALLET_CREDENTIALS).get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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

  public Object register(LinkedHashMap<String, String> didDoc) {
    try {
      // generate DID and verKey from seed
      String seed = didDoc.get("seed");
      String config = new DidJSONParameters.CreateAndStoreMyDidJSONParameter(null, seed, null, null).toJson();
      CreateAndStoreMyDidResult result = Did.createAndStoreMyDid(this.wallet, config).get();
      String verKey = result.getVerkey();
      String did = result.getDid();

      // create DIDDoc and store on ledger
      ZonedDateTime now = utils.currentDateTime();
      LinkedHashMap<String, String> nym = new LinkedHashMap<>();
      nym.put("did", did);
      nym.put("verKey", verKey);
      nym.put("alias", didDoc.get("alias"));
      nym.put("role", didDoc.get("role"));
      String document = utils.writeJSON(nym);
      DIDEntity didEntity = new DIDEntity(did, document, now);
      didRepository.save(didEntity);
      return getDIDDoc(did);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Object delete(String id) {
    Object didDoc = this.getDIDDoc(id);
    didRepository.deleteById(id);
    return didDoc;
  }

  @PreDestroy
  public void springPreDestroy() {
    try {
      wallet.closeWallet().get();
      Wallet.deleteWallet(WALLET_CONFIG, WALLET_CREDENTIALS).get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
