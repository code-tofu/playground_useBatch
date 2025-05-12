package code.tofu.useBatch.utils;

import code.tofu.useBatch.chunk.model.FlatFileLineItem;
import code.tofu.useBatch.chunk.model.FlatFileVendorDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProcessorUtils {


    public static FlatFileVendorDetails converttoVendorDetails(String input){
        try {
            return ObjectMapperUtil.INSTANCE.getObjectMapper().readValue(input, FlatFileVendorDetails.class);
        } catch (JsonProcessingException e){
            log.error("Exception while converting Vendor Details:",e);
        }
        return null;
    }

    public static List<FlatFileLineItem> convertToLineItems(String input){
        try {
            return ObjectMapperUtil.INSTANCE.getObjectMapper().readValue(input, new TypeReference<List<FlatFileLineItem>>() {});
        } catch (JsonProcessingException e){
            log.error("Exception while converting Line items:",e);
        }
        return null;
    }
}