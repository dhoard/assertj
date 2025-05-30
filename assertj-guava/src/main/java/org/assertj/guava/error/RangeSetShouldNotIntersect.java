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
package org.assertj.guava.error;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

/**
 * @author Ilya Koshaleu
 */
public class RangeSetShouldNotIntersect extends BasicErrorMessageFactory {

  public static ErrorMessageFactory shouldNotIntersect(Object actual, Object unexpected, Iterable<?> intersected) {
    return new RangeSetShouldNotIntersect(actual, unexpected, intersected);
  }

  /**
   * Creates a new <code>{@link BasicErrorMessageFactory}</code>.
   * 
   * @param actual actual {@code RangeSet}.
   * @param unexpected ranges that should not be intersected.
   * @param intersected list of ranges that haven't be intersected, but they have.
   */
  private RangeSetShouldNotIntersect(Object actual, Object unexpected, Object intersected) {
    super("%nExpecting:%n  %s%nnot to intersect%n  %s%nbut it intersects%n  %s%n",
          actual, unexpected, intersected);
  }

}
