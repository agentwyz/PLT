let imply v =
    match v with
    (true, true) -> true
    | (true, false) -> false


type name 