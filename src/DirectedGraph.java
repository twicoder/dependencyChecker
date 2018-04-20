import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DirectedGraph {
    Set<GraphNode> graphNodes;

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
            System.out.println("----------------------" + oneNode.getNodeName() + "----------------------------");
            System.out.println("\t\tChild Info:");
            for(GraphNode oneChildNode : oneNode.getChildren()){
                System.out.println("\t\t\t\t" + oneChildNode.getNodeName());
            }
            System.out.println("\t\tParent Info:");
            for(GraphNode oneParentNode : oneNode.getParents()){
                System.out.println("\t\t\t\t" + oneParentNode.getNodeName());
            }
        }
    }

    public void addNode(String nodeName, ArrayList<String> children){
        for(String oneChild : children){
            addNode(nodeName,oneChild);
        }
    }

    public void addNode(String nodeName, String childNodeName){
        addNode(nodeName);
        addNode(childNodeName);
        getNodeByName(nodeName).getChildren().add(getNodeByName(childNodeName));
        getNodeByName(childNodeName).getParents().add(getNodeByName(nodeName));
    }


    public void addNode(String nodeName){
        if(!isNodeExists(nodeName)){
            GraphNode newGraphNode = new GraphNode(nodeName);
            this.graphNodes.add(newGraphNode);
            System.out.println("Added node with name:" + nodeName);
        }
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
