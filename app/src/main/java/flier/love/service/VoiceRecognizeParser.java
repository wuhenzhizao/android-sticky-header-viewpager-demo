package flier.love.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by wuhenzhizao on 2015/3/18.
 */
public class VoiceRecognizeParser {

    public static StringBuilder parseRecognizedResult(StringBuilder content, String result){
        try {
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
            boolean isLast = jsonObject.getBoolean("ls");
            JSONArray wsJA = jsonObject.getJSONArray("ws");
            for (int i = 0; i < wsJA.length(); i++){
                JSONArray cwJA = wsJA.getJSONObject(i).getJSONArray("cw");
                for (int j = 0; j < cwJA.length(); j++){
                    content.append(cwJA.getJSONObject(j).getString("w"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return content;
    }
}
