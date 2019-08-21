package dds.frba.utn.quemepongo.Model.WebServices;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults( level = AccessLevel.PRIVATE)
public class Error {
    String timestamp;
    Integer status;
    String error;
    String message;
    String path;
}
