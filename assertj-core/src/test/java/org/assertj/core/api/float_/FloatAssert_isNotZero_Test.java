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
package org.assertj.core.api.float_;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.FloatAssert;
import org.assertj.core.api.FloatAssertBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link FloatAssert#isNotZero()}</code>.
 *
 * @author Alex Ruiz
 */
class FloatAssert_isNotZero_Test extends FloatAssertBaseTest {

  @Override
  protected FloatAssert invoke_api_method() {
    return assertions.isNotZero();
  }

  @Override
  protected void verify_internal_effects() {
    verify(floats).assertIsNotZero(getInfo(assertions), getActual(assertions));
  }

  @Test
  void should_pass_with_Float_negative_zero() {
    // GIVEN
    final Float negativeZero = -0.0f;
    // THEN
    assertThat(negativeZero).isNotZero();
  }

  @Test
  void should_fail_with_primitive_negative_zero() {
    // GIVEN
    final float negativeZero = -0.0f;

    // WHEN
    Throwable error = catchThrowable(() -> assertThat(negativeZero).isNotZero());

    // THEN
    assertThat(error).isInstanceOf(AssertionError.class)
                     .hasMessage("%nExpecting actual:%n  -0.0f%nnot to be equal to:%n  0.0f%n".formatted());
  }

  @Test
  void should_fail_with_primitive_positive_zero() {
    // GIVEN
    final float positiveZero = 0.0f;

    // WHEN
    Throwable error = catchThrowable(() -> assertThat(positiveZero).isNotZero());

    // THEN
    assertThat(error).isInstanceOf(AssertionError.class)
                     .hasMessage("%nExpecting actual:%n  0.0f%nnot to be equal to:%n  0.0f%n".formatted());
  }

  @Test
  void should_fail_with_Float_positive_zero() {
    // GIVEN
    final Float positiveZero = 0.0f;

    // WHEN
    Throwable error = catchThrowable(() -> assertThat(positiveZero).isNotZero());

    // THEN
    assertThat(error).isInstanceOf(AssertionError.class)
                     .hasMessage("%nExpecting actual:%n  0.0f%nnot to be equal to:%n  0.0f%n".formatted());
  }

}
