package com.msa.domain.entities;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

public final class User {

    private final String userId, displayName;

    public User(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
