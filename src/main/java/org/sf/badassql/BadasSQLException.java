package org.sf.badassql;

/**
 * Thrown when bad SQL is found.
 */
public class BadasSQLException extends RuntimeException {
    public BadasSQLException(String reason) {
        super(reason);
    }
}
