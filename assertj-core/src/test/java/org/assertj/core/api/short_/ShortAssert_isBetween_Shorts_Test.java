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
package org.assertj.core.api.short_;

import static org.mockito.Mockito.verify;

import org.assertj.core.api.ShortAssert;
import org.assertj.core.api.ShortAssertBaseTest;

/**
 * Tests for <code>{@link ShortAssert#isBetween(Short, Short)}</code>.
 * 
 * @author William Delanoue
 */
class ShortAssert_isBetween_Shorts_Test extends ShortAssertBaseTest {

  @Override
  protected ShortAssert invoke_api_method() {
    return assertions.isBetween((short) 6, (short) 8);
  }

  @Override
  protected void verify_internal_effects() {
    verify(shorts).assertIsBetween(getInfo(assertions), getActual(assertions), (short) 6, (short) 8);
  }
}
