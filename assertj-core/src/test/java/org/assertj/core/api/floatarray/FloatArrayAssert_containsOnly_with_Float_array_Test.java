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
package org.assertj.core.api.floatarray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.core.testkit.FloatArrays.arrayOf;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.FloatArrayAssert;
import org.assertj.core.api.FloatArrayAssertBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link FloatArrayAssert#containsOnly(Float[])}</code>.
 *
 * @author Omar Morales Ortega
 */
class FloatArrayAssert_containsOnly_with_Float_array_Test extends FloatArrayAssertBaseTest {

  @Test
  void should_fail_if_values_is_null() {
    // GIVEN
    Float[] values = null;
    // WHEN
    Throwable thrown = catchThrowable(() -> assertions.containsOnly(values));
    // THEN
    then(thrown).isInstanceOf(NullPointerException.class)
                .hasMessage(shouldNotBeNull("values").create());
  }

  @Test
  void should_pass_if_values_are_in_range_of_precision() {
    // GIVEN
    Float[] values = new Float[] { 2.0f, 3.0f, 0.9f };
    // WHEN/THEN
    assertThat(arrayOf(1.0f, 2.0f, 3.0f)).containsOnly(values, withPrecision(0.2f));
  }

  @Override
  protected FloatArrayAssert invoke_api_method() {
    return assertions.containsOnly(new Float[] { 1.0f, 2.0f });
  }

  @Override
  protected void verify_internal_effects() {
    verify(arrays).assertContainsOnly(getInfo(assertions), getActual(assertions), arrayOf(1.0f, 2.0f));
  }

}
