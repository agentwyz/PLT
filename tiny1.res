/*
上面tiny0因为计算需要这样写
Add(Add(Cst(2)), Add(Cst(2)))
这样写比较困难, 但是我们可以这样写

Add(a, b)
a = Add(Cst(2))
b = Add(Cst(2))
*/

type rec expr = 
| Cst(int)
| Add(expr, expr)
| Mul(expr, expr)
| Var(string)               //表达式可能为字符串Var("wang")
| Let(string, expr, expr)   //该变量只可以在之后使用


//创建一个环境, 我们就在这个环境中进行寻找变量
type env = list<(string, int)>

let rec eval = (expr, env) => {
    switch expr {
    | Cst(i) => i
    | Add(a, b) => eval(a, env) + eval(b, env)
    | Mul(a, b) => eval(a, env) * eval(b, env)
    | Var(x) => assoc(x, env)                                       //相当于racket中的assign->[("x" ,1)] 在变量中进行查找
    | Let(e, e1, e2) => eval(e2, list{(x, eval(e1, env)), ...env})  //对环境增加一个值
    }
}

//eval(Let("s", 1, 2), 空环境)

module NameLess = {
    type expr = 
    | Cst(int)
    | Add(expr, expr)
    | Mul(expr, expr)
    | Var(int)
    | Let(expr, expr)
    
    type env = list<int>
    
    type rec eval = (expr: NameLess.expr, env) => {
    switch expr {
    | Cst(i) => i
    | Add(a, b) => eval(a, env) + eval(b, env)
    | Mul(a, b) => eval(a, env) * eval(b, env)
    | Var(n) => List.nth(env, n)                            //使用整数来进行寻址
    | Let(e1, e2) => eval(e2, list{eval(e1, env), ...env})
    }
}
}



