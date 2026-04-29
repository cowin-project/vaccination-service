package com.vaccination.service.entity.enums;

public enum DoseNumber {
    DOSE_1(1),
    DOSE_2(2);

    private final int value;

    DoseNumber(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
