/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2025 the original author or authors.
 */
package org.assertj.core.util;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

import org.assertj.core.api.filter.FilterOperator;

/**
 * Verifies correct argument values and state. Borrowed from Guava.
 * 
 * @author alruiz@google.com (Alex Ruiz)
 */
public final class Preconditions {
  public static final String ARGUMENT_EMPTY = "Argument expected not to be empty!";

  /**
   * Verifies that the given {@code CharSequence} is not {@code null} or empty.
   * 
   * @param s the given {@code CharSequence}.
   * @return the validated {@code CharSequence}.
   * @throws NullPointerException if the given {@code CharSequence} is {@code null}.
   * @throws IllegalArgumentException if the given {@code CharSequence} is empty.
   */
  public static CharSequence checkNotNullOrEmpty(CharSequence s) {
    return checkNotNullOrEmpty(s, ARGUMENT_EMPTY);
  }

  /**
   * Verifies that the given {@code CharSequence} is not {@code null} or empty.
   * 
   * @param s the given {@code CharSequence}.
   * @param message error message in case of empty {@code String}.
   * @return the validated {@code CharSequence}.
   * @throws NullPointerException if the given {@code CharSequence} is {@code null}.
   * @throws IllegalArgumentException if the given {@code CharSequence} is empty.
   */
  public static CharSequence checkNotNullOrEmpty(CharSequence s, String message) {
    requireNonNull(s, message);
    if (s.length() == 0) throwExceptionForBeingEmpty(message);
    return s;
  }

  /**
   * Verifies that the given array is not {@code null} or empty.
   * 
   * @param <T> the type of elements of the array.
   * @param array the given array.
   * @return the validated array.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   */
  public static <T> T[] checkNotNullOrEmpty(T[] array) {
    T[] checked = requireNonNull(array);
    if (checked.length == 0) throwExceptionForBeingEmpty();
    return checked;
  }

  /**
   * Verifies that the given {@link FilterOperator} reference is not {@code null}.
   * 
   * @param <T> the type of the FilterOperator to check.
   * @param filterOperator the given {@link FilterOperator} reference.
   * @throws IllegalArgumentException if the given {@link FilterOperator} reference is {@code null}.
   */
  public static <T> void checkNotNull(FilterOperator<T> filterOperator) {
    checkArgument(filterOperator != null, "The expected value should not be null.%n"
                                          + "If you were trying to filter on a null value, please use filteredOnNull(String propertyOrFieldName) instead");
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the calling method.
   * <p>
   * Borrowed from Guava.
   *
   * @param expression a boolean expression
   * @param errorMessageTemplate a template for the exception message should the check fail. The
   *     message is formed by calling {@link String#format(String, Object...)} with the given parameters.
   * @param errorMessageArgs the arguments to be substituted into the message template.
   * @throws IllegalArgumentException if {@code expression} is false
   * @throws NullPointerException if the check fails and either {@code errorMessageTemplate} or
   *     {@code errorMessageArgs} is null (don't let this happen)
   */
  public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
    if (!expression) throw new IllegalArgumentException(errorMessageTemplate.formatted(errorMessageArgs));
  }

  /**
   * Ensures the truth of an expression using a supplier to fetch the error message in case of a failure.
   *
   * @param expression a boolean expression
   * @param errorMessage a supplier to build the error message in case of failure. Must not be null.
   * @throws IllegalArgumentException if {@code expression} is false
   * @throws NullPointerException if the check fails and {@code errorMessage} is null (don't let this happen).
   */
  public static void checkArgument(boolean expression, Supplier<String> errorMessage) {
    if (!expression) throw new IllegalArgumentException(errorMessage.get());
  }

  /**
   * Ensures the truth of an expression involving the state of the calling instance, but not
   * involving any parameters to the calling method.
   * <p>
   * Borrowed from Guava.
   *
   * @param expression a boolean expression
   * @param errorMessageTemplate a template for the exception message should the check fail.The
   *     message is formed by calling {@link String#format(String, Object...)} with the given parameters.
   * @param errorMessageArgs the arguments to be substituted into the message template. Arguments
   *     are converted to strings using {@link String#valueOf(Object)}.
   * @throws IllegalStateException if {@code expression} is false
   * @throws NullPointerException if the check fails and either {@code errorMessageTemplate} or
   *     {@code errorMessageArgs} is null (don't let this happen)
   */
  public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
    if (!expression) {
      throw new IllegalStateException(errorMessageTemplate.formatted(errorMessageArgs));
    }
  }

  private Preconditions() {}

  private static void throwExceptionForBeingEmpty() {
    throwExceptionForBeingEmpty(ARGUMENT_EMPTY);
  }

  private static void throwExceptionForBeingEmpty(String message) {
    throw new IllegalArgumentException(message);
  }
}
