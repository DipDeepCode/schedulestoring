package ru.ddc.schedulestoring.entity;

import java.time.LocalTime;

public enum SlotStartTime {
    S0600(LocalTime.of(6, 0)),
    S0615(LocalTime.of(6, 15)),
    S0630(LocalTime.of(6, 30)),
    S0645(LocalTime.of(6, 45));

    private final LocalTime time;

    SlotStartTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
