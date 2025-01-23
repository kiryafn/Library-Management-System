package com.library.library_management.data.entities;

/**
 * Enumeration representing the possible statuses of a book copy in the library management system.
 *
 * <p>The {@code CopyStatus} enum defines the lifecycle states that a physical copy of a book
 * can have, such as "Available", "Borrowed", "Reserved", or "Lost". These statuses are
 * used to track the current state of book copies and help manage their availability
 * within the library system.</p>
 */
public enum CopyStatus {

    /**
     * Status indicating that the copy is available for borrowing.
     */
    Available("Available"),

    /**
     * Status indicating that the copy has been borrowed and is currently checked out.
     */
    Borrowed("Borrowed"),

    /**
     * Status indicating that the copy has been reserved by a user but is not yet borrowed.
     */
    Reserved("Reserved"),

    /**
     * Status indicating that the copy has been reported as lost.
     */
    Lost("Lost");

    /**
     * A human-readable representation of the status.
     *
     * <p>This field stores the name of the status, which may be displayed in the user interface
     * or used in other parts of the system for status representation.</p>
     */
    private final String name;

    /**
     * Constructor for {@code CopyStatus}.
     *
     * <p>Initializes a {@code CopyStatus} with the given name.</p>
     *
     * @param name the human-readable name of the status
     */
    CopyStatus(String name) {
        this.name = name;
    }

    /**
     * Retrieves the human-readable name of the status.
     *
     * <p>The name is typically used for displaying the status in a user interface or
     * for identifying the status in business logic.</p>
     *
     * @return the name of the status
     */
    public String getName() {
        return name;
    }
}