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
package org.assertj.tests.core.internal.strings;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.error.ShouldNotContainCharSequence.shouldNotContain;
import static org.assertj.core.internal.ErrorMessages.arrayOfValuesToLookForIsNull;
import static org.assertj.tests.core.testkit.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import org.assertj.core.api.comparisonstrategy.StandardComparisonStrategy;
import org.assertj.tests.core.internal.StringsBaseTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author Alex Ruiz
 */
class Strings_assertDoesNotContain_Test extends StringsBaseTest {

  @Test
  void should_fail_if_actual_contains_any_of_values() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> strings.assertDoesNotContain(someInfo(), "Yoda", "oda"))
                                                   .withMessage(shouldNotContain("Yoda", "oda",
                                                                                 StandardComparisonStrategy.instance()).create());
  }

  @Test
  void should_throw_error_if_list_of_values_is_null() {
    assertThatNullPointerException().isThrownBy(() -> {
      String[] expected = null;
      strings.assertDoesNotContain(someInfo(), "Yoda", expected);
    }).withMessage(arrayOfValuesToLookForIsNull());
  }

  @Test
  void should_throw_error_if_given_value_is_null() {
    assertThatNullPointerException().isThrownBy(() -> {
      String expected = null;
      strings.assertDoesNotContain(someInfo(), "Yoda", expected);
    }).withMessage("The char sequence to look for should not be null");
  }

  @Test
  void should_throw_error_if_any_element_of_values_is_null() {
    String[] values = { "author", null };
    assertThatNullPointerException().isThrownBy(() -> strings.assertDoesNotContain(someInfo(), "Yoda", values))
                                    .withMessage("Expecting CharSequence elements not to be null but found one at index 1");
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> strings.assertDoesNotContain(someInfo(), null, "Yoda"))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_actual_does_not_contain_sequence() {
    strings.assertDoesNotContain(someInfo(), "Yoda", "Lu");
  }

  @Test
  void should_pass_if_actual_does_not_contain_sequence_according_to_custom_comparison_strategy() {
    stringsWithCaseInsensitiveComparisonStrategy.assertDoesNotContain(someInfo(), "Yoda", "Lu");
  }

  @Test
  void should_fail_if_actual_does_not_contain_sequence_according_to_custom_comparison_strategy() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> stringsWithCaseInsensitiveComparisonStrategy.assertDoesNotContain(someInfo(),
                                                                                                                                       "Yoda",
                                                                                                                                       "yoda"))
                                                   .withMessage(shouldNotContain("Yoda", "yoda", comparisonStrategy).create());
  }

  @Test
  void should_pass_if_actual_does_not_contain_all_of_given_values() {
    String[] values = { "practice", "made", "good" };
    strings.assertDoesNotContain(someInfo(), "Practice makes perfect", values);
  }

  @Test
  void should_fail_if_actual_contains_any_of_given_values() {
    String[] values = { "practice", "make", "good" };
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> strings.assertDoesNotContain(someInfo(),
                                                                                                  "Practice makes perfect",
                                                                                                  values))
                                                   .withMessage(shouldNotContain("Practice makes perfect", values, Set.of("make"),
                                                                                 StandardComparisonStrategy.instance()).create()
                                                                                                                       .formatted());
  }

  @Test
  void should_pass_if_actual_does_not_contain_all_of_given_values_according_to_custom_comparison_strategy() {
    String[] values = { "p1ractice", "made", "good" };
    stringsWithCaseInsensitiveComparisonStrategy.assertDoesNotContain(someInfo(), "Practice makes perfect", values);
  }

  @Test
  void should_fail_if_actual_contains_any_of_given_values_according_to_custom_comparison_strategy() {
    String[] values = { "practice", "made", "good" };
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> stringsWithCaseInsensitiveComparisonStrategy.assertDoesNotContain(someInfo(),
                                                                                                                                       "Practice makes perfect",
                                                                                                                                       values))
                                                   .withMessage(shouldNotContain("Practice makes perfect", values,
                                                                                 Set.of("practice"), comparisonStrategy).create()
                                                                                                                        .formatted());
  }

}
