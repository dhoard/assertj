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
import static org.assertj.core.error.ShouldBeInSameHour.shouldBeInSameHour;
import static org.assertj.core.internal.ErrorMessages.dateToCompareActualWithIsNull;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Dates;
import org.assertj.core.internal.DatesBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Dates#assertIsInSameHourAs(AssertionInfo, Date, Date)}</code>.
 *
 * @author Joel Costigliola
 */
class Dates_assertIsInSameHourAs_Test extends DatesBaseTest {

  @Override
  protected void initActualDate() {
    actual = parseDatetime("2011-01-01T03:01:02");
  }

  @Test
  void should_fail_if_actual_is_not_in_same_hour_as_given_date() {
    AssertionInfo info = someInfo();
    Date other = parseDatetime("2011-01-01T04:01:02");

    Throwable error = catchThrowable(() -> dates.assertIsInSameHourAs(info, actual, other));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeInSameHour(actual, other));
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> dates.assertIsInSameHourAs(someInfo(), null, new Date()))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_throw_error_if_given_date_is_null() {
    assertThatNullPointerException().isThrownBy(() -> dates.assertIsInSameHourAs(someInfo(), actual, null))
                                    .withMessage(dateToCompareActualWithIsNull());
  }

  @Test
  void should_pass_if_actual_is_in_same_hour_as_given_date() {
    dates.assertIsInSameHourAs(someInfo(), actual, parseDatetime("2011-01-01T03:59:02"));
    dates.assertIsEqualWithPrecision(someInfo(), actual, parseDatetime("2011-01-01T03:59:02"), TimeUnit.HOURS);
  }

  @Test
  void should_fail_if_actual_is_not_in_same_hour_as_given_date_whatever_custom_comparison_strategy_is() {
    AssertionInfo info = someInfo();
    Date other = parseDatetime("2011-01-01T04:01:02");

    Throwable error = catchThrowable(() -> datesWithCustomComparisonStrategy.assertIsInSameHourAs(info, actual, other));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeInSameHour(actual, other));
  }

  @Test
  void should_fail_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> datesWithCustomComparisonStrategy.assertIsInSameHourAs(someInfo(),
                                                                                                                            null,
                                                                                                                            new Date()))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_throw_error_if_given_date_is_null_whatever_custom_comparison_strategy_is() {
    assertThatNullPointerException().isThrownBy(() -> datesWithCustomComparisonStrategy.assertIsInSameHourAs(someInfo(),
                                                                                                             actual,
                                                                                                             null))
                                    .withMessage(dateToCompareActualWithIsNull());
  }

  @Test
  void should_pass_if_actual_is_in_same_hour_as_given_date_whatever_custom_comparison_strategy_is() {
    datesWithCustomComparisonStrategy.assertIsInSameHourAs(someInfo(), actual, parseDatetime("2011-01-01T03:59:02"));
  }

}
