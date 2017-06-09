package com.msa.domain.entities;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

// Lce -> Loading / Content / Error
public abstract class Lce<T> {

    static boolean isLoading = true;
    static boolean hasError = false;
    private static Throwable throwable;
    private T dataPayload;
    private Lce<T> da;

    public static <T> Lce<T> data(final T data) {
        isLoading = false;
        hasError = false;
        return new Lce<T>() {
            @Override
            public boolean isLoading() {
                return true;
            }

            @Override
            public boolean hasError() {
                return false;
            }

            @Override
            public T getData() {
                return data;
            }
        };
    }

    public static <T> Lce<T> error(Throwable error) {
        hasError = true;
        throwable = error;
        return Lce.error(Lce.throwable);
    }

    public static <T> Lce<T> loading() {
        isLoading = true;
        return Lce.loading();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    public Throwable getError() {
        return throwable;

    }

    public T getData() {
        return dataPayload;
    }
}