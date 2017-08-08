    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ofd5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ian Currie
 */
public class OntFormat {
    private static final Map<String, List<List<String>>> ONT_TABLE = new HashMap<>();
    
    public static void outputColumns(List<List<String>> csvList, List<String> ontologyAttributes, String ont_loca) throws IOException{
        for(String attribute: ontologyAttributes){
            String columnLocation = ont_loca + attribute + ".csv";
            PrintWriter print = new PrintWriter(new FileWriter(columnLocation, false));
            int i = csvList.get(0).indexOf(attribute);
            StringBuilder sb = new StringBuilder();
            
            for(int j=1; j<csvList.size(); j++){
                String line = csvList.get(j).get(i);
                sb.append(line).append("\n");
            }
            print.write(sb.toString());
            print.close();  
        }
    }
    public static void getOnt(List<String> ontologyAttributes, String delim, Integer threshold, String ont_loca, Integer length) throws IOException{
        for(String attribute:ontologyAttributes){
            BufferedReader scan;
            String lineStr;
            List<List<String>> attOntTable = new ArrayList<>();
            
            scan = new BufferedReader(new FileReader(new File(ont_loca + attribute +"_"+length.toString()+ "out.csv")));
            while((lineStr = scan.readLine()) != null ){
                List<String> line = new ArrayList<>(Arrays.asList(lineStr.split(delim)));
                List<String> thrLine = line.subList(1,threshold + 2 > line.size() ? line.size() : threshold + 2);
                attOntTable.add(thrLine);
            }
            ONT_TABLE.put(attribute, attOntTable);
        }
    }
    public static List<List<String>> getTable(String attribute){
        return ONT_TABLE.get(attribute);
    }
}
