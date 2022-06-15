package com.zrl.ssi.centralizedledger.controller;
import com.zrl.ssi.centralizedledger.service.DIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "")
public class GenesisController {

  DIDService didService;

  @Autowired
  public GenesisController(DIDService didService) {
    this.didService = didService;
  }

  @GetMapping(path = "/genesis")
  public Object getDID() {
    return "{}";
  }

  @PostMapping(path = "/register")
  public Object registerDID(@RequestBody Object didDoc) {
    return didService.register(didDoc);
  }
}
