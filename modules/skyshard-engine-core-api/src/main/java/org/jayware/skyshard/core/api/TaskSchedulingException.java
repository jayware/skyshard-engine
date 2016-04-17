package org.jayware.skyshard.core.api;

public class TaskSchedulingException
extends RuntimeException
{
    public TaskSchedulingException(Task task)
    {
        super();
    }

    public TaskSchedulingException(Task task, String message)
    {
        super(message);
    }

    public TaskSchedulingException(Task task, Throwable throwable)
    {
        super(throwable);
    }

    public TaskSchedulingException(Task task, String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
