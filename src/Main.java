import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filepath = "./data";
        File file = new File(filepath);

        DirectedGraph directedGraph = new DirectedGraph();


        if (file.isDirectory()) {
            String[] filelist = file.list();
            for(String oneFileName : filelist){
                String fileContent = readFileContent(file.getAbsolutePath() + File.separator + oneFileName);
                JsonReader jsonReader = Json.createReader(new StringReader(fileContent));
                JsonObject jsonContent = jsonReader.readObject();
                for(Map.Entry<String,JsonValue> oneRecord: jsonContent.entrySet()){
                    String projectName = oneRecord.getKey();
                    ArrayList<String> projectDependencies = getStringArrayFromString(oneRecord.getValue().toString());
                    directedGraph.addNode(projectName, projectDependencies);
                }
            }
        } else {
            System.out.println("Target is not exits or is not a directory!");
        }

        interactShell(directedGraph);
    }

    public static void interactShell(DirectedGraph directedGraph){
        String userCmd = "";
        Scanner scanner = new Scanner(System.in);
        boolean shouldExit = false;
        while(!shouldExit){
            userCmd = scanner.nextLine();
            switch(userCmd){
                case "print":
                    directedGraph.printInfo();
                    break;
                case "exit":
                    shouldExit = true;
                    break;
                case "nodename":
                    System.out.println("Please input the node name:");
                    String nodeName = new Scanner(System.in).nextLine();
                    directedGraph.printNodeInfo(nodeName);
                    break;
                case "list":
                    directedGraph.listAllNode();
                    break;
                case "sole":
                    directedGraph.listAllSoleNode();
                    break;
                case "delete":
                    System.out.println("Please input the node name you want delete:");
                    String nodeNamesToDelete = new Scanner(System.in).nextLine();
                    String[] nodeNames = nodeNamesToDelete.split(" ");
                    for(String oneNodeName : nodeNames){
                        directedGraph.deleteNode(oneNodeName);
                    }
                    break;
                default:
                    System.out.println("Unknown cmd:" + userCmd);
                    break;
            }
        }
    }

    private static ArrayList<String> getStringArrayFromString(String oneLineContent) {
        ArrayList<String> result = new ArrayList<>();
        String lineWithoutHeaderAndTail = oneLineContent.substring(1,oneLineContent.length()-1);
        if(lineWithoutHeaderAndTail.contains(",")){
            String[] parsedStringArray = lineWithoutHeaderAndTail.split(",");
            for(String oneString : parsedStringArray){
                result.add(oneString.substring(1,oneString.length()-1));
            }
        }
        return  result;
    }

    private static String readFileContent(String dataFilePath) {
        String fileContent = "";
        File file = new File(dataFilePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                fileContent += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return fileContent;
    }
}
