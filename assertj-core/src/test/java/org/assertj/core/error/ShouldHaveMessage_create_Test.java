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
package org.assertj.core.error;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldHaveMessage.shouldHaveMessage;
import static org.assertj.core.util.Throwables.getStackTrace;

import org.assertj.core.internal.TestDescription;
import org.junit.jupiter.api.Test;

class ShouldHaveMessage_create_Test {

  @Test
  void should_create_error_message() {
    // GIVEN
    Exception cause = new Exception("cause");
    RuntimeException actual = new RuntimeException("error message", cause);
    // WHEN
    String errorMessage = shouldHaveMessage(actual, "expected error message").create(new TestDescription("TEST"));
    // THEN
    then(errorMessage).isEqualTo("[TEST] %n" +
                                 "Expecting message to be:%n" +
                                 "  \"expected error message\"%n" +
                                 "but was:%n" +
                                 "  \"error message\"%n" +
                                 "%n" +
                                 "Throwable that failed the check:%n" +
                                 "%n%s",
                                 getStackTrace(actual));
  }

  @Test
  void should_create_error_message_escaping_percent() {
    // GIVEN
    Throwable actual = new RuntimeException("%3A");
    // WHEN
    String errorMessage = shouldHaveMessage(actual, "expected error message").create(new TestDescription("TEST"));
    // THEN
    then(errorMessage).isEqualTo("[TEST] %n" +
                                 "Expecting message to be:%n" +
                                 "  \"expected error message\"%n" +
                                 "but was:%n" +
                                 "  \"%%3A\"%n" +
                                 "%n" +
                                 "Throwable that failed the check:%n" +
                                 "%n%s",
                                 getStackTrace(actual));
  }

}
