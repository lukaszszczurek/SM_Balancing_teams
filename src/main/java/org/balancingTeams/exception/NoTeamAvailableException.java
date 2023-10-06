package org.balancingTeams.exception;

public class NoTeamAvailableException extends RuntimeException{

    public static final String DEFAULT_MESSAGE =
            "No team available when not everyone can be assigned to a team in this iteration";

    public NoTeamAvailableException(String message) {
        super(message);
    }
}
