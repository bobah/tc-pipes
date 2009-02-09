package org.terracotta;

import org.terracotta.jmx.util.events.ClusterEvents;

import javax.management.MalformedObjectNameException;

/**
 * Created by vkatson
 * Date: 05.02.2009
 * Time: 13:01:24
 */
public class App {
    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException {

        //Creating listener istance.
        ClusterEvents.ClusterListener listener = new ClusterEvents.ClusterListener();
        //Registering listner.
        ClusterEvents.registerListener(listener);
        //Now we're waiting for listener registration completion. Here the program hangs up.
        //If we skip this step listener.getMyNodeId() would return null istead of nodeID.
        listener.waitForRegistration();

        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println("Node ID: " + listener.getMyNodeId());
        System.out.println("+++++++++++++++++++++++++++++++++++++");

    }
}
