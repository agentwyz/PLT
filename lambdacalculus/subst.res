//如果x是需要替代的变量

let rec subst = (x, y, a) => {
    switch s {
    | Var(y) => if x == y {v} else {a}
    | Fn(y, b) => if x == y {a} else {Fn(y, subst(x, v, b))}
    | App(b, c) => App(subst(x, v, b), subst(x, v, c))
    }
}

let rename = (t, old, name) => {
    switch t {
    | Var(x)
    | 
    }
}


