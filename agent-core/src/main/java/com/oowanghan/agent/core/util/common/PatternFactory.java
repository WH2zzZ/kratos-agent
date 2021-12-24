package com.oowanghan.agent.core.util.common;

import com.oowanghan.agent.core.constant.PatternConstant;
import com.oowanghan.agent.core.util.spring.SpringMatcher;

/**
 * 模式工厂类
 * @Author WangHan
 * @Create 2021/9/12 6:24 下午
 */
public class PatternFactory {

    private static SpringMatcher springMatcher = new SpringMatcher();

    public static final SpringMatcher getMatcher(String pattern){
        switch (pattern){
            case PatternConstant.SPRING:
            default:
                return springMatcher;
        }
    }
}
