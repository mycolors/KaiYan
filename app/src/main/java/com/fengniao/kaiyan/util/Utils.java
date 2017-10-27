package com.fengniao.kaiyan.util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

public class Utils {

    //获取deviceId
    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String getDeviceId(Activity activity) {
        return ((TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceSoftwareVersion();
    }


    public static String deleteHeader(String code) {
        int start = code.indexOf("<header>");
        int end = code.indexOf("</header>");
        if (start != 0 && end != 0 && start < end) {
            //从起始位置到终止位置，并不包含终止位置
            String content = code.substring(start, end + 9);
            code = code.replace(content, "");
        }
        return code;
    }


    public static String formatYueArticlePageCode(String code) {
        code = code.replace("<header>\n" +
                "    <h1 class=\"logo\"><img src=\"http://yue.fm/images/logo.png\" alt=\"yue\" /></h1>\n" +
                "    <nav>\n" +
                "        <ul>\n" +
                "            <li><a href=\"/\">下载 app</a></li>\n" +
                "            <li><a href=\"/recruit\">创作人招募</a></li>\n" +
                "        </ul>\n" +
                "    </nav>\n" +
                "</header>", "");
        code = code.replace("<div class=\"notice\">\n" +
                "        <img class=\"icon\" src=\"http://yue.fm/images/icon-app.png\" alt=\"YUE\" />\n" +
                "        <div class=\"text\">\n" +
                "            <p>YUE － 现在开始阅读</p>\n" +
                "            <a class=\"download\" href=\"/\">下载 APP</a>\n" +
                "        </div>\n" +
                "    </div>", "");
        code = code.replace("<footer>\n" +
                "    <p class=\"copy\">&copy; 2017 YUE   <a href=\"http://www.miibeian.gov.cn\">沪ICP备16023457号</a></p>\n" +
                "                </footer>", "");
        String assetsFontCSS = "<link href=\"file:///android_asset/myfont.css\" rel=\"stylesheet\" type=\"text/css\"/>";
        String assetsBodyCSS = "<link href=\"file:///android_asset/body.css\" rel=\"stylesheet\" type=\"text/css\"/>";
        code = code.replace("</head>", assetsFontCSS + "\n" + assetsBodyCSS + "\n" + "</head>");
        return code;
    }
}
