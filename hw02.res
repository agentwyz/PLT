//nameless --> Indexed
module Indexed = {
    type rec expr =
    | Cst (int)
    | Add (expr)
    | Mul (expr, expr)
    | Var ({bind: int, stack_index: int})   //通过编译inline record
    | Let (expr, expr)
}

type sv = Slocal | Stmp
type senv = list<sv>

//抽象解释算法
let complie = (expr) => {
    let rec go = (expr: NameLes.expr, senv: senv) : Indexed.expr => {
        switch expr {
        | Cst(i) => Cst(i)
        | Var(s) => Var({bind: s, stack_index: sindex(senv, s)})
        | Add(e1, e2) => Add(go(e1, senv), go(e2, list{Stmp, ...senv}))
        | Mul(e1, e2) => Mul(go(e1, senv), go(e2, list{Stmp, ...senv}))
        | Let(e1, e2) => Let(go(e1, senv), go(e2, list{Slocal, ...senv}))
        }
    }
}