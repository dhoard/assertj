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
package org.assertj.core.internal.iterables;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldBeNullOrEmpty.shouldBeNullOrEmpty;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Iterables;
import org.assertj.core.internal.IterablesBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Iterables#assertNullOrEmpty(AssertionInfo, Collection)}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class Iterables_assertNullOrEmpty_Test extends IterablesBaseTest {

  @Test
  void should_pass_if_actual_is_null() {
    iterables.assertNullOrEmpty(someInfo(), null);
  }

  @Test
  void should_pass_if_actual_is_empty() {
    iterables.assertNullOrEmpty(someInfo(), emptyList());
  }

  @Test
  void should_fail_if_actual_has_elements() {
    AssertionInfo info = someInfo();
    Collection<String> actual = newArrayList("Yoda");

    Throwable error = catchThrowable(() -> iterables.assertNullOrEmpty(info, actual));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeNullOrEmpty(actual));
  }

  @Test
  void should_pass_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    iterablesWithCaseInsensitiveComparisonStrategy.assertNullOrEmpty(someInfo(), null);
  }

  @Test
  void should_pass_if_actual_is_empty_whatever_custom_comparison_strategy_is() {
    iterablesWithCaseInsensitiveComparisonStrategy.assertNullOrEmpty(someInfo(), emptyList());
  }

  @Test
  void should_fail_if_actual_has_elements_whatever_custom_comparison_strategy_is() {
    AssertionInfo info = someInfo();
    Collection<String> actual = newArrayList("Yoda");

    Throwable error = catchThrowable(() -> iterablesWithCaseInsensitiveComparisonStrategy.assertNullOrEmpty(info, actual));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeNullOrEmpty(actual));
  }
}
