package com.zrl.ssi.centralizedledger.constant;

public final class Messages {
  public static String SECRET_NOT_FOUND = "Secret searched was not found";
  public static String SECRET_STORAGE_EXCEPTION = "Error occurred during communication with storage";
  public static String CONTRACT_EXECUTION_EXCEPTION = "Smart contract execution error happened";

  public static String USER_ORG_NOT_FOUND = "User's organization was not found";
  public static String USER_ORG_NOT_FOUND_FROM_ISSUER = "User's organization was not found from issuer provided by access token";
  public static String USER_ENROLMENT_EXCEPTION = "Exception happened during user enrollment";
  public static String ENROLLMENT_NOT_FOUND = "User enrollment was not found. Please enroll user";
  public static String FABRIC_CA_PASSWORD_NOT_FOUND = "Fabric CA password not found. Re-enrollment can't proceed";
  public static String USER_LIST_FETCH_EXCEPTION = "Couldn't fetch users from database";
  public static String USER_LIST_FETCH_FORBIDDEN = "Current user has no permissions to query user list";
  public static String REGISTRATION_REQUEST_CREATION_FAILURE = "Blockchain user registration request couldn't be created";
  public static String REGISTRATION_REQUEST_CREATION_FORBIDDEN = "Current user has no permissions to create another users";
  public static String CANNOT_QUERY_BLOCKCHAIN_NETWORK = "Cannot query blockchain network";
  public static String NETWORK_PATH_DOES_NOT_EXIST = "Network config path %s does not exist";
  public static String UABLE_TO_GET_USER_CONTRACT = "Could not get user contract";

  public static String ISSUER_ID_NOT_SPECIFIED = "Issuer ID is not supplied in header 'x-hpass-issuer-id'";
  public static String ISSUER_ID_WRONG_FORMAT = "Issuer ID supplied in header 'x-hpass-issuer-id' doesn't follow format 'orgId.userId'";
  public static String ISSUER_ALREADY_ONBOARDED = "Issuer specified is already onboarded";
  public static String ISSUER_NOT_FOUND = "Certificate issuer was not found";

  public static String AUTHORIZATION_HEADER_NOT_SPECIFIED = "Authorization header not specified in 'authorization'";

  public static String PUBLIC_KEY_NOT_FOUND = "Public key specified was not found";
  public static String EXPIRATION_VALIDATION_FAILURE = "Credential is already expired: provided %s, current: %s";
  public static String DATA_SCHEMA_VALIDATION_FAILURE = "Data validation by schema failed: ";
  public static String JSON_PARSE_EXCEPTION = "JSON parse exception";
  public static String PKI_EXCEPTION = "Internal error";
  public static String X509_EXCEPTION = "Public key does not contain a valid X509 Certificate";
  public static String DCC_EXCEPTION = "DCC generation exception: ";
  public static String DATE_DESERIALIZATION_EXCEPTION = "Provided date has wrong format, expected: ";
  public static String BASE64_DESERIALIZATION_EXCEPTION = "Data provided is not encoded in base64 format";
  public static String REQUEST_FILE_READ_EXCEPTION = "File provided can't be read";
  public static String EXPIRED_OR_INVALID_JWT_TOKEN = "Expired or invalid JWT token provided in header 'authorization'";
  public static String QR_CERTIFICATE_SIZE_EXCEPTION = "Requested size is too small: ";

  public static String OBFUSCATION_MISSING_FIELD = "Data provided doesn't contain field: ";
  public static String OBFUSCATION_NON_TEXT_FIELD = "Only text values can be obfuscated, provided non text. Field: ";
  public static String OBFUSCATION_FIELD_FAILED = "Could not obfuscate value for field: ";
  public static String OBFUSCATION_DUPLICATES_FIELD_EXCEPTION = "Obfuscation request contains duplicates";

  public static String PAGINATION_MAX_PAGESIZE_EXCEPTION = "Pagesize value provided exceeds the maximum pagination size allowed";

  public static String CHAINCODE_UPDATE_LOGGING_LEVEL_EXCEPTION = "Chaincode can not be updated with logging level: ";

  public static String CANNOT_RESOLVE_BEARER_TOKEN_FROM_REQUEST = "Cannot resolve Bearer token from request";
  public static String CANNOT_EXTRACT_ISSUER_FROM_TOKEN = "Cannot extract issuer from Bearer token";
  public static String UNKNOWN_TENANT = "unknown tenant: ";

  public static String CACHE_SCHEMA_QUERY_NOT_SUPPORTED = "rich query of cached schemas is not supported";

  public static String BAD_REQUEST_ADD_CERTIFICATE_FOR_HA_OWNER = "Duplicate Owner tried to be added";

  public static String BAD_REQUEST_EMPTY_CERTIFICATE_REQUEST_EXCEPTION = "Cannot process empty request";

  public static String BAD_REQUEST_QR_SIZE_EXCEPTION = "QR size is too small.";

}
