type rec expr = 
| Cst(int)
| Add(expr, expr)
| Mul(expr, expr)
| Var(string)
| Let(string, expr, expr)

 
type env = list<(string, int)>  //定义一个环境

let rec eval = (expr, env) => {
    switch expr {
    | Cst(i) => i
    | Add(i, j) => eval(i, env) + eval(j, env)
    | Mul(i, j) => eval(i, env) * eval(j, env)
    | Var(x) => Belt.List.getAssoc(env, x, (a, b) => a == b) -> Belt.Option.getWithDefault(_, 0) //对在环境中对变量求值
    | Let(i, j, k) => eval(k, list{(i, eval(j, env)), ...env})  //对变量进行绑定值, 然后放入环境中
    }
}

Js.log(eval(Let("a", Cst(1), Var("a")), list{}))

///////////////////第二部///////////////////////////////
module Nameless = {
    type rec expr =
    | Cst(int)
    | Add(expr, expr)
    | Mul(expr, expr)
    | Var(int)
    | Let(expr, expr)

    type env = list<int>

    let rec eval = (expr, env) => {
        switch expr {
        | Cst(i) => i
        | Add(i, j) => eval(i, env) + eval(j, env)
        | Mul(i, j) => eval(i, env) * eval(j, env)
        | Var(i) => List.nth(env, i)
        | Let(i, j) => eval(j, list{eval(i, env), ...env})
        }
    }
    let a = eval(Let(Cst(2), Var(0)), list{})
    Js.log(eval(Let(Cst(a), Var(0)), list{}))
}

///////////////////////////////第三步//////////////////////////////

 type rec expr =
    | Cst(int)
    | Add(expr, expr)
    | Mul(expr, expr)
    | Var(string)
    | Let(string, expr, expr)

//实现编译算法
type cenv = list<string>

//创建index函数
let findIndex = (env, str) => {
    let rec subFindIndex = (list, idx) => {
        switch list {
        | list{} => -1
        | list{hd, ...tail} => hd === str ? idx : subFindIndex(tail, idx + 1)
        }
    }
    subFindIndex(env, 0)
}

let rec comp = (expr: expr, cenv: cenv): Nameless.expr => {
    switch expr {
    | Cst(i) => Cst(i)
    | Add(a, b) => Add(comp(a, cenv), comp(b, cenv))
    | Mul(a, b) => Mul(comp(a, cenv), comp(b, cenv))
    | Var(x) => Var(findIndex(cenv, x)) //Var(index(env, "x"))
    | Let(x, e1, e2) => Let(comp(e1, cenv), comp(e2, list{x, ...cenv}))
    }
}