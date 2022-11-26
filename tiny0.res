//手写一个语言
type rec expr = 
 | Cst(int)
 | Mul(expr)
 | Add(expr)

//创建一个解释器
let rec interp = (expr: expr) => {
    switch expr {
    | Cst(i) => i
    | Mul(i, j) => interp(i) * interp(j)
    | Add(i, j) => interp(i) + interp(j)
    }
}

//因为interp需要ex
Js.log(interp(Mul(Cst(1), Cst(2))))
Js.log(interp(Add(Cst(2), Cst(3))))


//因为需要将上面的语言使用到了宿主语言的栈

//创建三个指令,
type instr = Cst(int) | Mul | Add

//创建一个指令集
type instrs = list<instr>

//创建一个操作数
type operand = int

//创建一个操作数集合
type stack = list<operand>

//运行的时候替换
let rec eval = (instrs: instrs, stack: stack) => {
    switch (instrs, stack) {
    | (list{}, list{result}) => result
    | (list{ Cst(i), ...rest}, _) => 
        eval(rest, list{i, ...stack})
    | (list{ Add, ...rest}, list{a, b, ...stack}) => 
        eval(rest, list{a + b, ...stack})
    | (list{ Mul, ...rest}, list{a, b, ...stack}) => 
        eval(rest, list{a * b, ...stack})
    | _ => assert false
    }
}