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

(*列表*)
(*列表的构造[]*)
(*列表是不可变的*)

(*这是一个空的列表*)
[]

[1.; 2.; 3;]

[true, false];;

[[1; 2]; [3; 4]; [5; 6]];;

(*压栈*)
1 :: [1; 2];;
1 :: 2 :: 3 :: [];;

(*适合放置少的东西*)

type student = {
    name: string;
    year: int;
}

let wyz = {
    name = "wangyangzheng";
    year = 12345
}

(* 在命令行中输入 use "verible.ml" *)

(*元组*)
let a = (10, 10, "am");;

(*我们可以给这种类型起一个名字*)
type a = int * int * string;;

let t : time = (10, 10, "time");;

type point = float * float;;

let p : point = (5., 3.5)

(*只适合两元组*)
fst p;;
snd p;;