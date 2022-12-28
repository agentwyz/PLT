public interface TokenReader {
    //返回Token流中下一个, 并取出来
    public Token read();

    //返回Token流中下一个, 不取出来
    public Token peek();

    //token流回退一步, 恢复原来的Token
    public void unread();

    //获取Token流当前的读取位置
    public int getPosition();

    //设置Token流当前的读取位置
    public void setPosition(int position);
}