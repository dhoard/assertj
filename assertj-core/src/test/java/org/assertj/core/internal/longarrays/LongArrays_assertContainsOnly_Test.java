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
package org.assertj.core.internal.longarrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldContainOnly.shouldContainOnly;
import static org.assertj.core.internal.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.testkit.LongArrays.arrayOf;
import static org.assertj.core.testkit.LongArrays.emptyArray;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.LongArrays;
import org.assertj.core.internal.LongArraysBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link LongArrays#assertContainsOnly(AssertionInfo, long[], long[])}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
class LongArrays_assertContainsOnly_Test extends LongArraysBaseTest {

  @Test
  void should_pass_if_actual_contains_given_values_only() {
    arrays.assertContainsOnly(someInfo(), actual, arrayOf(6L, 8L, 10L));
  }

  @Test
  void should_pass_if_actual_contains_given_values_only_in_different_order() {
    arrays.assertContainsOnly(someInfo(), actual, arrayOf(10L, 8L, 6L));
  }

  @Test
  void should_pass_if_actual_contains_given_values_only_more_than_once() {
    actual = arrayOf(6L, 8L, 10L, 8L, 8L, 8L);
    arrays.assertContainsOnly(someInfo(), actual, arrayOf(6L, 8L, 10L));
  }

  @Test
  void should_pass_if_actual_contains_given_values_only_even_if_duplicated() {
    arrays.assertContainsOnly(someInfo(), actual, arrayOf(6L, 8L, 10L, 6L, 8L, 10L));
  }

  @Test
  void should_pass_if_actual_and_given_values_are_empty() {
    actual = emptyArray();
    arrays.assertContainsOnly(someInfo(), actual, emptyArray());
  }

  @Test
  void should_fail_if_array_of_values_to_look_for_is_empty_and_actual_is_not() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> arrays.assertContainsOnly(someInfo(), actual, emptyArray()));
  }

  @Test
  void should_throw_error_if_array_of_values_to_look_for_is_null() {
    assertThatNullPointerException().isThrownBy(() -> arrays.assertContainsOnly(someInfo(), actual, null))
                                    .withMessage(valuesToLookForIsNull());
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> arrays.assertContainsOnly(someInfo(), null, arrayOf(8L)))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_fail_if_actual_does_not_contain_given_values_only() {
    AssertionInfo info = someInfo();
    long[] expected = { 6L, 8L, 20L };

    Throwable error = catchThrowable(() -> arrays.assertContainsOnly(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainOnly(actual, expected, newArrayList(20L), newArrayList(10L)));
  }

  @Test
  void should_pass_if_actual_contains_given_values_only_according_to_custom_comparison_strategy() {
    arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(), actual, arrayOf(6L, -8L, 10L));
  }

  @Test
  void should_pass_if_actual_contains_given_values_only_in_different_order_according_to_custom_comparison_strategy() {
    arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(), actual, arrayOf(10L, -8L, 6L));
  }

  @Test
  void should_pass_if_actual_contains_given_values_only_more_than_once_according_to_custom_comparison_strategy() {
    actual = arrayOf(6L, -8L, 10L, -8L, -8L, -8L);
    arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(), actual, arrayOf(6L, -8L, 10L));
  }

  @Test
  void should_pass_if_actual_contains_given_values_only_even_if_duplicated_according_to_custom_comparison_strategy() {
    arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(), actual, arrayOf(6L, -8L, 10L, 6L, -8L, 10L));
  }

  @Test
  void should_fail_if_array_of_values_to_look_for_is_empty_and_actual_is_not_whatever_custom_comparison_strategy_is() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(),
                                                                                                                           actual,
                                                                                                                           emptyArray()));
  }

  @Test
  void should_throw_error_if_array_of_values_to_look_for_is_null_whatever_custom_comparison_strategy_is() {
    assertThatNullPointerException().isThrownBy(() -> arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(),
                                                                                                            actual,
                                                                                                            null))
                                    .withMessage(valuesToLookForIsNull());
  }

  @Test
  void should_fail_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(),
                                                                                                                           null,
                                                                                                                           arrayOf(-8L)))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_fail_if_actual_does_not_contain_given_values_only_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    long[] expected = { 6L, -8L, 20L };

    Throwable error = catchThrowable(() -> arraysWithCustomComparisonStrategy.assertContainsOnly(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info,
                             shouldContainOnly(actual, expected, newArrayList(20L), newArrayList(10L),
                                               absValueComparisonStrategy));
  }
}
