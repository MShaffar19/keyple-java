/********************************************************************************
 * Copyright (c) 2019 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information regarding copyright
 * ownership.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
package org.eclipse.keyple.core.seproxy.plugin.state;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.eclipse.keyple.core.seproxy.exception.KeypleIOReaderException;
import org.eclipse.keyple.core.seproxy.plugin.AbstractObservableLocalReader;
import org.eclipse.keyple.core.seproxy.plugin.SmartInsertionReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadedWaitForSeInsertion extends DefaultWaitForSeInsertion {

    private Future<Boolean> waitForCarPresent;
    private final long timeout;
    private final ExecutorService executor;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ThreadedWaitForSeInsertion.class);

    public ThreadedWaitForSeInsertion(AbstractObservableLocalReader reader, long timeout,
            ExecutorService executor) {
        super(reader);
        this.timeout = timeout;
        this.executor = executor;

    }

    @Override
    public void activate() {
        logger.trace("[{}] Activate => ThreadedWaitForSeInsertion", reader.getName());
        waitForCarPresent = executor.submit(waitForCardPresent(this.timeout));
        // logger.debug("End of activate currentState {} ",this.currentState);

    }

    private Callable<Boolean> waitForCardPresent(final long timeout) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                logger.trace("[{}] Invoke waitForCardPresent asynchronously", reader.getName());
                try {
                    if (((SmartInsertionReader) reader).waitForCardPresent(timeout)) {
                        onEvent(AbstractObservableLocalReader.InternalEvent.SE_INSERTED);
                        return true;
                    }
                } catch (KeypleIOReaderException e) {
                    logger.trace(
                            "[{}] waitForCardPresent => Error while polling card with waitForCardPresent",
                            reader.getName());
                    onEvent(AbstractObservableLocalReader.InternalEvent.STOP_DETECT);
                }
                onEvent(AbstractObservableLocalReader.InternalEvent.TIME_OUT);
                return false;
            }
        };
    }


    @Override
    public void deActivate() {
        logger.trace("[{}] deActivate => ThreadedWaitForSeInsertion", reader.getName());
        if (waitForCarPresent != null && !waitForCarPresent.isDone()) {
            waitForCarPresent.cancel(true);
        }
    }


}
