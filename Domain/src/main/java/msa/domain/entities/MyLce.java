package msa.domain.entities;

/**
 * Created by Abhimuktheeswarar on 09-06-2017.
 */

public class MyLce<T> {

    boolean isLoading;
    boolean hasError;
    Throwable throwable;
    T data;

    public static <T> MyLce<T> data(T data) {
        MyLce myLce = new MyLce();
        myLce.data = data;
        myLce.isLoading = false;
        myLce.hasError = false;
        return myLce;
    }


    public static <T> MyLce<T> error(Throwable error) {
        MyLce myLce = new MyLce();
        myLce.throwable = error;
        return myLce;
    }


    public static <T> MyLce<T> loading() {
        MyLce myLce = new MyLce();
        myLce.isLoading = true;
        return myLce;
    }


    boolean isLoading() {
        return isLoading;
    }

    boolean hasError() {
        return hasError;
    }

    Throwable getError() {
        return throwable;
    }

    T getData() {
        return data;
    }

}
