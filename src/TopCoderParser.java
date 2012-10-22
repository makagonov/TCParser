import java.util.*;
import java.io.*;

//How to use: paste full HTML content of the page containing "System Test Results" for any correct solution
//into parser.in file.

public class TopCoderParser {
    public static void main (String args[]) throws Exception{
        new TopCoderParser();
    }

    BufferedReader in;
    PrintWriter out;
    StringTokenizer st;

    public TopCoderParser() throws Exception{
        in = new BufferedReader(new FileReader("parser.in"));
        solve();
    }

    private int testCounter = 0;

    public void solve() throws Exception{
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = in.readLine()) != null)
            sb.append(line);
        int start_index = 0;
        while(true){
            start_index = sb.indexOf("<TR valign=\"top\">", start_index);
            if (start_index < 0) break;
            this.processTestCase(sb.substring(sb.indexOf("<TR valign=\"top\">", start_index), start_index = sb.indexOf("</TR>", start_index)));
        }
    }

    public void processTestCase(String text) throws Exception{
        this.testCounter++;
        int start_index = 0;
        String [] tds = new String[7];
        for(int i = 0;; i++){
            start_index = text.indexOf("<TD", start_index);
            if (start_index < 0) break;
            tds[i] = text.substring(start_index = text.indexOf(">", start_index) + 1, start_index = text.indexOf("</TD>", start_index));
        }
        System.out.println("testCase: " + testCounter);
        for (int i = 0; i < tds.length; i++){
            System.out.println(tds[i]);
        }
        System.out.println("-----------------------------");
        this.parseInput(tds[1]);
        this.parseOutput(tds[3]);
    }

    private boolean input_separate_lines = false;

    public void parseInput(String input) throws Exception{
        out = new PrintWriter(new FileWriter(String.format("%03d.dat", testCounter)));
        while(input.length() > 0){
            input = input.trim();
            if (input.startsWith("{")){
                int closeIndex = input.indexOf("}");
                String tmp = input.substring(1, closeIndex);
                StringBuilder sb = new StringBuilder();
                String [] sp = tmp.split(", ");
                out.println(tmp.length() > 0 ? sp.length : 0);
                for (int i = 0; i < sp.length; i++){
                    sp[i] = sp[i].trim();
                    if (sp[i].length() > 0){
                        sb.append(eraseQuotes(sp[i]) + (input_separate_lines ? "\n" : " "));
                    }
                }
                out.println(sb.toString().trim());

                if (closeIndex + 2 < input.length()){
                    input = input.substring(closeIndex + 2, input.length());
                }
                else break;
            }
            else if (input.contains(",")){
                String tmp = input.substring(0, input.indexOf(","));
                out.println(eraseQuotes(tmp));
                input = input.substring(input.indexOf(",") + 1, input.length());
            }
            else{
                out.println(eraseQuotes(input));
                break;
            }
        }
        out.close();
    }

    private boolean output_separate_line = false;
    public void parseOutput(String output) throws Exception{
        out = new PrintWriter(new FileWriter(String.format("%03d.ans", testCounter)));
        if (output.contains("{") && output.contains("}")){
            output = output.substring(1, output.length() - 1);
            String [] sp = output.split(",");
            for (int i = 0; i < sp.length; i++){
                sp[i] = sp[i].trim();
                if (sp[i].length() > 0)
                    out.print(eraseQuotes(sp[i]) + (output_separate_line ? "\n":" "));
            }
        }
        else{
            out.println(eraseQuotes(output));
        }
        out.close();

    }

    public String eraseQuotes(String s){
        return (s.startsWith("\"") && s.endsWith("\"")) ? s.substring(1, s.length() - 1) : s;
    }
}
