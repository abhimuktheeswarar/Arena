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

package msa.rehearsal.injector.components;

import dagger.Component;
import msa.rehearsal.injector.PerActivity;
import msa.rehearsal.injector.modules.ActivityModule;
import msa.rehearsal.injector.modules.MovieModule;
import msa.rehearsal.round1.subround1_2.SubRound1_2Fragment;
import msa.rehearsal.round2.subround2_1.SubRound2_1Fragment;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MovieModule.class}
)
public interface MovieComponent {

    void inject(SubRound1_2Fragment subRound12Fragment);

    void inject(SubRound2_1Fragment subRound21Fragment);


}
