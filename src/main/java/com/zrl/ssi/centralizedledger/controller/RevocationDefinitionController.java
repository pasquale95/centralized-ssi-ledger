package com.zrl.ssi.centralizedledger.controller;

import com.zrl.ssi.centralizedledger.service.RevocationDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class RevocationDefinitionController {

  RevocationDefinitionService revocationDefinitionService;

  @Autowired
  public RevocationDefinitionController(RevocationDefinitionService revocationDefinitionService) {
    this.revocationDefinitionService = revocationDefinitionService;
  }

  @GetMapping(path = "/revocationDefinition/{id}")
  public Object getRevocationDefinition(@PathVariable("id") String id) {
    return revocationDefinitionService.getRevocationDefinitionDoc(id);
  }

  @PostMapping(path = "/revocationDefinition/{id}")
  public Object createRevocationDefinition(@PathVariable("id") String id, @RequestBody Map<String, Object> revocationDefinitionDoc) {
    return revocationDefinitionService.save(id, revocationDefinitionDoc);
  }

  @DeleteMapping(path = "/revocationDefinition/{id}")
  public Object deleteRevocationDefinition(@PathVariable("id") String id) {
    return revocationDefinitionService.delete(id);
  }
}
