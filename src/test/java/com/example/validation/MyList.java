package com.example.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.assertj.core.util.Lists;

public @Valid class MyList extends ArrayList {

    public @NotEmpty MyList(List<String> values) {
        if(values != null) {
            addAll(values);
        }
    }

    // this annoation should produce an exception for a unit test
    public static MyList createEmptyList(List<String> values) {
        return new MyList(values);
    }

}
