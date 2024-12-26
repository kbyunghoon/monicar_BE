package org.emulator.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ControlData {
    private String controlId;
    private String controlCode;
    private String cycleInfoTransmitTerm;
}
