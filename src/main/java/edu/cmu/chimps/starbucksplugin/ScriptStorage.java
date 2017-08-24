/*
  Copyright 2017 CHIMPS Lab, Carnegie Mellon University

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package edu.cmu.chimps.starbucksplugin;

import android.content.Context;
import android.content.SharedPreferences;

public class ScriptStorage {
    public static final String POSITION = "send_script_position";
    public static final String STORAGE = "save_script_file";

    public static void storeScript(Context context, String scriptName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE).edit();

        editor.putString(POSITION, scriptName);
        editor.apply();
    }

    public static String getScript(Context context) {
        SharedPreferences pref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return pref.getString(POSITION, "empty");
    }


}
