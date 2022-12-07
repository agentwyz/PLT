type rec nat =
| Z
| S(nat)

let three = S (S (S (Z) ) )

let church_to_peano = (n) => n(x => S(x), Z)

let rec peano_to_church = (n) => {
    switch n {
    | Z => church_zero
    | S(n) => church_succ(peano_to_church(n))   //先将小的变成一个church然后将小的加1
    }
 }

 let pred = (n) => {
    let init = {church_zero, church_zero}
    let iter = ((_, y)) => (y, church_succ(y))
    let (ans, _) = n(iter, init)
 }

//lambda(s, z) S^n(x)
 let church_decode = (n) => n((x) => x + 1, 0)

 Js.Console.log(church_decode(church_two))
 Js.Console.log(church_decode(pred(church_two)))