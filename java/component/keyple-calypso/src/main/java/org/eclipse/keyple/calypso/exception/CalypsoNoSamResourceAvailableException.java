/* **************************************************************************************
 * Copyright (c) 2020 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.eclipse.keyple.calypso.exception;

import org.eclipse.keyple.core.service.exception.KeypleException;

/**
 * Indicates that there are no SAM resources available.
 *
 * @since 0.9
 */
public class CalypsoNoSamResourceAvailableException extends KeypleException {

  /**
   * @param message the message to identify the exception context
   * @since 0.9
   */
  public CalypsoNoSamResourceAvailableException(String message) {
    super(message);
  }
}
