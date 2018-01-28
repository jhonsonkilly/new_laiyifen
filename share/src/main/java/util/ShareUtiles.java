package util;

import android.content.Context;
import android.net.Uri;

import com.ody.p2p.utils.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by pzy on 2017/2/22.
 */
public class ShareUtiles {

    /**
     * 读文件（ShareSDK.xml）获取第三方平台配置
     * @param context
     * @param platform  平台
     * @param Key       键
     * @return
     */
    public static String getFromAssets(Context context,String platform,final String Key){
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open("ShareSDK.xml") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            boolean flage=false;
            while((line = bufReader.readLine()) != null){
                if (line.contains(platform)){
                    flage=true;
                }
                if (flage&&line.contains(Key)){
                    Result=Uri.parse(line.replace(" ", "").replace("\"","")).getQueryParameter(Key);
                    return StringUtils.isEmpty(Result)? "":Result;
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
