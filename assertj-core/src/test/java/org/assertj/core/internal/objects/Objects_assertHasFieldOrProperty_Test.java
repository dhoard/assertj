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
package org.assertj.core.internal.objects;

import static org.assertj.core.api.Assertions.catchIllegalArgumentException;
import static org.assertj.core.api.Assertions.catchRuntimeException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldHavePropertyOrField.shouldHavePropertyOrField;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.ObjectsBaseTest;
import org.junit.jupiter.api.Test;

class Objects_assertHasFieldOrProperty_Test extends ObjectsBaseTest {

  protected static final AssertionInfo INFO = someInfo();

  @Test
  void should_pass_if_actual_has_expected_field() {
    // GIVEN
    Object actual = new Data();
    // WHEN/THEN
    objects.assertHasFieldOrProperty(INFO, actual, "field1");
  }

  @Test
  void should_pass_if_actual_has_expected_property_not_backed_by_field() {
    // GIVEN
    Object actual = new Data();
    // WHEN/THEN
    objects.assertHasFieldOrProperty(INFO, actual, "field3");
  }

  @Test
  void should_fail_if_actual_is_null() {
    // GIVEN
    Object actual = null;
    // WHEN
    AssertionError error = expectAssertionError(() -> objects.assertHasFieldOrProperty(INFO, actual, "field1"));
    // THEN
    then(error).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_field_or_property_does_not_exists() {
    // GIVEN
    Object actual = new Data();
    String fieldName = "unknown_field";
    // WHEN
    AssertionError error = expectAssertionError(() -> objects.assertHasFieldOrProperty(INFO, actual, fieldName));
    // THEN
    then(error).hasMessage(shouldHavePropertyOrField(actual, fieldName).create());
  }

  @Test
  void should_fail_if_field_exists_but_is_static() {
    // GIVEN
    Object actual = new Data();
    String fieldName = "staticField";
    // WHEN
    AssertionError error = expectAssertionError(() -> objects.assertHasFieldOrProperty(INFO, actual, fieldName));
    // THEN
    then(error).hasMessage(shouldHavePropertyOrField(actual, fieldName).create());
  }

  @Test
  void should_fail_if_given_field_or_property_name_is_null() {
    // GIVEN
    Object actual = new Data();
    String fieldName = null;
    // WHEN
    RuntimeException exception = catchIllegalArgumentException(() -> objects.assertHasFieldOrProperty(INFO, actual, fieldName));
    // THEN
    then(exception).hasMessage("The name of the property/field to read should not be null");
  }

  @Test
  void should_use_field_if_getters_throws_exception() {
    // GIVEN
    Object actual = new Data();
    String fieldName = "fieldWithGetterThrowing";
    // WHEN/THEN
    objects.assertHasFieldOrProperty(INFO, actual, fieldName);
  }

  @Test
  void should_rethrow_getter_exception_if_field_is_missing() {
    // GIVEN
    Object actual = new Data();
    String fieldName = "unknownFieldWithGetterThrowing";
    // WHEN
    RuntimeException exception = catchRuntimeException(() -> objects.assertHasFieldOrProperty(INFO, actual, fieldName));
    // THEN
    then(exception).hasMessage("some dummy exception");
  }

  @SuppressWarnings("unused")
  private static class Data {

    private Object field1;
    private Object field2;
    private Object fieldWithGetterThrowing;
    private static Object staticField;

    @Override
    public String toString() {
      return "data";
    }

    public Object getField3() {
      return null;
    }

    public Object getUnknownFieldWithGetterThrowing() {
      throw new RuntimeException("some dummy exception");
    }

    public Object getFieldWithGetterThrowing() {
      throw new RuntimeException("some dummy exception");
    }
  }

}
