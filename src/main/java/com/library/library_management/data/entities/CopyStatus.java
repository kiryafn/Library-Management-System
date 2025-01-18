package com.library.library_management.data.entities;

public enum CopyStatus {
    Available("Available"),
    Borrowed("Borrowed"),
    Reserved("Reserved"),
    LOST("Lost");

    String name;
    CopyStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}