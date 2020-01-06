package com.example.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public @Valid class MyList extends ArrayList {

    public @NotEmpty MyList(List<String> values) {
        if(values != null) {
            addAll(values);
        }
    }

}
