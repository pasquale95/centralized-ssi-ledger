/**
 * Licensed Materials - Property of IBM
 * IBM Digital Health Pass PID-TBD
 * (c) Copyright IBM Corporation 2020  All Rights Reserved.
 */

package com.zrl.ssi.centralizedledger.utils;

import static com.zrl.ssi.centralizedledger.constant.AppConstants.RFC3339_DATE_FORMAT;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zrl.ssi.centralizedledger.constant.Messages;
import com.zrl.ssi.centralizedledger.errors.ServiceRuntimeException;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomUtils {

  private ObjectMapper mapper;

  @Autowired
  public CustomUtils(ObjectMapper mapper) {
    this.mapper = mapper;
    this.mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
  }

  public Map<String, Object> readSingleJSON(byte[] rawJSON) {
    try {
      return mapper.readValue(rawJSON, new TypeReference<Map<String, Object>>() {
      });
    } catch (IOException e) {
      throw new ServiceRuntimeException(Messages.JSON_PARSE_EXCEPTION, e);
    }
  }


  public <T> T readJSON(byte[] rawJSON, Class<T> type) {
    try {
      return mapper.readValue(rawJSON, type);
    } catch (IOException e) {
      throw new ServiceRuntimeException(Messages.JSON_PARSE_EXCEPTION, e);
    }
  }

  public String writeJSON(Object object) {
    try {
      return this.mapper.writeValueAsString(object);
    } catch (IOException e) {
      throw new ServiceRuntimeException(Messages.JSON_PARSE_EXCEPTION, e);
    }
  }

  public String writeSortedJSON(Object object) {
    try {
      JsonNode node = mapper.convertValue(object, JsonNode.class);
      Object tree = mapper.treeToValue(node, Object.class);
      return this.mapper.writeValueAsString(tree);
    } catch (IOException e) {
      throw new ServiceRuntimeException(Messages.JSON_PARSE_EXCEPTION, e);
    }
  }

  public List<Map<String, Object>> readArrayJSON(byte[] rawJSON) {
    try {
      return mapper.readValue(rawJSON, new TypeReference<List<Map<String, Object>>>() {
      });
    } catch (IOException e) {
      throw new ServiceRuntimeException(Messages.JSON_PARSE_EXCEPTION, e);
    }
  }

  public <T> T convertValue(Object fromValue, Class<T> toValueType) {
    return this.mapper.convertValue(fromValue, toValueType);
  }

  public String generateUUIDV4() {
    return UUID.randomUUID().toString();
  }

  public byte[] generateRandomBytes(int bytesCount) {
    SecureRandom secureRandom = new SecureRandom();
    byte bytes[] = new byte[bytesCount];
    secureRandom.nextBytes(bytes);

    return bytes;
  }

  public String dateTimeRFC339() {
    return DateTimeFormatter.ofPattern(RFC3339_DATE_FORMAT).format(OffsetDateTime.now(ZoneOffset.UTC));
  }

  public ZonedDateTime currentDateTime() {
    return ZonedDateTime.now(ZoneOffset.UTC);
  }
}
