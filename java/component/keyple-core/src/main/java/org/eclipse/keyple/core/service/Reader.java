/* **************************************************************************************
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.eclipse.keyple.core.service;

import org.eclipse.keyple.core.service.exception.KeypleReaderIOException;
import org.eclipse.keyple.core.service.exception.KeypleReaderProtocolNotSupportedException;

/**
 * Defines the high level API to access a card reader.
 *
 * <ul>
 *   <li>To retrieve the unique reader name
 *   <li>To check the card presence.
 *   <li>To activate and deactivate the card protocols.
 *   <li>To tell if the communication is contactless or not.
 * </ul>
 *
 * The interface is used by applications processing the card.
 *
 * @since 0.9
 */
public interface Reader {

  /**
   * Gets the name of the reader
   *
   * @return A not empty string.
   * @since 0.9
   */
  String getName();

  /**
   * Checks if is the card present.
   *
   * @return true if a card is present in the reader
   * @throws KeypleReaderIOException if the communication with the reader or the card has failed
   * @throws IllegalStateException is called when reader is no longer registered
   * @since 0.9
   */
  boolean isCardPresent();

  /**
   * Activates the provided card protocol and assigns it a name.
   *
   * <ul>
   *   <li>Activates the detection of cards using this protocol (if the plugin allows it).
   *   <li>Asks the plugin to take this protocol into account if a card using this protocol is
   *       identified during the selection phase.
   *   <li>Internally associates the two strings provided as arguments.
   *   <li>The #readerProtocolName argument is the name of the protocol among those supported by the
   *       reader.
   *   <li>The #applicationProtocolName is the name of the protocol to be the plugin when a card
   *       using this protocol is detected.
   * </ul>
   *
   * Note: in the case where multiple protocols are activated, they will be checked in the selection
   * process in the order in which they were activated. The most likely cases should therefore be
   * activated first.
   *
   * @param readerProtocolName A not empty String.
   * @param applicationProtocolName A not empty String.
   * @throws KeypleReaderProtocolNotSupportedException if the protocol is not supported.
   * @throws IllegalStateException is called when reader is no longer registered
   * @since 1.0
   */
  void activateProtocol(String readerProtocolName, String applicationProtocolName);

  /**
   * Deactivates the provided card protocol.
   *
   * <ul>
   *   <li>Inhibits the detection of cards using this protocol.
   *   <li>Ask the plugin to ignore this protocol if a card using this protocol is identified during
   *       the selection phase.
   * </ul>
   *
   * @param readerProtocolName A not empty String.
   * @throws KeypleReaderProtocolNotSupportedException if the protocol is not supported.
   * @throws IllegalStateException is called when reader is no longer registered
   * @since 1.0
   */
  void deactivateProtocol(String readerProtocolName);

  /**
   * Tells if the current card communication is contactless.
   *
   * @return True if the communication is contactless, false if not.
   * @throws IllegalStateException is called when reader is no longer registered
   * @since 1.0
   */
  boolean isContactless();
}
