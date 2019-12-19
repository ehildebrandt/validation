package com.example.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
public class Foo {

    public void foo(@NotNull @Size(min = 2, max = 5) String foo) {
    }
}
