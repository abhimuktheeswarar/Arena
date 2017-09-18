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

package msa.data.repository;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@Singleton
public class DataStoreFac {

    private final Context context;

    @Inject
    public DataStoreFac(Context context) {
        this.context = context;
    }
}
