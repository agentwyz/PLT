import java.util.List;

/*
- AST的节点
- 属性包括AST的类型, 文本值, 下级节点和父亲节点
*/

public interface ASTNode {

    //创建一个父亲节点
    public ASTNode getParent();

    //创建一个子节点
    public List<ASTNode> getChildren();

    //AST类型
    public ASTNodeType getType();

    //文本值
    public String getText();

}