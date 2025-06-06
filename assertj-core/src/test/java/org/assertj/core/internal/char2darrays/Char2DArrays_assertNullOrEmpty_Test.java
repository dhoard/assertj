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
package org.assertj.core.internal.char2darrays;

import static org.assertj.core.testkit.TestData.someInfo;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Char2DArrays;
import org.assertj.core.internal.Char2DArraysBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Char2DArrays#assertNullOrEmpty(AssertionInfo, char[][])}</code>.
 *
 * @author Maciej Wajcht
 */
class Char2DArrays_assertNullOrEmpty_Test extends Char2DArraysBaseTest {

  @Test
  void should_delegate_to_Arrays2D() {
    // WHEN
    char2DArrays.assertNullOrEmpty(someInfo(), actual);
    // THEN
    verify(arrays2d).assertNullOrEmpty(info, failures, actual);
  }
}
