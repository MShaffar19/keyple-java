/*
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License version 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 */

package org.eclipse.keyple.calypso.command.po.parser;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.keyple.command.AbstractApduResponseParser;
import org.eclipse.keyple.seproxy.ApduResponse;
import org.eclipse.keyple.seproxy.SeResponse;
import org.eclipse.keyple.seproxy.SeResponseSet;
import org.eclipse.keyple.util.ByteBufferUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UpdateRecordRespParsTest {

    @Test
    public void updateRecordRespPars() {
        List<ApduResponse> responses = new ArrayList<ApduResponse>();
        ApduResponse apduResponse = new ApduResponse(ByteBuffer.wrap(new byte[] {90, 00}), null);
        responses.add(apduResponse);
        SeResponseSet seResponse = new SeResponseSet(new SeResponse(true, null,
                new ApduResponse(ByteBufferUtils.fromHex("9000"), null), responses));

        AbstractApduResponseParser apduResponseParser =
                new UpdateRecordRespPars(seResponse.getSingleResponse().getApduResponses().get(0));
        Assert.assertArrayEquals(new byte[] {90, 0},
                ByteBufferUtils.toBytes(apduResponseParser.getApduResponse().getBytes()));
    }
}