package org.jayware.skyshard.launcher.mocks;


import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.util.Map;

import static org.mockito.Mockito.mock;


public class FrameworkFactoryMock
implements FrameworkFactory
{
    @Override
    public Framework newFramework(Map<String, String> configuration)
    {
        return mock(Framework.class);
    }
}
