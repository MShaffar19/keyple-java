/*
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License version 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 */

package org.eclipse.keyple.plugin.remote_se.rse;

import org.eclipse.keyple.plugin.remote_se.transport.TransportNode;
import org.eclipse.keyple.seproxy.ReaderPlugin;
import org.eclipse.keyple.seproxy.SeProxyService;

import java.util.SortedSet;
import java.util.TreeSet;

public class VirtualSeRemoteService {

    private TransportNode node;

    public VirtualSeRemoteService() {
    }

    public void bindTransportNode(TransportNode node) {
        this.node = node;
    }

    public void bindPlugin(RsePlugin plugin) {
        SortedSet<ReaderPlugin> plugins = new TreeSet<ReaderPlugin>();
        plugins.add(plugin);
        SeProxyService.getInstance().setPlugins(plugins);
        this.node.setDtoReceiver(plugin);
    }


    //manage session


}
