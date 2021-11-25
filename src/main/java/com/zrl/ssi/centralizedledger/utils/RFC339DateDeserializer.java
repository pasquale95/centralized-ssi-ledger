/**
 * Licensed Materials - Property of IBM
 * IBM Digital Health Pass PID-TBD
 * (c) Copyright IBM Corporation 2020  All Rights Reserved.
 */

package com.zrl.ssi.centralizedledger.utils;

import static com.zrl.ssi.centralizedledger.constant.AppConstants.RFC3339_DATE_FORMAT;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.zrl.ssi.centralizedledger.constant.Messages;
import com.zrl.ssi.centralizedledger.errors.BadRequestException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RFC339DateDeserializer extends StdDeserializer<ZonedDateTime> {
  private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(RFC3339_DATE_FORMAT).withZone(ZoneId.of("UTC"));

  @Override
  public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    String date = jsonParser.getText();

    try {
      return ZonedDateTime.parse(date, dateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new BadRequestException(Messages.DATE_DESERIALIZATION_EXCEPTION + RFC3339_DATE_FORMAT, e);
    }
  }

  public RFC339DateDeserializer() {
    this(null);
  }

  public RFC339DateDeserializer(Class<?> vc) {
    super(vc);
  }
}
