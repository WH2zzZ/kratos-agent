package com.oowanghan.agent.core.util.matcher;

import com.oowanghan.agent.core.util.common.Matcher;

/**
 * @Author WangHan
 * @Create 2022/1/4 11:48 下午
 */
public class BeanNameMatcher implements Matcher {

    @Override
    public boolean isMatch(String descriptor) {
        return descriptor.contains(descriptor);
    }
}
