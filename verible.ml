(*下面是变量的绑定, 同时是全局变量*)
let a = 1;;

let b = 2
and c = 3;;

(*这种是错误的, 因为d的值在;;之后才结束*)
let d = 3
and e = d + 3;;


let a_tuple = (3, "three");;

(*let 表达式, 同时也是local verible*)
let xl = 3 in xl * xl;;

let a = 3.0 and b = 3.4 in a +. b;;


(*类型注释*)
let add (x : int) (y : int) = x + y;;





