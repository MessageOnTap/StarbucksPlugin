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


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
public class StarbucksIntent extends PreferenceActivity {
    public static String scriptResult;
    public final static String TAG = "StarbucksIntent";
    public static String scriptName;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent sugiliteIntent = new Intent("edu.cmu.hcii.sugilite.COMMUNICATION");
        sugiliteIntent.addCategory("android.intent.category.DEFAULT");
        sugiliteIntent.putExtra("messageType", "RUN_SCRIPT");
        if (scriptName != null) {
            sugiliteIntent.putExtra("arg1", scriptName);
            sugiliteIntent.putExtra("arg2", "Run Script Complete");
            startActivityForResult(sugiliteIntent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            Bundle bundle = data.getExtras();
            scriptResult = bundle.getString("messageType");
            Log.e(TAG, "onActivityResult: " + scriptResult);

        }
    }

}
