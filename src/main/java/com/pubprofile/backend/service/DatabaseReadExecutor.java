package com.pubprofile.backend.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.function.Supplier;

@Component
public class DatabaseReadExecutor {

    private static final Logger log = LoggerFactory.getLogger(DatabaseReadExecutor.class);
    private static final int MAX_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MILLIS = 250L;

    public <T> T execute(String operation, Supplier<T> supplier) {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                return supplier.get();
            } catch (RuntimeException ex) {
                lastException = ex;

                if (!isRetryable(ex) || attempt == MAX_ATTEMPTS) {
                    throw ex;
                }

                log.warn("Transient database read failure during {} (attempt {}/{}). Retrying.",
                        operation, attempt, MAX_ATTEMPTS, ex);
                sleepBeforeRetry();
            }
        }

        throw lastException;
    }

    private boolean isRetryable(Throwable throwable) {
        Throwable current = throwable;

        while (current != null) {
            if (current instanceof DataAccessException
                    || current instanceof JDBCConnectionException
                    || current instanceof PersistenceException
                    || current instanceof SQLException) {
                return true;
            }
            current = current.getCause();
        }

        return false;
    }

    private void sleepBeforeRetry() {
        try {
            Thread.sleep(RETRY_DELAY_MILLIS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Database read retry interrupted", ex);
        }
    }
}
