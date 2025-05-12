package code.tofu.useBatch.chunk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlatFileVendorDetails {

    String vendorId;
    String vendorName;
    String vendorContact;
    String vendorAddress;
}
