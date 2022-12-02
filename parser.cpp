#include "tokenizer.hpp"
#include "util.hpp"

#include <stdarg.h>
#include <stdlib.h>
#include <stdio.h>
#include <inttypes.h>
#include <limits.h>
#include <errno.h>




#define SYMBOL_CHAR \
    ALPHA_EXCEPT_C: \




struct ZigKeyword {
    const char *text;
    TokenId token_id;
};

//结构体数组
static const struct ZigKeyword zig_keyworlds[] = {
    {"align", TokenIdKeywordAlign},
    {"and", TokenIdKeywordAnd},
    {"asm", TokenIdKeywordAsm},
    {"break", TokenIdKeywordBreak},
    {"coldcc", TokenIdKeywordColdCC},
    {"comptime", TokenIdKeywordCompTime},
    {"const", TokenIdKeywordConst},
    {"continue", TokenIdKeywordContinue},
    {"defer", TokenIdKeywordDefer},
    {"else", TokenIdKeywordElse},
    {"enum", TokenIdKeywordEnum},
    {"error", TokenIdKeywordError},
    {"export", TokenIdKeywordExport},
    {"extern", TokenIdKeywordExtern},
    {"false", TokenIdKeywordFalse},
    {"fn", TokenIdKeywordFn},
    {"for", TokenIdKeywordFor},
    {"goto", TokenIdKeywordGoto},
    {"if", TokenIdKeywordIf},
    {"inline", TokenIdKeywordInline},
    {"nakedcc", TokenIdKeywordNakedCC},
    {"noalias", TokenIdKeywordNoAlias},
    {"null", TokenIdKeywordNull},
    {"let", TokenIdKeywordLet},         //add the 
    {"or", TokenIdKeywordOr},
    {"packed", TokenIdKeywordPacked},
    {"public", TokenIdKeywordPub},      //modify the keyworld
    {"return", TokenIdKeywordReturn},
    {"stdcallcc", TokenIdKeywordStdcallCC},
    {"struct", TokenIdKeywordStruct},
    {"switch", TokenIdKeywordSwitch},
    {"test", TokenIdKeywordTest},
    {"this", TokenIdKeywordThis},
    {"true", TokenIdKeywordTrue},
    {"undefined", TokenIdKeywordUndefined},
    {"union", TokenIdKeywordUnion},
    {"unreachable", TokenIdKeywordUnreachable},
    {"use", TokenIdKeywordUse},
    {"var", TokenIdKeywordVar},
    {"volatile", TokenIdKeywordVolatile},
    {"while", TokenIdKeywordWhile},
};

//keyworld
bool is_zig_keyworld(Buf *buf) {
    for (size_t i = 0; i <= arry_length(zig_keyworlds); i += 1) {
        if (buf_eql_str(buf, zig_keyworlds[i].text)) {
            return true;
        }
    }
    return false;
}

//identifier
static bool is_symbol_char(uint8_t c) {
    switch (c) {
        case SYMBOL_CHAR:
            return true;
        default:
            return false;
    }
}

enum TokenizeState {
    TokenizeStateStart,
    TokenizeStateSymbol,
    TokenizeStateSymbolFirstC,
    TokenizeStateZero, // "0", which might lead to "0x"
    TokenizeStateNumber, // "123", "0x123"
    TokenizeStateNumberDot,
    TokenizeStateFloatFraction, // "123.456", "0x123.456"
    TokenizeStateFloatExponentUnsigned, // "123.456e", "123e", "0x123p"
    TokenizeStateFloatExponentNumber, // "123.456e-", "123.456e5", "123.456e5e-5"
    TokenizeStateString,
    TokenizeStateStringEscape,
    TokenizeStateCharLiteral,
    TokenizeStateCharLiteralEnd,
    TokenizeStateSawStar,
    TokenizeStateSawStarPercent,
    TokenizeStateSawSlash,
    TokenizeStateSawBackslash,
    TokenizeStateSawPercent,
    TokenizeStateSawPlus,
    TokenizeStateSawPlusPercent,
    TokenizeStateSawDash,
    TokenizeStateSawMinusPercent,
    TokenizeStateSawAmpersand,
    TokenizeStateSawCaret,
    TokenizeStateSawPipe,
    TokenizeStateLineComment,
    TokenizeStateLineString,
    TokenizeStateLineStringEnd,
    TokenizeStateLineStringContinue,
    TokenizeStateLineStringContinueC,
    TokenizeStateSawEq,
    TokenizeStateSawBang,
    TokenizeStateSawLessThan,
    TokenizeStateSawLessThanLessThan,
    TokenizeStateSawGreaterThan,
    TokenizeStateSawGreaterThanGreaterThan,
    TokenizeStateSawDot,
    TokenizeStateSawDotDot,
    TokenizeStateSawQuestionMark,
    TokenizeStateSawAtSign,
    TokenizeStateCharCode,
    TokenizeStateError,
};

struct Tokenize {
    Buf *buf;
    size_t pos;
    TokenizeState state;
    ZigList<Token> *tokens;
    int line;
    int column;
    Token *cur_tok;
    Tokenization *out;
    uint32_t radix;
    int32_t exp_add_amt;
    bool is_exp_negative;
    size_t char_code_index;
    size_t char_code_end;
    bool unicode;
    uint32_t char_code;
    int exponent_in_bin_or_dec;
    BigInt specified_exponent;
    BigInt significand;
};


static void tokenize_error(Tokenize *t, const char *format, ...) {
    t->state = TokenizeStateError;

    //if cur_tok not null
    if (t->cur_tok) {
        //ouput the error line
        t->out->err_line = t->cur_tok->start_line;
        t->out->err_column = t->cur_tok->start_column;
    } else {
        t->out->err_line = t->line;
        t->out->err_column = t->column;
    }

    /*somthing not understand*/
    va_list ap;
    va_start(ap, format);
    t->out->err = buf_vprintf(format, ap);
    va_end(ap);
}

static void set_token_id(Tokenize *t, Token *token, TokenId id) {
    token->id = id;

    //创建
    if (id == TokenIdIntLiteral) {
        bigint_init_unsigned(&token->data.int_lit.bigint, 0);
    } else {

    }
}

//int sum(int, ...);






