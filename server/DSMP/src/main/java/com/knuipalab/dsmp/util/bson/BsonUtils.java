package com.knuipalab.dsmp.util.bson;

import org.bson.BsonValue;

public class BsonUtils {

    public static Object toJavaType(BsonValue value) {

        switch (value.getBsonType()) {
            case INT32:
                return value.asInt32().getValue();
            case INT64:
                return value.asInt64().getValue();
            case STRING:
                return value.asString().getValue();
            case DECIMAL128:
                return value.asDecimal128().doubleValue();
            case DOUBLE:
                return value.asDouble().getValue();
            case BOOLEAN:
                return value.asBoolean().getValue();
            case OBJECT_ID:
                return value.asObjectId().getValue();
            case BINARY:
                return value.asBinary().getData();
            case SYMBOL:
                return value.asSymbol().getSymbol();
            case ARRAY:
                return value.asArray().toArray();
            default:
                return value;
        }
    }
}
