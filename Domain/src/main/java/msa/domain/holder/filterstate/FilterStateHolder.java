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

package msa.domain.holder.filterstate;

import org.jetbrains.annotations.Nullable;

import io.reactivex.annotations.NonNull;

/**
 * Created by Abhimuktheeswarar on 05-07-2017.
 */

public class FilterStateHolder<F> {

    @NonNull
    private final FilterState filterState;

    @NonNull
    private final F filter;

    private FilterStateHolder(@NonNull FilterState filterState, @NonNull F filter) {
        this.filterState = filterState;
        this.filter = filter;
    }

    public static <F> FilterStateHolder<F> initialized() {
        return new FilterStateHolder<>(FilterState.INITIALIZED, null);
    }

    public static <F> FilterStateHolder<F> basic(@Nullable F filter) {
        return new FilterStateHolder<>(FilterState.BASIC, filter);
    }

    public static <F> FilterStateHolder<F> apply(@Nullable F filter) {
        return new FilterStateHolder<>(FilterState.APPLIED, filter);
    }

    public static <F> FilterStateHolder<F> reset() {
        return new FilterStateHolder<>(FilterState.RESET, null);
    }

    public static <F> FilterStateHolder<F> reset(@Nullable F filter) {
        return new FilterStateHolder<>(FilterState.RESET, filter);
    }

    public static <F> FilterStateHolder<F> close() {
        return new FilterStateHolder<>(FilterState.CLOSE, null);
    }

    public FilterState getFilterState() {
        return filterState;
    }

    public F getFilter() {
        return filter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilterStateHolder<?> filterStateHolder = (FilterStateHolder<?>) o;

        if (filterState != filterStateHolder.filterState) {
            return false;
        }
        return filter != null ? filter.equals(filterStateHolder.filter) : filterStateHolder.filter == null;
    }

    @Override
    public int hashCode() {
        int result = filterState.hashCode();
        result = 31 * result + (filter != null ? filter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResourceCarrier{" +
                "filterState=" + filterState +
                ", filter=" + filter +
                '}';
    }
}
