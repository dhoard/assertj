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
package org.assertj.core.error.future;

import java.util.concurrent.Future;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

public class ShouldNotBeDone extends BasicErrorMessageFactory {

  private static final String SHOULD_NOT_BE_DONE = "%nExpecting%n  <%s>%nnot to be done.%n" + Warning.WARNING;

  public static ErrorMessageFactory shouldNotBeDone(Future<?> actual) {
    return new ShouldNotBeDone(actual);
  }

  private ShouldNotBeDone(Future<?> actual) {
    super(SHOULD_NOT_BE_DONE, actual);
  }
}
