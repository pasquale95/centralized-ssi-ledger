package com.zrl.ssi.centralizedledger.controller;

import com.zrl.ssi.centralizedledger.service.DIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class DIDController {

  DIDService didService;

  @Autowired
  public DIDController(DIDService didService) {
    this.didService = didService;
  }

  @GetMapping(path = "/did/{id}")
  public Object getDID(@PathVariable("id") String id) {
    return didService.getDIDDoc(id);
  }

  @PostMapping(path = "/did/{id}")
  public Object createDID(@PathVariable("id") String id, @RequestBody Object didDoc) {
    return didService.save(id, didDoc);
  }

  @DeleteMapping(path = "/did/{id}")
  public Object deleteDID(@PathVariable("id") String id) {
    return didService.delete(id);
  }
}
