package com.example.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class FooBar {

    public FooBar(@NotNull @Min(1) Integer fooBar) {

    }

}
