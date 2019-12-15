package com.example.validation;

import javax.validation.constraints.Size;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import org.springframework.validation.annotation.Validated;

@Immutable
@Value.Style(get = {"is*", "get*"})
@Validated(MyGroup1.class)
public interface SampleWithGroups extends Validatable {

    @Size(groups = MyGroup1.class, min = 2, max = 5)
    @Size(groups = MyGroup2.class, min = 1, max = 5)
    String getName();

}
