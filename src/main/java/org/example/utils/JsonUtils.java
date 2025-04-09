package org.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.xml.internal.ws.developer.SerializationFeature;

public class JsonUtils {

    public static String convertObject2Json(Object obj){
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }
}
