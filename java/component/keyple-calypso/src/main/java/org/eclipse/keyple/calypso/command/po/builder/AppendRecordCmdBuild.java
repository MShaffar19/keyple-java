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
package org.eclipse.keyple.calypso.command.po.builder;

import org.eclipse.keyple.calypso.command.PoClass;
import org.eclipse.keyple.calypso.command.po.*;
import org.eclipse.keyple.calypso.command.po.parser.AppendRecordRespPars;
import org.eclipse.keyple.core.card.message.ApduResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builds the Append Record APDU command.
 *
 * @since 0.9
 */
public final class AppendRecordCmdBuild extends AbstractPoCommandBuilder<AppendRecordRespPars> {

  private static final Logger logger = LoggerFactory.getLogger(AppendRecordCmdBuild.class);

  /** The command. */
  private static final CalypsoPoCommand command = CalypsoPoCommand.APPEND_RECORD;

  /* Construction arguments */
  private final int sfi;
  private final byte[] data;

  /**
   * Instantiates a new UpdateRecordCmdBuild.
   *
   * @param poClass indicates which CLA byte should be used for the Apdu
   * @param sfi the sfi to select
   * @param newRecordData the new record data to write
   * @throws IllegalArgumentException - if the command is inconsistent
   * @since 0.9
   */
  public AppendRecordCmdBuild(PoClass poClass, byte sfi, byte[] newRecordData) {
    super(command, null);
    byte cla = poClass.getValue();

    this.sfi = sfi;
    this.data = newRecordData;

    byte p1 = (byte) 0x00;
    byte p2 = (sfi == 0) ? (byte) 0x00 : (byte) (sfi * 8);

    this.request = setApduRequest(cla, command, p1, p2, newRecordData, null);

    if (logger.isDebugEnabled()) {
      String extraInfo = String.format("SFI=%02X", sfi);
      this.addSubName(extraInfo);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.9
   */
  @Override
  public AppendRecordRespPars createResponseParser(ApduResponse apduResponse) {
    return new AppendRecordRespPars(apduResponse, this);
  }

  /**
   * {@inheritDoc}
   *
   * <p>This command modified the contents of the PO and therefore uses the session buffer.
   *
   * @return true
   * @since 0.9
   */
  @Override
  public boolean isSessionBufferUsed() {
    return true;
  }

  /**
   * @return the SFI of the accessed file
   * @since 0.9
   */
  public int getSfi() {
    return sfi;
  }

  /**
   * @return the data sent to the PO
   * @since 0.9
   */
  public byte[] getData() {
    return data;
  }
}
