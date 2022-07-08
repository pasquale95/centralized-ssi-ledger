package com.zrl.ssi.centralizedledger.controller;
import com.zrl.ssi.centralizedledger.service.DIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(path = "")
public class RegistrationController {

  DIDService didService;

  @Autowired
  public RegistrationController(DIDService didService) {
    this.didService = didService;
  }

  @PostMapping(path = "/register")
  public Object registerDID(@RequestBody LinkedHashMap<String, String> didDoc) {
    return didService.register(didDoc);
  }
}
