package com.solmaz.entity;

public enum LiveTime {
    THREE("3 Gün"),SEVEN("7 Gün"),FIFTEEN("15 Gün"),INDEFINITE("Süresiz");
    private final String name;

    LiveTime(String name) {
        this.name = name;
    }
}
