package org.example.locationdistanceservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionRequest {
    @Size(min = 2, max = 2, message = "параметр startPos должен содержать 2 элемента.")
    @NotNull(message = "параметр startPos не должен быть null.")
    private double[] startPos;
    @Size(min = 2, max = 2, message = "параметр endPos должен содержать 2 элемента.")
    @NotNull(message = "параметр endPos не должен быть null.")
    private double[] endPos;
}
