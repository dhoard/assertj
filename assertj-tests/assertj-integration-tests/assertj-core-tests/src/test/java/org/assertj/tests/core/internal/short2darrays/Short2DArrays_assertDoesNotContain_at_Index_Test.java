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
package org.assertj.tests.core.internal.short2darrays;

import static org.assertj.core.data.Index.atIndex;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

/**
 * @author Maciej Wajcht
 */
class Short2DArrays_assertDoesNotContain_at_Index_Test extends Short2DArraysBaseTest {

  @Test
  void should_delegate_to_Arrays2D() {
    // GIVEN
    short[] shorts = new short[] { 0, 2, 4 };
    // WHEN
    short2DArrays.assertDoesNotContain(info, actual, shorts, atIndex(1));
    // THEN
    verify(arrays2d).assertDoesNotContain(info, failures, actual, shorts, atIndex(1));
  }
}
