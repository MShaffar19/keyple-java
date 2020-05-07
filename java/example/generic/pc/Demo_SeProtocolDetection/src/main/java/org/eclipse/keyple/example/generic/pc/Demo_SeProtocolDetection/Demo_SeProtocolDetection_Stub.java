/********************************************************************************
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information regarding copyright
 * ownership.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
package org.eclipse.keyple.example.generic.pc.Demo_SeProtocolDetection;


import java.util.EnumSet;
import org.eclipse.keyple.core.seproxy.ReaderPlugin;
import org.eclipse.keyple.core.seproxy.SeProxyService;
import org.eclipse.keyple.core.seproxy.exception.KeyplePluginInstantiationException;
import org.eclipse.keyple.core.seproxy.exception.KeyplePluginNotFoundException;
import org.eclipse.keyple.core.seproxy.exception.KeypleReaderNotFoundException;
import org.eclipse.keyple.core.seproxy.protocol.SeCommonProtocols;
import org.eclipse.keyple.example.common.generic.SeProtocolDetectionEngine;
import org.eclipse.keyple.example.common.generic.stub.StubMifareClassic;
import org.eclipse.keyple.example.common.generic.stub.StubMifareDesfire;
import org.eclipse.keyple.example.common.generic.stub.StubMifareUL;
import org.eclipse.keyple.plugin.stub.StubPlugin;
import org.eclipse.keyple.plugin.stub.StubPluginFactory;
import org.eclipse.keyple.plugin.stub.StubProtocolSetting;
import org.eclipse.keyple.plugin.stub.StubReader;

/**
 * This class handles the reader events generated by the SeProxyService
 */
public class

Demo_SeProtocolDetection_Stub {

    public Demo_SeProtocolDetection_Stub() {
        super();
    }

    /**
     * Application entry
     *
     * @param args the program arguments
     * @throws IllegalArgumentException in case of a bad argument
     * @throws InterruptedException if thread error occurs
     */
    public static void main(String[] args) throws InterruptedException,
            KeyplePluginNotFoundException, KeyplePluginInstantiationException {
        /* get the SeProxyService instance */
        SeProxyService seProxyService = SeProxyService.getInstance();

        final String STUB_PLUGIN_NAME = "stub1";

        /* Register Stub plugin in the platform */
        seProxyService.registerPlugin(new StubPluginFactory(STUB_PLUGIN_NAME));
        ReaderPlugin stubPlugin = seProxyService.getPlugin(STUB_PLUGIN_NAME);

        /* create an observer class to handle the SE operations */
        SeProtocolDetectionEngine observer = new SeProtocolDetectionEngine();

        /*
         * Plug PO reader.
         */
        ((StubPlugin) stubPlugin).plugStubReader("poReader", true);

        Thread.sleep(200);

        StubReader poReader = null;
        try {
            poReader = (StubReader) (stubPlugin.getReader("poReader"));
        } catch (KeypleReaderNotFoundException e) {
            e.printStackTrace();
        }

        observer.setReader(poReader);

        // Protocol detection settings.
        // add 8 expected protocols with three different methods:
        // - using a custom enumset
        // - adding protocols individually
        // A real application should use only one method.

        // Method 1
        // add several settings at once with setting an enumset
        poReader.setSeProtocolSetting(StubProtocolSetting.getSpecificSettings(EnumSet.of(
                SeCommonProtocols.PROTOCOL_MIFARE_CLASSIC, SeCommonProtocols.PROTOCOL_MIFARE_UL)));

        // Method 2
        // append protocols individually
        // no change
        poReader.addSeProtocolSetting(SeCommonProtocols.PROTOCOL_MEMORY_ST25,
                StubProtocolSetting.STUB_PROTOCOL_SETTING
                        .get(SeCommonProtocols.PROTOCOL_MEMORY_ST25));

        // regex extended
        poReader.addSeProtocolSetting(SeCommonProtocols.PROTOCOL_ISO14443_4,
                StubProtocolSetting.STUB_PROTOCOL_SETTING.get(SeCommonProtocols.PROTOCOL_ISO14443_4)
                        + "|3B8D.*");

        // Set terminal as Observer of the first reader
        poReader.addObserver(observer);

        // poReader.insertSe(new StubCalypsoClassic());
        //
        // Thread.sleep(300);
        //
        // poReader.removeSe();

        // Thread.sleep(100);
        //
        // poReader.insertSe(new StubCalypsoBPrime());

        Thread.sleep(300);

        poReader.removeSe();

        Thread.sleep(100);

        poReader.insertSe(new StubMifareClassic());

        Thread.sleep(300);

        poReader.removeSe();

        Thread.sleep(100);

        /* insert Mifare UltraLight */
        poReader.insertSe(new StubMifareUL());

        Thread.sleep(300);

        poReader.removeSe();

        Thread.sleep(100);

        /* insert Mifare Desfire */
        poReader.insertSe(new StubMifareDesfire());

        Thread.sleep(300);

        poReader.removeSe();

        Thread.sleep(100);



        System.exit(0);
    }
}
