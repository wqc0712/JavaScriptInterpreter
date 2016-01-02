import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Created by tom on 15/12/26.
 * Copyright (c) 2015 tom
 */
public class JavaScriptInterpreter {

    static private int count(String s) {
        char[] chs = s.toCharArray();
        int c = 0;
        for (char ch:chs) {
            if (ch == '{') c = c + 1;
            if (ch == '}') c = c - 1;
        }
        return c;
    }

    public static void main(String args[]) {
        Context.getInstance().initial();
        Scanner scanner = new Scanner(System.in);
        FileInputStream inputStream = null;
        while (true) {
            if (inputStream != null) {
                if (!scanner.hasNextLine()) {
                    try {
                        inputStream.close();
                        scanner = new Scanner(System.in);
                        inputStream = null;
                    } catch (Exception err) {
                        System.err.print(err.getMessage());
                    }
                }
            }
            String line = scanner.nextLine();
            int c = count(line);
            while (c != 0) {
                if (c < 0) break;
                line = line+scanner.nextLine();
                c = count(line);
            }
            if (c < 0) {
                System.err.print("ERR Block!");
                continue;
            }

            if (line.equals("exit;")) {
                System.out.println("Bye!");
                return;
            }

            if (line != null) {
                //line = line.trim();

                try {
                    ANTLRInputStream input = new ANTLRInputStream(line);
                    ECMAScriptLexer lexer = new ECMAScriptLexer(input);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    ECMAScriptParser parser = new ECMAScriptParser(tokens);
                    parser.removeErrorListeners();
                    ParseTree tree = parser.program();
                    System.out.println(tree.toStringTree());
                    Visitor visitor = new Visitor();
                    visitor.visit(tree);
                    ExpressionQueue.getInstance().print();
                } catch (Exception err) {
                    System.err.println(err.getMessage());
                }
            }
        }
    }
}
