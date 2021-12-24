package com.oowanghan.rpc.transport.common.handler.channel;

/**
 * @Author WangHan
 * @Create 2021/11/23 1:05 上午
 */
public abstract class StatusChannel implements BaseChannel {

    private static final int ACTIVE = 1;
    private static final int CLOSING = 2;
    private volatile int flag;

    @Override
    public boolean isConnected() {
        return (flag & ACTIVE) == ACTIVE;
    }

    public void closing() {
        flag = CLOSING;
    }

    public void active() {
        flag = ACTIVE;
    }
}
