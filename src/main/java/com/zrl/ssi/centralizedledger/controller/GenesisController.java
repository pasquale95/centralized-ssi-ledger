package com.zrl.ssi.centralizedledger.controller;
import com.zrl.ssi.centralizedledger.service.DIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(path = "")
public class GenesisController {

  @Autowired
  Environment environment;

  DIDService didService;

  @Autowired
  public GenesisController(DIDService didService) {
    this.didService = didService;
  }

  @GetMapping(path = "/genesis")
  public Object getDID() {
    return "{\"client_ip\":\"http://host.docker.internal\",\"client_port\":8080}";
  }

  @PostMapping(path = "/register")
  public Object registerDID(@RequestBody LinkedHashMap<String, String> didDoc) {
    return didService.register(didDoc);
  }
}
