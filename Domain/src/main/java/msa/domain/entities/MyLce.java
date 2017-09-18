/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
