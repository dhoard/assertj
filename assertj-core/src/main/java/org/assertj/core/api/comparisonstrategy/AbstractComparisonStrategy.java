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
package org.assertj.core.api.comparisonstrategy;

import static java.lang.reflect.Array.getLength;
import static java.util.Collections.EMPTY_SET;
import static org.assertj.core.util.IterableUtil.isNullOrEmpty;

import java.lang.reflect.Array;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Base implementation of {@link ComparisonStrategy} contract.
 *
 * @author Joel Costigliola
 */
public abstract class AbstractComparisonStrategy implements ComparisonStrategy {

  @Override
  public Iterable<?> duplicatesFrom(Iterable<?> iterable) {
    if (isNullOrEmpty(iterable)) return EMPTY_SET;

    Set<Object> noDuplicates = newSetUsingComparisonStrategy();
    Set<Object> duplicatesWithOrderPreserved = new LinkedHashSet<>();
    for (Object element : iterable) {
      if (noDuplicates.contains(element)) {
        duplicatesWithOrderPreserved.add(element);
      } else {
        noDuplicates.add(element);
      }
    }
    return duplicatesWithOrderPreserved;
  }

  /**
   * Returns a {@link Set} honoring the comparison strategy used.
   *
   * @return a {@link Set} honoring the comparison strategy used.
   */
  protected abstract Set<Object> newSetUsingComparisonStrategy();

  @Override
  public boolean arrayContains(Object array, Object value) {
    for (int i = 0; i < getLength(array); i++) {
      Object element = Array.get(array, i);
      if (areEqual(element, value)) return true;
    }
    return false;
  }

  @Override
  public boolean isLessThan(Object actual, Object other) {
    return !isGreaterThanOrEqualTo(actual, other);
  }

  @Override
  public boolean isLessThanOrEqualTo(Object actual, Object other) {
    return !isGreaterThan(actual, other);
  }

  @Override
  public boolean isGreaterThanOrEqualTo(Object actual, Object other) {
    return areEqual(actual, other) || isGreaterThan(actual, other);
  }

  @Override
  public boolean isStandard() {
    return false;
  }

}
