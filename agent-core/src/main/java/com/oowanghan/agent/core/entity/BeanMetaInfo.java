package com.oowanghan.agent.core.entity;

import java.util.List;

/**
 * 类元信息
 * @Author WangHan
 * @Create 2021/9/3 12:48 上午
 */
public class BeanMetaInfo {

    private int accessFlag;

    private String beanInternalName;

    private List<String> beanAnnotation;

    private List<String> interfaceName;

    public List<String> getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(List<String> interfaceName) {
        this.interfaceName = interfaceName;
    }

    public int getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(int accessFlag) {
        this.accessFlag = accessFlag;
    }

    public String getBeanInternalName() {
        return beanInternalName;
    }

    public void setBeanInternalName(String beanInternalName) {
        this.beanInternalName = beanInternalName;
    }

    public List<String> getBeanAnnotation() {
        return beanAnnotation;
    }

    public void setBeanAnnotation(List<String> beanAnnotation) {
        this.beanAnnotation = beanAnnotation;
    }

    @Override
    public String toString() {
        return "BeanMetaInfo{" +
                "accessFlag=" + accessFlag +
                ", beanInternalName='" + beanInternalName + '\'' +
                ", beanAnnotation=" + beanAnnotation +
                '}';
    }
}
