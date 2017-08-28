/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package msa.domain.holder.datastate;


import org.jetbrains.annotations.Nullable;

import io.reactivex.annotations.NonNull;


/**
 * A generic class that holds a value with its loading dataState.
 *
 * @param <D>
 */
public class DataStateContainer<D> {

    @NonNull
    private DataState dataState;

    @Nullable
    private String message;

    @Nullable
    private D data;

    public DataStateContainer() {
        this.dataState = DataState.INITIALIZE;
        this.data = null;
        this.message = null;
    }

    public DataStateContainer(D data) {
        this.dataState = DataState.INITIALIZE;
        this.data = data;
        this.message = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataStateContainer<?> that = (DataStateContainer<?>) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResourceCarrier{" +
                "dataState=" + dataState +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public DataState getDataState() {
        return dataState;
    }

    public void setDataState(DataState dataState) {
        this.dataState = dataState;
    }

    @Nullable
    public D getData() {
        return data;
    }

    public void setData(@Nullable D data) {
        this.data = data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }
}
