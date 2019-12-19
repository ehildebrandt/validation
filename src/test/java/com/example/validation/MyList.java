package com.example.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.assertj.core.util.Lists;

public @Valid class MyList extends ArrayList {

    public @NotEmpty MyList(List<String> values) {
        if(values != null) {
            addAll(values);
        }
    }

    // this annoation should produce an exception for a unit test
    public static @NotEmpty MyList createEmptyList() {
        return new MyList(Lists.emptyList());
    }

    public @NotNull MyList copy() {
        return new MyList(this);
    }

}
