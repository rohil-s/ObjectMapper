package jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreType
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataModel {
    String test;
}
