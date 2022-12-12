(*函数的定义*)
function x -> x * x;;

(*函数的调用*)
(function x -> x * x) 5;;

(*给函数起一个名字*)
let sq = function x -> x + 1;;

(*简化版本*)
let sq x = x * x;;

(*函数作为输出*)
function x -> (function y -> 3 * x + y);;

(function x -> (function y -> 3 * x + y) 2 3;;

(*函数作为输入*)
let app = function f -> function x -> f x;;
app odd 2;;

(*let app = function f x -> f x;;*)


(*形成一个闭包*)
let a = 1;;
(function x -> x * a) 5;;

(*函数递归*)
let rec sigma x = if x = 0
                    then 0
                    else x + sigma(x - 1)

(*幂等函数*)
let id x = x;;

(*有时候类型变量的名称我们并不知道*)
(*所以我们使用'a表示一种泛型, 这里我们就使用*)

(*运算符号作为一个函数*)

(+) 1 2;;

(*乘号有可能会被看成是一个注释, 建议在两边加上一个空格*)
( * ) 1 2;;

(*最好加上一个括号*)
let (<^>) x y = max x y;;

(*前缀是由括号的, 但是中缀是没有扩号的*)

succ 1 * 2;;

(succ 1) * 10;; (*输出20*)
(succ 2 * 10);; (*输出21*)

(*避免写括号*)
succ @@ 2 * 10;; (*先计算后面的表达式, 然后使用succ*)

(*反向运行*)
5 |> succ |> square;;

