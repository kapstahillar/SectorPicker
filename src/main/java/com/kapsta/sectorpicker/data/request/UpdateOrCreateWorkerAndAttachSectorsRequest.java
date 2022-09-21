package com.kapsta.sectorpicker.data.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
public class UpdateOrCreateWorkerAndAttachSectorsRequest {
    private String workerId = null;

    @NotEmpty(message = "Choose at least 1 sector")
    private List<Long> sectors = new ArrayList<>();

    @NotEmpty(message = "Name is mandatory!")
    private String name = "";

    @NotNull(message = "Terms and conditions are mandatory!")
    @AssertTrue(message = "Have to accept terms and conditions!")
    private boolean tac = false;

    public UUID getUUID() {
        if (workerId == null || workerId.isEmpty()) {
            return null;
        }
        return UUID.fromString(workerId);
    }
}
