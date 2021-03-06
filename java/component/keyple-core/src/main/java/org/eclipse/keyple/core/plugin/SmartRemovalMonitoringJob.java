/* **************************************************************************************
 * Copyright (c) 2019 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.eclipse.keyple.core.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Detect the card removal thanks to the method {@link
 * WaitForCardRemovalBlocking#waitForCardAbsentNative()}.
 *
 * <p>This method is invoked in another thread
 *
 * <p>This job should be used by readers who have the ability to natively detect the disappearance
 * of the card during a communication session with an ES (between two APDU exchanges).
 *
 * <p>PC/SC readers have this capability.
 *
 * <p>If the card is removed during processing, then an internal CARD_REMOVED event is triggered.
 *
 * <p>If a communication problem with the reader occurs (KeypleReaderIOException) an internal
 * STOP_DETECT event is fired.
 *
 * <p>All runtime exceptions that may occur during the monitoring process are caught and notified at
 * the application level through the {@link
 * org.eclipse.keyple.core.service.event.ReaderObservationExceptionHandler} mechanism.
 *
 * @since 0.9
 */
class SmartRemovalMonitoringJob extends AbstractMonitoringJob {

  private static final Logger logger = LoggerFactory.getLogger(SmartRemovalMonitoringJob.class);

  private final WaitForCardRemovalBlocking reader;

  public SmartRemovalMonitoringJob(WaitForCardRemovalBlocking reader) {
    this.reader = reader;
  }

  /** (package-private)<br> */
  @Override
  Runnable getMonitoringJob(final AbstractObservableState state) {
    /*
     * Invoke the method WaitForCardRemovalBlocking#waitForCardAbsentNative() in another thread
     */
    return new Runnable() {
      @Override
      public void run() {
        try {
          if (reader.waitForCardAbsentNative()) {
            // timeout is already managed within the task
            state.onEvent(AbstractObservableLocalReader.InternalEvent.CARD_REMOVED);
          } else {
            if (logger.isTraceEnabled()) {
              logger.trace(
                  "[{}] waitForCardAbsentNative => return false, task interrupted",
                  reader.getName());
            }
          }
        } catch (RuntimeException e) {
          ((AbstractObservableLocalReader) reader)
              .getObservationExceptionHandler()
              .onReaderObservationError(
                  ((AbstractReader) reader).getPluginName(), reader.getName(), e);
        }
      }
    };
  }

  /** (package-private)<br> */
  @Override
  void stop() {
    reader.stopWaitForCardRemoval();
  }
}
