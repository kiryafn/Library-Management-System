package com.library.library_management.data.entities;

public enum CopyStatus {
    AVAILABLE("Available"),
    BORROWED("Borrowed"),
    RESERVED("Reserved"),
    LOST("Lost");

    String name;
    CopyStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}