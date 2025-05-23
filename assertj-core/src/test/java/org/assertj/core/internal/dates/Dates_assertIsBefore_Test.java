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
package org.assertj.core.internal.dates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldBeBefore.shouldBeBefore;
import static org.assertj.core.internal.ErrorMessages.dateToCompareActualWithIsNull;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Dates;
import org.assertj.core.internal.DatesBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Dates#assertIsBefore(AssertionInfo, Date, Date)}</code>.
 * 
 * @author Joel Costigliola
 */
class Dates_assertIsBefore_Test extends DatesBaseTest {

  @Test
  void should_fail_if_actual_is_not_strictly_before_given_date() {
    AssertionInfo info = someInfo();
    Date other = parseDate("2000-01-01");

    Throwable error = catchThrowable(() -> dates.assertIsBefore(info, actual, other));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeBefore(actual, other));
  }

  @Test
  void should_fail_if_actual_is_equals_to_given_date() {
    AssertionInfo info = someInfo();
    Date other = parseDate("2011-01-01");

    Throwable error = catchThrowable(() -> dates.assertIsBefore(info, actual, other));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeBefore(actual, other));
  }

  @Test
  void should_throw_error_if_given_date_is_null() {
    assertThatNullPointerException().isThrownBy(() -> dates.assertIsBefore(someInfo(), actual, null))
                                    .withMessage(dateToCompareActualWithIsNull());
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> dates.assertIsBefore(someInfo(), null,
                                                                                          parseDate("2010-01-01")))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_actual_is_strictly_before_given_date() {
    dates.assertIsBefore(someInfo(), actual, parseDate("2020-01-01"));
  }

  @Test
  void should_fail_if_actual_is_not_strictly_before_given_date_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Date other = parseDate("2000-01-01");

    Throwable error = catchThrowable(() -> datesWithCustomComparisonStrategy.assertIsBefore(info, actual, other));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeBefore(actual, other, yearAndMonthComparisonStrategy));
  }

  @Test
  void should_fail_if_actual_is_equals_to_given_date_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Date other = parseDate("2011-01-31");

    Throwable error = catchThrowable(() -> datesWithCustomComparisonStrategy.assertIsBefore(info, actual, other));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeBefore(actual, other, yearAndMonthComparisonStrategy));
  }

  @Test
  void should_throw_error_if_given_date_is_null_whatever_custom_comparison_strategy_is() {
    assertThatNullPointerException().isThrownBy(() -> datesWithCustomComparisonStrategy.assertIsBefore(someInfo(),
                                                                                                       actual, null))
                                    .withMessage(dateToCompareActualWithIsNull());
  }

  @Test
  void should_fail_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> datesWithCustomComparisonStrategy.assertIsBefore(someInfo(),
                                                                                                                      null,
                                                                                                                      parseDate("2010-01-01")))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_actual_is_strictly_before_given_date_according_to_custom_comparison_strategy() {
    datesWithCustomComparisonStrategy.assertIsBefore(someInfo(), actual, parseDate("2020-01-01"));
  }

}
