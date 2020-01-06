package com.example.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.assertj.core.util.Lists;

public @Valid class OtherList extends ArrayList {

    public OtherList(List<String> values) {
        if (values != null) {
            addAll(values);
        }
    }

    // this annoation should produce an exception for a unit test
    public @NotEmpty OtherList createEmptyList() {
        return new OtherList(Lists.emptyList());
    }

    public static @NotEmpty OtherList createEmptyListStatic() {
        return new OtherList(Lists.emptyList());
    }

    public @NotNull OtherList copy() {
        return new OtherList(this);
    }

}
