let a =
    match not true with 
    | true -> "nope" 
    | false -> "yep";;

let b =
    match 42 with 
    | foo -> foo;;

let z =
    match "foo" with
    | "bar" -> 0
    | _ -> 1;;