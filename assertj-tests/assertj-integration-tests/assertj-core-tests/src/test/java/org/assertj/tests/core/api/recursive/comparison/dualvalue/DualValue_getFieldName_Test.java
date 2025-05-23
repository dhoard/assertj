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
package org.assertj.tests.core.api.recursive.comparison.dualvalue;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.Lists.list;

import org.assertj.core.api.recursive.comparison.DualValue;
import org.junit.jupiter.api.Test;

class DualValue_getFieldName_Test {

  @Test
  void should_return_top_level_field_name() {
    // GIVEN
    DualValue dualValue = new DualValue(list("foo"), "", "");
    // WHEN
    String expectedFieldName = dualValue.getFieldName();
    // THEN
    then(expectedFieldName).isEqualTo("foo");
  }

  @Test
  void should_return_nested_field_name() {
    // GIVEN
    DualValue dualValue = new DualValue(list("foo", "bar"), "", "");
    // WHEN
    String expectedFieldName = dualValue.getFieldName();
    // THEN
    then(expectedFieldName).isEqualTo("bar");
  }

  @Test
  void should_return_empty_for_root_objects() {
    // GIVEN
    DualValue dualValue = new DualValue(emptyList(), "", "");
    // WHEN
    String expectedFieldName = dualValue.getFieldName();
    // THEN
    then(expectedFieldName).isEqualTo("");
  }

}
