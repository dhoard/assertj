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

/**
 * Creates an error message indicating that an assertion that verifies {@link CharSequence}
 * is not blank.
 */
public class ShouldNotContainOnlyWhitespaces extends BasicErrorMessageFactory {

  /**
   * Creates a new <code>{@link ShouldNotContainOnlyWhitespaces}</code>.
   * @param actual the actual value in the failed assertion.
   * @return the created {@code ErrorMessageFactory}.
   */
  public static ErrorMessageFactory shouldNotContainOnlyWhitespaces(CharSequence actual) {
    return new ShouldNotContainOnlyWhitespaces(actual);
  }

  private ShouldNotContainOnlyWhitespaces(Object actual) {
    super("%n" +
          "Expecting string not to contain only whitespaces but was:%n" +
          "  %s", actual);
  }
}
