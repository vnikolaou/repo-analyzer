package net.pionware.oss.repo.service.internal;

import java.io.IOException;

@FunctionalInterface
public interface ResponseHandler<T> {
    T handle(String response) throws IOException;
}
