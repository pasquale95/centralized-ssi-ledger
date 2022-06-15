package com.zrl.ssi.centralizedledger.controller;

import com.zrl.ssi.centralizedledger.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class SchemaController {

  SchemaService schemaService;

  @Autowired
  public SchemaController(SchemaService schemaService) {
    this.schemaService = schemaService;
  }

  @GetMapping(path = "/schema/{id}")
  public Object getSchema(@PathVariable("id") String id) {
    return schemaService.getSchema(id);
  }

  @PostMapping(path = "/schema/{id}")
  public Object createSchema(@PathVariable("id") String id, @RequestBody Object schemaDoc) {
    return schemaService.save(id, schemaDoc);
  }

  @DeleteMapping(path = "/schema/{id}")
  public Object deleteSchema(@PathVariable("id") String id) {
    return schemaService.delete(id);
  }
}
