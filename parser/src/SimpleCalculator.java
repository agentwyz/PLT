import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleCalculator {
    public static void main(String[] args) {

    }

    private class SimpleASTNode implements ASTNode {
        SimpleASTNode parent = null;
        List<ASTNode> children = new ArrayList<ASTNode>();
        List<ASTNode> readonlyChildren = Collections.unmodifiableList(children);
        ASTNodeType nodeType = null;
        String text = null;

        //创建一个构造函数
        public SimpleASTNode(ASTNodeType nodeType, String next)
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
    public static void intDeclare(TokenReader tokens) {
        SimpleASTNode node = null;
        Token token = tokens.peek();

        //IntDeclaration : Int Identifier ('=', additiveExpression)?;
        if (token.getType() == TokenType.Int) {
            token = tokens.read();
            if (token.peek().getType() == TokenType.Identifier) {
                //create a node
                node = new SimpleASTNode(ASTNodeType.IntDeclearation, token.getText());
                token = tokens.peek();
                if (token.getType() == TokenType.Assignment) {
                    tokens.read();
                    SimpleASTNode child = additve(tokens);
                    if (child == null) {
                        throw new Exception("Invalide varible initialization, expression an expreesion");
                    } else {
                        node.addChild(child);
                    }
                }
            } else {
                throw new Exception("Verible name expected");
            }
        }
    }
}
