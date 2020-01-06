package com.example.validation;

import javax.validation.constraints.Size;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@Value.Style(get = {"is*", "get*"})
public interface Sample extends Validatable {

    @Size(min = 1, max = 5)
    String getName();

}
