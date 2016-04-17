package org.jayware.skyshard.util;


/**
 * Static convenience methods that help a method or constructor checkArgument whether it was invoked correctly.
 * <p>
 * <b>Note:</b> These methods are based on methods of the {@link com.google.common.base.Preconditions Google Guava Preconditions}
 * class.
 */
public class Preconditions
{
    @FunctionalInterface
    public interface Precondition
    {
        boolean check();
    }

    /**
     * Ensures that the specified {@link Precondition} is met.
     * <p>
     * If the passed {@link Precondition} returns <code>false</code> an {@link IllegalArgumentException} is thrown.
     *
     * @param precondition a {@link Precondition}
     *
     *
     * @throws IllegalArgumentException if the specified {@link Precondition} returns false.
     */
    public static void checkArgument(Precondition precondition)
    {
        if (!precondition.check())
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures that the specified {@link Precondition} is met.
     * <p>
     * If the passed {@link Precondition} returns <code>false</code> an {@link IllegalStateException} is thrown.
     *
     * @param precondition a {@link Precondition}
     *
     *
     * @throws IllegalArgumentException if the specified {@link Precondition} returns false.
     */
    public static void checkState(Precondition precondition)
    {
        if (!precondition.check())
        {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * <p>
     * <b>Note:</b> This operation is based on the {@link com.google.common.base.Preconditions#checkNotNull(Object)
     * Google Guavas Preconditions},
     * but instead of throwing a {@link NullPointerException} when the passed reference is null, this operation
     * throws an {@link IllegalArgumentException}.
     *
     * @param reference an object reference
     * @param <T> the object's type.
     *
     * @return the non-null reference that was validated
     *
     * @throws IllegalArgumentException if reference is <code>null</code>
     */
    public static <T> T checkNotNull(T reference)
    {
        if (reference == null)
        {
            throw new IllegalArgumentException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * <p>
     * <b>Note:</b> This operation is based on the {@link com.google.common.base.Preconditions#checkNotNull(Object, Object)
     * Google Guavas Preconditions},
     * but instead of throwing a {@link NullPointerException} when the passed reference is null, this operation
     * throws an {@link IllegalArgumentException}.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the checkArgument fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @param <T> the object's type.
     * @return the non-null reference that was validated
     *
     * @throws IllegalArgumentException if reference is <code>null</code>
     */
    public static <T> T checkNotNull(T reference, Object errorMessage)
    {
        if (reference == null)
        {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures that a {@link String} passed as a parameter to the calling method is not empty.
     * <p>
     * <b>Note:</b> This operation is based on the {@link com.google.common.base.Preconditions#checkNotNull(Object)
     * Google Guavas Preconditions},
     * but instead of throwing a {@link NullPointerException} when the passed reference is null, this operation
     * throws an {@link IllegalArgumentException}.
     *
     * @param string a {@link String}
     *
     * @return the {@link String} that was validated
     *
     * @throws IllegalArgumentException if {@code string} is null
     */
    public static String checkStringNotEmpty(String string)
    {
        if (string == null || string.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        return string;
    }
}
