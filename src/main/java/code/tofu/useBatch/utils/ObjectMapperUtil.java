package code.tofu.useBatch.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperUtil {

    INSTANCE;

    private final ObjectMapper mapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        return mapper;
    }
}
