let a =
    match not true with 
    | true -> "nope" 
    | false -> "yep";;

let b =
    match 42 with 
    | foo -> foo;;

let c =
    match "foo" with
    | "bar" -> 0
    | _ -> 1;;

let d =
    match [] with
    | [] -> "empty"
    | _ -> "not empty";;

let e =
    match [1:2] with
    | [] -> "empty"
    | _ -> "not empty";;


(*注意模式匹配并行是需要等价的*)
let f = 
    match ["toylor"; "swift"] with
    | [] -> "folkore"   (*这个地方是字符串*)
    | h :: t -> h;;     (*这个地方是也需要是字符串*)

let fst3 t =
    match t with
    | (a, b, c) -> a;;


