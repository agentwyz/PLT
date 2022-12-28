import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleLexer {
    public static void main(String[] args) {
        SimpleLexer lexer = new SimpleLexer();
    }

    private boolean isDigit(int ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isAlpha(int ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private boolean isBlank(int ch) {
        return ch == ' '
            || ch == '\t'
            || ch == '\n';
    }

    private enum DfaState {
        Inital,

        If,
        Id_if1, Id_if2,

        Else,
        Id_else1, Id_else2, Id_else3, Id_else4,

        Int,
        Id_int1, Id_int2, Id_int3,

        Id, GT, GE,

        Assignment,

        Plus, Minus, Star, Slash,

        SemiColon,
        LeftParen, RightParen,

        IntLiteral
    }

    private final class SimpleToken implements Token {
        //token类型
        private TokenType type = null;

        //文本类型
        private String text = null;

        @Override
        public TokenType getType() {
            return type;
        }

        @Override
        public String getText() {
            return text;
        }

    }

    private StringBuffer tokenText = null;  //临时保存token的文本
    private List<Token> tokens = null;      //保存解析出来的token
    private SimpleToken token  = null;      //当前正在解析的token

    public DfaState initToken(char ch) {

        if (tokenText.length() > 0) {
            token.text = tokenText.toString();
            tokens.add(token);

            //重新赋值
            tokenText = new StringBuffer();
            token = new SimpleToken();
        }

        DfaState newState = DfaState.IntLiteral;

        if (isAlpha(ch)) {
            //处理关键字
            if (ch == 'i') {
                newState = DfaState.Id_int1;
            } else {
                newState = DfaState.Id;
            }
            token.type = TokenType.Identifier;
            tokenText.append(ch);
        } else if (isDigit(ch)) {
            newState = DfaState.IntLiteral;
            token.type = TokenType.IntLiteral;
        } else if (ch == '>') {
            newState = DfaState.GT;
            token.type = TokenType.GT;
            tokenText.append(ch);
        } else if (ch == '+') {
            newState = DfaState.Plus;
            token.type = TokenType.Plus;
            tokenText.append(ch);
        } else if (ch == '-') {
            newState = DfaState.Minus;
            token.type = TokenType.Minus;
            tokenText.append(ch);
        } else if (ch == '*') {
            newState = DfaState.Star;
            token.type = TokenType.Star;
            tokenText.append(ch);
        } else if (ch == '/') {
            newState = DfaState.Slash;
            token.type = TokenType.Slash;
        }
    }
}
