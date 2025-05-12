package code.tofu.useBatch.chunk;

import code.tofu.useBatch.chunk.model.FlatFileInput;
import code.tofu.useBatch.utils.ProcessorUtils;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import static code.tofu.useBatch.utils.ProcessorUtils.convertToLineItems;

public class FlatFileInputFieldSetMapper implements FieldSetMapper<FlatFileInput> {

    private final static String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZ";


    @Override
        public FlatFileInput mapFieldSet(FieldSet fieldSet) throws BindException {
        return FlatFileInput.builder()
                .purchaseOrderNumber(fieldSet.readString("purchase_order_number"))
                .orderDateTime(fieldSet.readDate("order_date_time",dateTimeFormat))
                .fileFlatFileVendorDetails(ProcessorUtils.converttoVendorDetails(fieldSet.readString("vendor_details")))
                .lineItems(convertToLineItems(fieldSet.readString("line_items")))
                .build();

    }

}
