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

package msa.arena.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import msa.arena.movies.search.searchmenu.SearchMenuDummyFragment;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SearchMenuDummyFragment contributeSearchMenuDummyFragment();
}
