package org.terracotta;

import org.terracotta.util.jmx.SimpleListener;

import javax.management.Notification;

/**
 * Created by vkatson
 * Date: 06.02.2009
 * Time: 12:30:36
 */
public class MyListener extends SimpleListener {
    @Override
    public void handleEvent(Notification arg0, Object arg1) {
        System.out.println("Got event: " + arg0);
    }

    @Override
    public void initialClusterMembers(Object[] arg0) {
        System.out.print("Current cluster members are: [");
        for (int i = 0; i < arg0.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print("'" + arg0[i] + "'");
        }
        System.out.println("]");
    }

    @Override
    public void thisNodeConnected(Object arg0) {
        System.out.println("This node connected: " + arg0);
    }

    @Override
    public void thisNodeDisconnected(Object arg0) {
        System.out.println("This node disconnected: " + arg0);
    }

    @Override
    public void nodeConnected(Object arg0) {
        System.out.println("Node connected: " + arg0);
    }

    @Override
    public void nodeDisconnected(Object arg0) {
        System.out.println("Node disconnected: " + arg0);
    }
}
