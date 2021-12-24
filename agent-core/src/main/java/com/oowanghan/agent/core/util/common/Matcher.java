package com.oowanghan.agent.core.util.common;

/**
 * 匹配器
 * @Author WangHan
 * @Create 6:26 下午 2021/9/12
 */
public interface Matcher {

    /**
     * 匹配是否为匹配类
     * @return
     * @param descriptor
     */
    boolean isMatcherBeanByAnnotation(String descriptor);
}
