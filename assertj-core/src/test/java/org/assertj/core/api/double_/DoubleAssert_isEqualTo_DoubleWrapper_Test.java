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
package org.assertj.core.api.double_;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.DoubleAssertBaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DoubleAssert_isEqualTo_DoubleWrapper_Test extends DoubleAssertBaseTest {

  @Override
  protected DoubleAssert invoke_api_method() {
    return assertions.isEqualTo(Double.valueOf(getActual(assertions)));
  }

  @Override
  protected void verify_internal_effects() {
    verify(objects).assertEqual(getInfo(assertions), getActual(assertions), Double.valueOf(getActual(assertions)));
    verifyNoInteractions(doubles);
    verifyNoInteractions(comparables);
  }

  @ParameterizedTest
  @CsvSource({ "1.0d, 1.0d", "0.0d, 0.0d" })
  void should_pass_using_primitive_comparison(Double actual, Double expected) {
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_fail_when_comparing_negative_zero_to_positive_zero() {
    // GIVEN
    final Double positiveZero = 0.0d;
    final double negativeZero = -0.0d;
    // THEN
    expectAssertionError(() -> assertThat(negativeZero).isEqualTo(positiveZero));
  }

  @Test
  void should_honor_user_specified_comparator() {
    // GIVEN
    final Double one = 1.0d;
    // THEN
    assertThat(-one).usingComparator(ALWAY_EQUAL_DOUBLE)
                    .isEqualTo(one);
  }

}
