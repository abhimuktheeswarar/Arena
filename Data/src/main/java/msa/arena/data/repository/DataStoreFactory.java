/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package msa.arena.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import msa.arena.data.repository.datasources.dummy.DummyDataSource;
import msa.arena.data.repository.datasources.remote.ArenaApi;
import msa.arena.data.repository.datasources.remote.RemoteConnection;
import msa.arena.data.repository.datasources.remote.RemoteDataSource;

/**
 * Factory that creates different implementations of {@link BaseDataSource}.
 */
@Singleton
class DataStoreFactory {

    private final Context context;

    @Inject
    DataStoreFactory(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Create {@link RemoteDataSource} to retrieve data from the Cloud.
     */
    RemoteDataSource createRemoteDataSource() {

        return new RemoteDataSource(RemoteConnection.createService(ArenaApi.class));
    }

    DummyDataSource createDummyDataSource() {
        return new DummyDataSource();
    }
}