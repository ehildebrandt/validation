package com.example.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated(MyGroup1.class)
public class FooWithGroups {

    public void foo(@NotNull(groups = MyGroup1.class) @Size(min = 2, max = 5, groups = MyGroup2.class) String foo) {

    }

}
