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
package org.assertj.core.api.future;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class CompletableFutureAssert_succeedsWithin_Test extends AbstractFutureTest {

  @Test
  void should_allow_assertion_on_future_result_when_completed_normally() {
    // GIVEN
    String value = "done";
    CompletableFuture<String> future = completedFuture(value);
    // WHEN/THEN
    assertThat(future).succeedsWithin(1, MILLISECONDS)
                      .isEqualTo(value);
  }

  @Test
  void should_allow_assertion_on_future_result_when_completed_normally_within_timeout() {
    // GIVEN
    String value = "done";
    int sleepDuration = 10;
    CompletableFuture<String> future = completedFutureAfter(value, sleepDuration, executorService);
    // WHEN/THEN
    // using the same duration would fail depending on when the thread executing the future is started
    assertThat(future).succeedsWithin(sleepDuration + 100, MILLISECONDS)
                      .isEqualTo(value);
  }

  @Test
  void should_allow_narrowed_assertion_on_future_result() {
    // GIVEN
    String value = "done";
    CompletableFuture<String> future = completedFuture(value);
    // WHEN/THEN
    assertThat(future).succeedsWithin(1, MILLISECONDS, as(STRING))
                      .startsWith("don");
  }

  @Test
  void should_fail_if_completable_future_does_not_succeed_within_given_timeout() {
    // GIVEN
    int sleepDuration = 100000;
    CompletableFuture<String> future = completedFutureAfter("ook!", sleepDuration, executorService);
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(future).succeedsWithin(10, MILLISECONDS));
    // THEN
    then(assertionError).hasMessageStartingWith(format("%nExpecting%n" +
                                                       "  <CompletableFuture[Incomplete]>%n" +
                                                       "to be completed within 10L Millis.%n"));
  }

  @Test
  void should_fail_if_completable_future_is_cancelled() {
    // GIVEN
    CompletableFuture<String> future = new CompletableFuture<>();
    future.cancel(false);
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(future).succeedsWithin(1, MILLISECONDS));
    // THEN
    then(assertionError).hasMessageStartingWith(format("%nExpecting%n" +
                                                       "  <CompletableFuture[Cancelled]>%n" +
                                                       "to be completed within 1L Millis.%n" +
                                                       "%n" +
                                                       "exception caught while trying to get the future result: java.util.concurrent.CancellationException"));
  }

  @Test
  void should_fail_when_completable_future_is_null() {
    // GIVEN
    CompletableFuture<String> future = null;
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(future).succeedsWithin(1, MILLISECONDS));
    // THEN
    then(assertionError).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_completable_future_is_completed_exceptionally() {
    // GIVEN
    CompletableFuture<String> future = new CompletableFuture<>();
    future.completeExceptionally(new RuntimeException("boom%s%n"));
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(future).succeedsWithin(1, MILLISECONDS));
    // THEN
    then(assertionError).hasMessageStartingWith("%nExpecting%n  <CompletableFuture[Failed with the following stack trace:%njava.lang.RuntimeException: boom%%s%%n".formatted())
                        .hasMessageContaining("to be completed within 1L Millis.");
  }
}
