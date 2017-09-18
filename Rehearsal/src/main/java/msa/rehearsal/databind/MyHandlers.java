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

package msa.rehearsal.databind;

import android.util.Log;
import android.view.View;

/**
 * Created by Abhimuktheeswarar on 07-06-2017.
 */

public class MyHandlers {

    public void onClickFriend(View view) {
        Log.d(msa.rehearsal.databind.MyHandlers.class.getSimpleName(), "Clicked");
    }
}