package com.zrl.ssi.centralizedledger.controller;

import com.zrl.ssi.centralizedledger.service.CredentialDefinitionService;
import com.zrl.ssi.centralizedledger.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class CredentialDefinitionController {

  CredentialDefinitionService credentialDefinitionService;

  @Autowired
  public CredentialDefinitionController(CredentialDefinitionService credentialDefinitionService) {
    this.credentialDefinitionService = credentialDefinitionService;
  }

  @GetMapping(path = "/credentialDefinition/{id}")
  public Object getCredentialDefinition(@PathVariable("id") String id) {
    return credentialDefinitionService.getCredentialDefinition(id);
  }

  @PostMapping(path = "/credentialDefinition/{id}")
  public Object createCredentialDefinition(@PathVariable("id") String id, @RequestBody Object credentialDefinitionDoc) {
    return credentialDefinitionService.save(id, credentialDefinitionDoc);
  }

  @DeleteMapping(path = "/credentialDefinition/{id}")
  public Object deleteCredentialDefinition(@PathVariable("id") String id) {
    return credentialDefinitionService.delete(id);
  }
}
