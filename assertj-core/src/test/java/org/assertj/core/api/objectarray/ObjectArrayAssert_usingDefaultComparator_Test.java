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
package org.assertj.core.api.objectarray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.testkit.AlwaysEqualComparator.alwaysEqual;

import java.util.Comparator;

import org.assertj.core.api.ObjectArrayAssert;
import org.assertj.core.api.ObjectArrayAssertBaseTest;
import org.assertj.core.internal.ObjectArrays;
import org.assertj.core.internal.Objects;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests for <code>{@link ObjectArrayAssert#usingDefaultComparator()}</code>.
 *
 * @author Joel Costigliola
 * @author Mikhail Mazursky
 */
class ObjectArrayAssert_usingDefaultComparator_Test extends ObjectArrayAssertBaseTest {

  private Comparator<Object[]> comparator = alwaysEqual();

  private ObjectArrays arraysBefore;

  @BeforeEach
  void before() {
    arraysBefore = getArrays(assertions);
    assertions.usingComparator(comparator);
  }

  @Override
  protected ObjectArrayAssert<Object> invoke_api_method() {
    return assertions.usingDefaultComparator();
  }

  @Override
  protected void verify_internal_effects() {
    assertThat(getArrays(assertions).getComparator()).isNull();
    assertThat(getObjects(assertions)).isSameAs(Objects.instance());
    assertThat(getArrays(assertions)).isSameAs(arraysBefore);
  }
}
