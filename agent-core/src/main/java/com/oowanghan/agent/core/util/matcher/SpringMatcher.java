package com.oowanghan.agent.core.util.matcher;

import com.oowanghan.agent.core.util.common.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author WangHan
 * @Create 2021/9/12 6:26 下午
 */
public class SpringMatcher implements Matcher {

   public static final Logger log = LoggerFactory.getLogger(SpringMatcher.class);

    public static final String CONTROLLER = "Controller";
    public static final String SERVICE = "Service";
    public static final String REPOSITORY = "Repository";
    public static final String RESOURCE = "Deprecated";

    @Override
    public boolean isMatch(String descriptor) {
        boolean isMatcher = descriptor.contains(CONTROLLER) || descriptor.contains(SERVICE) ||
                descriptor.contains(REPOSITORY) || descriptor.contains(RESOURCE);
       log.info("[trace-info] match annotation : {} - {}", descriptor, isMatcher);
        return isMatcher;
    }
}
