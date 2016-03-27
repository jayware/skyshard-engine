/**
 * Skyshard Engine -- A 3D game engine written in Java
 *
 * Copyright (C) 2016 Markus Neubauer <markus.neubauer@jayware.org>,
 *                    Alexander Haumann <alexander.haumann@jayware.org>,
 *                    Manuel Hinke <manuel.hinke@jayware.org>,
 *                    Marina Schilling <marina.schilling@jayware.org>,
 *                    Elmar Schug <elmar.schug@jayware.org>,
 *
 *     This file is part of the Skyshard Engine.
 *
 *     The Skyshard Engine is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public License
 *     as published by the Free Software Foundation, either version 3 of
 *     the License, or any later version.
 *
 *     The Skyshard Engine is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jayware.skyshard.math.api;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class DefaultTransform
        implements Transform
{
    private static long NEXT_ID = 1;

    private Vector4f translation;
    private Quaternion4f rotation;

    private double roll, pitch, yaw;

    private Lock lock = new ReentrantLock();

    public DefaultTransform()
    {
        translation = new DefaultVector4f();

        rotation = new DefaultQuaternion4f();
        rotation.setIdentity();
    }

    @Override
    public Quaternion4f getRotation()
    {
        // TODO: Maybe incorrect! pitch, roll, yaw are interchange to get the right rotation!
        double p = (MathUtil.inRadians(-yaw) * 0.5);
        double y = (MathUtil.inRadians(-roll) * 0.5);
        double r = (MathUtil.inRadians(pitch) * 0.5);

        double sinp = sin(p);
        double siny = sin(y);
        double sinr = sin(r);
        double cosp = cos(p);
        double cosy = cos(y);
        double cosr = cos(r);

        lock.lock();
        try
        {
            rotation.set((float) (sinr * cosp * cosy - cosr * sinp * siny),
                    (float) (cosr * sinp * cosy + sinr * cosp * siny),
                    (float) (cosr * cosp * siny - sinr * sinp * cosy),
                    (float) (cosr * cosp * cosy + sinr * sinp * siny));

            rotation.normalize();
            return rotation;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public Vector4f getTranslation()
    {
        lock.lock();
        try
        {
            return translation;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void rotate(float pitch, float yaw, float roll)
    {
        lock.lock();
        try
        {
            this.pitch += pitch;
            this.yaw += yaw;
            this.roll += roll;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void move(float x, float y, float z)
    {
        lock.lock();
        try
        {
            translation.set(translation.getX() + x, translation.getY() + y, translation.getZ() + z, translation.getW());
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public float getPitch()
    {
        lock.lock();
        try
        {
            return (float) pitch;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void setPitch(float pitch)
    {
        lock.lock();
        try
        {
            this.pitch = pitch;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public float getRoll()
    {
        lock.lock();
        try
        {
            return (float) roll;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void setRoll(float roll)
    {
        lock.lock();
        try
        {
            this.roll = roll;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public float getYaw()
    {
        lock.lock();
        try
        {
            return (float) yaw;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void setYaw(float yaw)
    {
        lock.lock();
        try
        {
            this.yaw = yaw;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public float getX()
    {
        lock.lock();
        try
        {
            return translation.getX();
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void setX(float x)
    {
        lock.lock();
        try
        {
            translation.setX(x);
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public float getY()
    {
        lock.lock();
        try
        {
            return translation.getY();
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void setY(float y)
    {
        lock.lock();
        try
        {
            translation.setY(y);
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public float getZ()
    {
        lock.lock();
        try
        {
            return translation.getZ();
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void setZ(float z)
    {
        lock.lock();
        try
        {
            translation.setZ(z);
        }
        finally
        {
            lock.unlock();
        }
    }
}
