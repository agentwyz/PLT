//定义皮亚罗数
type rec nat = Z | S(nat)

let peano_zero = Z
let peano_one = S(Z)
let peano_two = S(S(Z))

type enum<'a> = ('a => 'a, 'a) => 'a;
//前者是一个函数, 后者是一个....
let church_zero = (s, z) => z
let church_one = (s, z) => s(z)
let church_two = (s, z) => s(s(z))

let peano_succ = (x) => S(x)
let church_succ = (n) => (s, z) => s(n(s, z))

//将皮亚罗数转化成church
let church_to_peano = (n) => n(x => S(x), Z)

let rec peano_to_church = (n) => {
    switch n {
        | Z => church_zero
        | S(n) => church_succ(peano_to_church(n))
    }
}

//求前继
let pred = (n) => {
    let init = ()
}