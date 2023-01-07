public class SimpleCalculator {
    public static void main(String[] args) {

    }
    public static void intDeclare() {
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
