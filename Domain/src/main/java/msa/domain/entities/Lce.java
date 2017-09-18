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

// Lce -> Loading / Content / Error
public abstract class Lce<T> {

    public static <T> Lce<T> data(final T data) {
        return new Lce<T>() {
            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean hasError() {
                return false;
            }

            @Override
            public Throwable getError() {
                return null;
            }

            @Override
            public T getData() {
                return data;
            }
        };
    }


    public static <T> Lce<T> error(final Throwable error) {
        return new Lce<T>() {
            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean hasError() {
                return true;
            }

            @Override
            public Throwable getError() {
                return error;
            }

            @Override
            public T getData() {
                return null;
            }
        };
    }


    public static <T> Lce<T> loading() {
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
            public Throwable getError() {
                return null;
            }

            @Override
            public T getData() {
                return null;
            }
        };
    }


    public abstract boolean isLoading();

    public abstract boolean hasError();

    public abstract Throwable getError();

    public abstract T getData();
}