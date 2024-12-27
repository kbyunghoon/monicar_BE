package org.emulator.infrastructure;

public final class EmulatorConstant {
    static final String TERMINAL_ID = "A001";
    static final String MANUFACTURER_ID = "6";
    static final String PACKET_VERSION = "5";
    static final String DEVICE_ID = "1";
    static final String CONTROL_CODE = "05";

    private EmulatorConstant() {
        throw new AssertionError();
    }
}
