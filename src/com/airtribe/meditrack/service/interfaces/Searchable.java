
package com.airtribe.meditrack.service.interfaces;

public interface Searchable {

    boolean matches(String query);

    default boolean matches(int value) {
        return false;
    }
}
