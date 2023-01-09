import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleCalculator {
    public static void main(String[] args) {
        //测试变量声明语句
        SimpleCalculator calculator = new SimpleCalculator();
        String script = "int a = 5 + 2 * 3;";

        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(script);

        try {
            SimpleASTNode node = calculator.intDeclare(tokens);
            calculator.dumpAST(node, "");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //测试表达式
        script = "2+3*5";
        System.out.println("\n计算: " + script + "，看上去一切正常。");
        calculator.evaluate(script);
    }
    //函数重载
    public void evaluate(String script) {
        try {
            ASTNode tree = parse(script);
            dumpAST(tree, "");
            evaluate(tree, "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private class SimpleASTNode implements ASTNode {
        SimpleASTNode parent = null;
        List<ASTNode> children = new ArrayList<ASTNode>();
        List<ASTNode> readonlyChildren = Collections.unmodifiableList(children);
        ASTNodeType nodeType = null;
        String text = null;

        //创建一个构造函数
        public SimpleASTNode(ASTNodeType nodeType, String text)
        {
            this.nodeType = nodeType;
            this.text = text;
        }

        //创建一个父亲节点
        @Override
        public ASTNode getParent() {
            return parent;
        }

        //创建一个子节点
        @Override
        public List<ASTNode> getChildren() {
            return readonlyChildren;
        }

        //AST类型
        @Override
        public ASTNodeType getType() {
            return nodeType;
        }

        //文本值
        @Override
        public String getText() {
            return text;
        }

        public void addChild(SimpleASTNode child)
        {
            children.add(child);
            child.parent = this;
        }

    }
    public SimpleASTNode intDeclare(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        //返回下一个节点
        Token token = tokens.peek();

        if (token != null && token.getType() == TokenType.Int) { //
            token = tokens.read();
            if (tokens.peek().getType() == TokenType.Identifier) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.getText());
                token = tokens.peek();
                if (token != null && token.getType() == TokenType.Assignment) {
                    tokens.read();
                    SimpleASTNode child = additve(tokens);
                    if (child == null) {
                        throw new Exception("Invalid varible initialization, expecting an expression");
                    } else {
                        node.addChild(child);
                    }
                }
            } else {
                throw new Exception("variable name expected");
            }

            if (node != null) {
                token = tokens.peek();
                if (token != null && token.getType() == TokenType.SemiColon) {
                    tokens.read();
                } else {
                    throw new Exception("invalid statement, expecting semicoin");
                }
            }
        }
        return node;
    }

    private SimpleASTNode additve(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = multiplicative(tokens);//2+3*4
        SimpleASTNode node = child1;

        Token token = tokens.peek();//首先看一看是不是加号
        //注意看这里
        if (token != null && child1 != null) {
            if (token.getType() == TokenType.Plus //然后匹配加号
                || token.getType() == TokenType.Minus) {
                //然后进行读取
                token = tokens.read();

                SimpleASTNode child2 = additve(tokens); //然后又再次进行匹配

                if (child2 != null) {
                    node = new SimpleASTNode(ASTNodeType.Additive, token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                } else {
                    throw new Exception("Invalid additive expression, expecting the right part");
                }
            }
        }
        return node;
    }


    private SimpleASTNode multiplicative(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = primary(tokens);//然后再次进行匹配
        SimpleASTNode node = child1;

        Token token = tokens.peek();
        if (child1 != null && token != null)
        {
            if (token.getType() == TokenType.Star
                    || token.getType() == TokenType.Slash)
            {
                token = tokens.read();
                SimpleASTNode child2 = multiplicative(tokens);
                if (child2 != null) {
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                } else {
                    throw new Exception("invalid multiplicative expression, expecting the right part");
                }
            }
        }
       return node;
    }

    //基础表达式
    private SimpleASTNode primary(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token token = tokens.peek();             //返回下一个token, 但是不会向前移动

        if (token != null) {
            if (token.getType() == TokenType.IntLiteral) {//然后有又没有匹配上
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.getText());
            } else if (token.getType() == TokenType.LeftParen) {
                tokens.read();
                node = additve(tokens);
                if (node != null) {
                    token = tokens.peek();
                    if (token != null && token.getType() == TokenType.RightParen) {
                        tokens.read();
                    } else {
                        throw new Exception("expecting right parenthesus");
                    }
                } else {
                    throw new Exception("expecting an additive expression inside parenthesis");
                }
            } else if (token.getType() == TokenType.Identifier) {
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.Identifier, token.getText());
            }
        }
        return node;
    }

    private SimpleASTNode assignmentStatement(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;

        Token token = tokens.peek();
        if (token != null && token.getType() == TokenType.Identifier) {
            token = tokens.read();  //读入标识符
            node = new SimpleASTNode(ASTNodeType.AssignmentStmt, token.getText());
            token = tokens.peek();
            if (token != null && token.getType() == TokenType.Assignment) {
                tokens.read();
                SimpleASTNode child = additve(tokens);
                if (child == null) {
                    throw new Exception("invalide assignment statemnet, expecting an expression");
                } else {
                    node.addChild(child);
                    token = tokens.peek();
                    if (token != null && token.getType() == TokenType.SemiColon) {
                        tokens.read();
                    } else {
                        throw new Exception("Invalid statement. expecting semicolon");
                    }
                }
            } else {
                tokens.unread();
                node = null;
            }
        }
        return node;
    }


    //表示根节点
    private SimpleASTNode prog(TokenReader tokens) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "Calculator");
        SimpleASTNode child = additve(tokens);

        if (child != null)
        {
            node.addChild(child);
        }
        return node;//创建一个节点
    }

    //求值
    public ASTNode parse(String code) throws Exception {
        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(code);

        ASTNode rootNode = prog(tokens);
        return rootNode;
    }

    private int evaluate(ASTNode node, String indent)
    {
        int result = 0;
        System.out.println(indent + "Calculating:" + node.getType());
        switch (node.getType()) {
            case Programm:
                for (ASTNode child : node.getChildren()) {
                    result = evaluate(child, indent + "\t");
                }
                break;
            case Additive:
                ASTNode child1 = node.getChildren().get(0);
                int value1 = evaluate(child1, indent + "\t");
                ASTNode child2 = node.getChildren().get(1);
                int value2 = evaluate(child2, indent + "\t");
                if (node.getText().equals("+")) {
                    result = value1 + value2;
                } else {
                    result = value1 - value2;
                }
                break;
            case Multiplicative:
                child1 = node.getChildren().get(0);
                value1 = evaluate(child1, indent + '\t');
                child2 = node.getChildren().get(1);
                value2 = evaluate(child2, indent + "\t");
                if (node.getText().equals("*")) {
                    result = value1 * value2;
                } else {
                    result = value1 / value2;
                }
                break;
            case IntLiteral:
                result = Integer.valueOf(node.getText()).intValue();
                break;
            default:
        }
        System.out.println(indent + "Result:" + result);
        return result;
    }

    //解析脚本
    private void dumpAST(ASTNode node, String indent)
    {
        System.out.println(indent + node.getType() + " " + node.getText());

        for (ASTNode child : node.getChildren()) {
            dumpAST(child, indent + '\t');
        }
    }

}
