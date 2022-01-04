package com.oowanghan.agent.core.upload;

import com.oowanghan.agent.core.entity.MethodMetaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author WangHan
 * @Create 2021/9/13 12:53 上午
 */
public class UploadClient {

   private static final Logger log = LoggerFactory.getLogger(UploadClient.class);

    public static void upload(MethodMetaInfo methodMetaInfo){
        log.info("[trace-info] method info : {}", methodMetaInfo);
    }
}
