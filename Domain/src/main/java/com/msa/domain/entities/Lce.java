package com.msa.domain.entities;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

// Lce -> Loading / Content / Error
public class Lce<T> {

    boolean isLoading;
    boolean hasError;
    Throwable throwable;
    T data;

    public static <T> Lce<T> data(T data) {
        Lce lce = new Lce();
        lce.data = data;
        lce.isLoading = false;
        lce.hasError = false;
        return lce;
    }


    public static <T> Lce<T> error(Throwable error) {
        Lce lce = new Lce();
        lce.hasError = true;
        lce.throwable = error;
        return lce;
    }


    public static <T> Lce<T> loading() {
        Lce lce = new Lce();
        lce.isLoading = true;
        return lce;
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
        return data;
    }
}