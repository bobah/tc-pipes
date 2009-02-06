package org.terracotta;

import org.terracotta.message.pipe.Pipe;
import org.terracotta.message.pipe.BlockingQueueBasedPipe;
import org.terracotta.message.topology.Topology;
import org.terracotta.message.topology.DefaultTopology;
import org.terracotta.message.topology.TopologyManager;
import org.terracotta.jmx.util.events.ClusterEvents;


import javax.management.MalformedObjectNameException;

/**
 * Created by vkatson
 * Date: 05.02.2009
 * Time: 13:01:24
 */
public class Consumer {
    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException {

        //Creating topology
        Pipe.Factory pipeFactory = new BlockingQueueBasedPipe.Factory(10);
        Topology.Factory topologyFactory = new DefaultTopology.Factory(pipeFactory);
        Topology topology = TopologyManager.getInstance().<String, String>getOrCreateTopology("myTopologyName", topologyFactory);

        ClusterEvents.ClusterListener listener = new ClusterEvents.ClusterListener();
        ClusterEvents.registerListener(listener);
        listener.waitForRegistration();

        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println("Node ID: " + listener.getMyNodeId());
        System.out.println("+++++++++++++++++++++++++++++++++++++");

        Pipe<String> pipe = topology.getOrCreatePipeFor("myRoutingId");

        for (; ;) {
            String message = pipe.take();
            System.out.println(message + " consumed on node " + listener.getMyNodeId());
//            System.out.println(message);
}
    }
}
