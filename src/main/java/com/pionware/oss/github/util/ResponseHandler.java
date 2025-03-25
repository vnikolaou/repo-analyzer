package com.pionware.oss.github.util;

import java.io.IOException;

@FunctionalInterface
public interface ResponseHandler<T> {
    T handle(String response) throws IOException;
}
