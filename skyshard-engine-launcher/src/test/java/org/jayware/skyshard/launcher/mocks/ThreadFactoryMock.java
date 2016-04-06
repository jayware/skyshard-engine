package org.jayware.skyshard.launcher.mocks;


import java.util.concurrent.ThreadFactory;


public class ThreadFactoryMock
implements ThreadFactory
{
    @Override
    public Thread newThread(Runnable r)
    {
        return null;
    }
}
