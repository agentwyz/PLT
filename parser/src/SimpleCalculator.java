import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleCalculator {
    public static void main(String[] args) {
        //测试变量声明语句
        SimpleCalculator calculator = new SimpleCalculator();
        String script = "int a = 3";

        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(script);

        try {
            SimpleASTNode node = calculator.intDeclare(tokens);
            calculator.dumpAST(node, "");
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
            return children;
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
        Token token = tokens.peek();

        //IntDeclaration : Int Identifier ('=', additiveExpression)?;
        if (token.getType() == TokenType.Int) {
            token = tokens.read(); //int
            //tokens=int,
            if (tokens.peek().getType() == TokenType.Identifier) {
                //create a node

                node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.getText());


                tokens.read();
                token = tokens.read();
                System.out.println(token.getType());
                if (token.getType() == TokenType.Assignment) {
                    SimpleASTNode child = test(tokens);
                    //System.out.println(child.text);
                    node.addChild(child);

                }
            } else {
                throw new Exception("Verible name expected");
            }
        }
        //System.out.println(node.text);
        return node;
    }

    private SimpleASTNode additve(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = null; //multiplicative(tokens);
        SimpleASTNode node = child1;

        Token token = tokens.peek();

        if (token != null) {
            if (token.getType() == TokenType.Plus
                || token.getType() == TokenType.Minus) {

                token = tokens.peek();
                SimpleASTNode child2 = additve(tokens);

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


    private SimpleASTNode test(TokenReader tokens) throws Exception {
        //System.out.println(tokens.read().getText());
        SimpleASTNode child1 = primary(tokens);
        //System.out.println(child1.text);

        SimpleASTNode node = child1;
        System.out.println(node.text);

       //node.addChild(child1);
       return node;
    }

    //表示根节点
    private SimpleASTNode prog(TokenReader tokens) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "Calculator");
        SimpleASTNode child = test(tokens);

        if (child != null)
        {
            node.addChild(child);
        }
        return node;//创建一个节点
    }

    //基础表达式
    private SimpleASTNode primary(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token token = tokens.read();
        //System.out.println(token.getText());
        if (token != null) {
            System.out.println(token.getType());
            if (token.getType() == TokenType.IntLiteral) {
                //System.out.println(token.getText());
                //System.out.println(token.getText());
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.getText());

            } else if (token.getType() == TokenType.Identifier) {
                token = tokens.read();
            } else if (token.getType() == TokenType.LeftParen) {

            }
        }
        //System.out.println(node.text);
        return node;
    }



    private void dumpAST(ASTNode node, String indent)
    {
        System.out.println(indent + node.getType() + " " + node.getText());

        for (ASTNode child : node.getChildren()) {
            dumpAST(child, indent + '\t');
        }
    }

}
