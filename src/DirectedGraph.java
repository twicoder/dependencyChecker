import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DirectedGraph {
    Set<GraphNode> graphNodes;

    String[] knownNodeNames = {
            "commons-collections4",
            "commons-codec",
            "logback-classic",
            "jersey-server",
            "bcprov-jdk16",
            "spring-context",
            "commons-net",
            "mail",
            "commons-lang3",
            "spring-aop",
            "jersey-client",
            "commons-compress",
            "spring-beans",
            "aspectjweaver",
            "spring-core",
            "javax.servlet-api",
            "quartz",
            "mockito-core",
            "jul-to-slf4j",
            "httpclient",
            "h2",
            "jersey-json",
            "jasypt",
            "commons-io",
            "spring-security-core"
    };

    public boolean isKnownNode(String nodeName){
        for(String oneKnownNodeName : knownNodeNames){
            if(oneKnownNodeName.equalsIgnoreCase(nodeName)){
                return true;
            }
        }
        return false;
    }

    public DirectedGraph(Set<GraphNode> graphNodes) {
        this.graphNodes = graphNodes;
    }

    public DirectedGraph() {
        this.graphNodes = new HashSet<>();
    }

    public void printInfo(){
        for(GraphNode oneNode : graphNodes){
            if(oneNode.isSoleNode()){
                continue;
            }
            printNodeInfo(oneNode.getNodeName());
        }
    }

    public void printNodeInfo(String nodeName){
        GraphNode targetNode = getNodeByName(nodeName);
        System.out.println("----------------------" + targetNode.getNodeName() + "----------------------------");
        System.out.println("\t\tChild Info:");
        for(GraphNode oneChildNode : targetNode.getChildren()){
            System.out.println("\t\t\t\t" + oneChildNode.getNodeName());
        }
        System.out.println("\t\tParent Info:");
        for(GraphNode oneParentNode : targetNode.getParents()){
            System.out.println("\t\t\t\t" + oneParentNode.getNodeName());
        }
    }

    public void listAllNode(){
        for(GraphNode graphNode : graphNodes){
            System.out.println(graphNode.getNodeName());
        }
    }

    public void listAllSoleNode(){
        for(GraphNode graphNode : graphNodes){
            if(graphNode.isSoleNode()){
                System.out.println(graphNode.getNodeName());
            }
        }
    }

    public void addNode(String nodeName, ArrayList<String> children){
        for(String oneChild : children){
            addNode(nodeName,oneChild);
        }
    }

    public void addNode(String nodeName, String childNodeName){
        if(isKnownNode(nodeName)){
            return;
        }
        addNode(nodeName);
        if(isKnownNode(childNodeName)){
            return;
        } else {
            addNode(childNodeName);
            getNodeByName(nodeName).getChildren().add(getNodeByName(childNodeName));
            getNodeByName(childNodeName).getParents().add(getNodeByName(nodeName));
        }
    }


    public void addNode(String nodeName){
        if(!isNodeExists(nodeName) && !isKnownNode(nodeName)){
            GraphNode newGraphNode = new GraphNode(nodeName);
            this.graphNodes.add(newGraphNode);
        }
    }

    // remove nodeName from its parents and children
    public void deleteNode(String nodeName){
        for(GraphNode node : getNodeByName(nodeName).getParents()){
            node.getChildren().remove(getNodeByName(nodeName));
        }
        for(GraphNode node : getNodeByName(nodeName).getChildren()){
            node.getParents().remove(getNodeByName(nodeName));
        }
        graphNodes.remove(getNodeByName(nodeName));
        System.out.println("Node deleted:" + nodeName);
    }

    public GraphNode getNodeByName(String nodeName){
        for(GraphNode graphNode : this.graphNodes){
            if(graphNode.getNodeName().equalsIgnoreCase(nodeName)){
                return graphNode;
            }
        }
        System.err.println("Node can't be found by name!");
        return null;
    }

    private boolean isNodeExists(String nodeName){
        for(GraphNode graphNode : this.graphNodes){
            if(graphNode.getNodeName().equalsIgnoreCase(nodeName)){
                return true;
            }
        }
        return false;
    }

    class GraphNode {
        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public Set<GraphNode> getParents() {
            return parents;
        }

        public void setParents(HashSet<GraphNode> parents) {
            this.parents = parents;
        }

        public Set<GraphNode> getChildren() {
            return children;
        }

        public void setChildren(HashSet<GraphNode> children) {
            this.children = children;
        }

        String nodeName;
        Set<GraphNode> parents;
        Set<GraphNode> children;

        public GraphNode(String nodeName) {
            this.nodeName = nodeName;
            this.children = new HashSet<>();
            this.parents = new HashSet<>();
        }

        public boolean isSoleNode(){
            if(this.children.size()==0){
                return true;
            } else {
                return false;
            }
        }
    }
}
