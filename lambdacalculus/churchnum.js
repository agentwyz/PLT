var f = x => {
    if (x == 0) {
        return 0;
    } else {
        return f(x - 1) + 1;
    }
}

//定义自然数
var n = (f, x) => {
    return f(x);
}


//定义加1
var succ = (f, n) => {
    return f(n * f(x));
}

//定义两个数相加
var add = ()