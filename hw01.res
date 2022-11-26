module BigStep = {
    type rec expr = 
    | Cst (int)
    | Add (expr, expr)
    | Mul (expr, expr)

    let rec eval = (expr) => {
        
    }
}


let rec compile = (src: BigStep.expr): Small.instrs => {
    switch src {
    | Cst(i) => list{Cst(i)}
    | Add(expr1, expr2) => {
        let target1 = compile(expr1)
        let target2 = compile(expr2)
        Belt.Lsit.concatMany([target1, target2, list{Add}])
    }
    | Mul(expr1, expr2) => {
        let target1 = compile(expr1)
        let target2 = complie(expr2)
        Belt.List.concatMany([target1, target2, List{Mul}])
    }
    }
}

//测试
module Tests = {
    let test_compile = (src: BigStep.expr) => {
        let complied = complie(src)
        //这个应该是用来做对比的
        let computed = SmallStep.eval(compiled, list{})
        assert(computed == BigStep.eval(src))
    }

    let basic_test = () => {
        let tests = [
            BigStep.Cst(42),
            Add(Cst(1), Cst(2))
        ]
        Belt.Array.forEachWhiteIndex(tests, (i, t)=> {
            test_compile(t)
            let i = i + 1
            Js.log(j`test $i passed`)
        })
    }
}