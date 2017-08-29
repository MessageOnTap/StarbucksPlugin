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
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.cmu.chimps.messageontap_api.JSONUtils;
import edu.cmu.chimps.messageontap_api.MessageOnTapPlugin;
import edu.cmu.chimps.messageontap_api.MethodConstants;
import edu.cmu.chimps.messageontap_api.ParseTree;
import edu.cmu.chimps.messageontap_api.SemanticTemplate;
import edu.cmu.chimps.messageontap_api.ServiceAttributes;
import edu.cmu.chimps.messageontap_api.Tag;


public class StarbucksPlugin extends MessageOnTapPlugin{

    public static final String TAG = "StarbucksPlugin";
    private Long mTidShowBubble;
    public static final String SEMANTIC_TEMPLATE_COFFEE = "coffee";

    @Override
    protected Set<SemanticTemplate> semanticTemplates() {
        Set<SemanticTemplate> templates = new HashSet<>();

        Set<Tag> tags = new HashSet<>();
        Set<String> reSet = new HashSet<>();
        reSet.add("coffee");
        reSet.add("cafe");
        reSet.add("starbucks");
        reSet.add("cappuccino");
        reSet.add("latte");
        reSet.add("frappuccino");

        tags.add(new Tag(ServiceAttributes.Internal.TAG_TIME,
                new HashSet<String>(), Tag.Type.MANDATORY));
        tags.add(new Tag("tag_coffee", reSet, Tag.Type.MANDATORY));
        templates.add(new SemanticTemplate().name(SEMANTIC_TEMPLATE_COFFEE)
                .tags(tags)
                .direction(ParseTree.Direction.INCOMING));

        return templates;
    }

    @Override
    protected void initNewSession(long sid, HashMap<String, Object> params) throws Exception {
        Log.e(TAG, "Session created here!");
        Log.e(TAG, JSONUtils.hashMapToString(params));
        //Log.e(TAG, "parse tree: " + ((ParseTree) JSONUtils.jsonToSimpleObject((String) params.get("tree"), JSONUtils.TYPE_PARSE_TREE)).toString());
        params.put(ServiceAttributes.UI.BUBBLE_FIRST_LINE, "Starbucks Plugin");
        params.put(ServiceAttributes.UI.BUBBLE_SECOND_LINE,"Order Coffee?");
        params.put(ServiceAttributes.UI.ICON_TYPE_STRING, getResources().getString(R.string.fa_coffee));

        // TID is something we might need to implement stateflow inside a plugin.
        mTidShowBubble = createTask(sid, MethodConstants.UI_TYPE, MethodConstants.UI_METHOD_SHOW_BUBBLE, params);
    }

    @Override
    protected void newTaskResponded(long sid, long tid, HashMap<String, Object> params) throws Exception {
        //request script
        //Perform action script
        Log.e(TAG, "Got task response!");
        Log.e(TAG, JSONUtils.hashMapToString(params));
        if (tid == mTidShowBubble) {
            Log.e(TAG, "TID is right " );
            if (params.get(ServiceAttributes.UI.STATUS).equals(ServiceAttributes.UI.Status.CLICKED)) {
                Log.e(TAG, "button clicked");
                Intent sugiliteIntent = new Intent("edu.cmu.hcii.sugilite.COMMUNICATION");
                sugiliteIntent.addCategory("android.intent.category.DEFAULT");
                sugiliteIntent.putExtra("messageType", "RUN_SCRIPT");
                String scriptName = ScriptStorage.getScript(StarbucksPlugin.this);
                if (!scriptName.isEmpty()) {
                    sugiliteIntent.putExtra("arg1", scriptName);
                    sugiliteIntent.putExtra("arg2", "Run Script Complete");
                    startActivity(sugiliteIntent);}
                endSession(sid);
                Log.e(TAG, "Ending session " + sid);
                Log.e(TAG, "Action officially run" + sid);
            }
        }else{
            Log.e(TAG, "Ending session " + sid);
            endSession(sid);
            Log.e(TAG, "Session ended");
        }
    }


}
