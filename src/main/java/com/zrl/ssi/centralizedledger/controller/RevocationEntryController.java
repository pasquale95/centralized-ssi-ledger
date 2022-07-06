package com.zrl.ssi.centralizedledger.controller;

import com.zrl.ssi.centralizedledger.entity.RevocationEntryId;
import com.zrl.ssi.centralizedledger.service.RevocationEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class RevocationEntryController {

  RevocationEntryService revocationEntryService;

  @Autowired
  public RevocationEntryController(RevocationEntryService revocationEntryService) {
    this.revocationEntryService = revocationEntryService;
  }

  @GetMapping(path = "/revocationEntry/{id}")
  public Object getRevocationEntry(
      @PathVariable("id") String id,
      @RequestParam(required=false) Map<String,String> queryParams
  ) {
    return revocationEntryService.getRevocationEntryDoc(new RevocationEntryId(id, Long.parseLong(queryParams.get("timestamp"))));
  }

  @GetMapping(path = "/revocationDelta/{id}")
  public Object getRevocationDelta(
      @PathVariable("id") String id,
      @RequestParam(required=false) Map<String,String> queryParams
  ) {
    return revocationEntryService.getRevocationEntryDelta(id, Long.parseLong(queryParams.get("timestamp")));
  }

  @PostMapping(path = "/revocationEntry/{id}")
  public Object createRevocationEntry(@PathVariable("id") String id, @RequestBody Object revocationEntryDoc) {
    return revocationEntryService.save(id, revocationEntryDoc);
  }

  @DeleteMapping(path = "/revocationEntry/{id}")
  public Object deleteRevocationEntry(
      @PathVariable("id") String id,
      @RequestParam(required=false) Map<String,String> queryParams
  ) {
    return revocationEntryService.delete(id, queryParams.get("timestamp"));
  }
}
