package com.example.validation;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface Bar {

   void bar(@NotNull @NotEmpty List<String> bar);

}
