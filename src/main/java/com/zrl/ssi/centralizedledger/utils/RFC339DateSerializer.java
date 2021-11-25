/**
 * Licensed Materials - Property of IBM
 * IBM Digital Health Pass PID-TBD
 * (c) Copyright IBM Corporation 2020  All Rights Reserved.
 */

package com.zrl.ssi.centralizedledger.utils;

import static com.zrl.ssi.centralizedledger.constant.AppConstants.RFC3339_DATE_FORMAT;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RFC339DateSerializer extends StdSerializer<ZonedDateTime> {
  private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(RFC3339_DATE_FORMAT).withZone(ZoneId.of("UTC"));

  public RFC339DateSerializer() {
    this(null);
  }

  public RFC339DateSerializer(Class<ZonedDateTime> vc) {
    super(vc);
  }

  @Override
  public void serialize(ZonedDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(dateTimeFormatter.format(value));
  }
}
