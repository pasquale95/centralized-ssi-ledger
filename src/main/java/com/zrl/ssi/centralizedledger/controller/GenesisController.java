package com.zrl.ssi.centralizedledger.controller;
import com.zrl.ssi.centralizedledger.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(path = "")
public class GenesisController {

  @Value("${server.port}")
  private int serverPort;

  CustomUtils utils;

  @Autowired
  public GenesisController(CustomUtils utils) {
    this.utils = utils;
  }

  @GetMapping(path = "/genesis")
  public Object getDID() {
    LinkedHashMap<String, String> genesisData = new LinkedHashMap<>();
    genesisData.put("client_ip", "http://host.docker.internal");
    genesisData.put("client_port", String.valueOf(this.serverPort));
    return utils.writeJSON(genesisData);
  }
}
