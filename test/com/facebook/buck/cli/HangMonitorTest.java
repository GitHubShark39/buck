/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.facebook.buck.cli;

import static org.junit.Assert.assertThat;

import com.facebook.buck.util.concurrent.TimeSpan;
import com.google.common.base.Function;
import com.google.common.util.concurrent.SettableFuture;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

public class HangMonitorTest {

  @Test
  public void reportContainsCurrentThread() throws Exception {
    Thread sleepingThread = new Thread("testThread") {
      @Override
      public void run() {
        hangForHangMonitorTestReport();
      }
      private void hangForHangMonitorTestReport() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // Ignore.
        }
      }
    };
    sleepingThread.start();
    final SettableFuture<String> result = SettableFuture.create();
    HangMonitor hangMonitor = new HangMonitor(
        new Function<String, Void>() {
          @Nullable
          @Override
          public Void apply(String input) {
            result.set(input);
            return null;
          }
        },
        new TimeSpan(10, TimeUnit.MILLISECONDS));
    hangMonitor.runOneIteration();
    assertThat(result.isDone(), Matchers.is(true));
    String report = result.get();
    assertThat(report, Matchers.containsString("hangForHangMonitorTestReport"));
  }

  @Test
  public void eventBusEventsSuppressReport() throws Exception {
    final AtomicBoolean didGetReport = new AtomicBoolean(false);
    HangMonitor hangMonitor = new HangMonitor(
        new Function<String, Void>() {
          @Nullable
          @Override
          public Void apply(String input) {
            didGetReport.set(true);
            return null;
          }
        },
        new TimeSpan(10, TimeUnit.MILLISECONDS));
    hangMonitor.onAnything(new Object());
    hangMonitor.runOneIteration();
    assertThat(didGetReport.get(), Matchers.is(false));
  }
}
