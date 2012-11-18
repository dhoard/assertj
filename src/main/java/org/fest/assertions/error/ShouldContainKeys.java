/*
 * Created on Jun 3, 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2010-2012 the original author or authors.
 */
package org.fest.assertions.error;

/**
 * Creates an error message indicating that an assertion that verifies a map contains some keys failed. TODO : move to
 * fest-assert-core
 * 
 * @author Joel Costigliola
 */
public class ShouldContainKeys extends BasicErrorMessageFactory {

  /**
   * Creates a new </code>{@link ShouldContainKeys}</code>.
   * 
   * @param actual the actual value in the failed assertion.
   * @return the created {@code ErrorMessageFactory}.
   */
  public static ErrorMessageFactory shouldContainKeys(Object actual, Object... keys) {
    return keys.length == 1 ? new ShouldContainKeys(actual, keys[0]) : new ShouldContainKeys(actual, keys);
  }

  private ShouldContainKeys(Object actual, Object key) {
    super("expecting:\n<%s>\n to contain key:\n<%s>", actual, key);
  }

  private ShouldContainKeys(Object actual, Object... keys) {
    super("expecting:\n<%s>\n to contain keys:\n<%s>", actual, keys);
  }
}
