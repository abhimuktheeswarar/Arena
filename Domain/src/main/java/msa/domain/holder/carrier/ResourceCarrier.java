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

package msa.domain.holder.carrier;


import org.jetbrains.annotations.Nullable;

import io.reactivex.annotations.NonNull;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <D>
 */
public class ResourceCarrier<D> {

    @NonNull
    public final Status status;

    @NonNull
    public final int code;

    @Nullable
    public final String message;

    @Nullable
    public final D data;

    private ResourceCarrier(@NonNull Status status, @Nullable int code, @Nullable D data, @Nullable String message) {
        this.status = status;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <D> ResourceCarrier<D> success(@Nullable D data) {
        return new ResourceCarrier<>(Status.SUCCESS, 200, data, null);
    }

    public static <D> ResourceCarrier<D> error(String msg) {
        return new ResourceCarrier<>(Status.ERROR, 0, null, msg);
    }

    public static <D> ResourceCarrier<D> error(String msg, int code) {
        return new ResourceCarrier<>(Status.ERROR, code, null, msg);
    }

    public static <D> ResourceCarrier<D> error(String msg, int code, @Nullable D data) {
        return new ResourceCarrier<>(Status.ERROR, code, data, msg);
    }

    public static <D> ResourceCarrier<D> networkError(String msg, @Nullable D data) {
        return new ResourceCarrier<>(Status.NETWORK_ERROR, 0, data, msg);
    }

    public static <D> ResourceCarrier<D> loading(@Nullable D data) {
        return new ResourceCarrier<>(Status.LOADING, 0, data, null);
    }

    public static <D> ResourceCarrier<D> completed(@Nullable D data) {
        return new ResourceCarrier<>(Status.COMPLETED, 200, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceCarrier<?> resource = (ResourceCarrier<?>) o;

        if (status != resource.status) {
            return false;
        }

        if (code != resource.code) {
            return false;
        }

        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = code + status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResourceCarrier{" +
                "status=" + status +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
