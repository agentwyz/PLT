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
function sq x -> 2 + sq x;;

(*形成一个闭包*)
let a = 1;;
(function x -> x * a) 5;;