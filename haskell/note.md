# 函数式编程

## lambda calculus

lambda calculus是万物的开始, lambda calculus有三个部分组成

1. 函数
2. 函数调用
3. 变量

上面三个东西在现实生活中也有者对应的形式, 它们分别是

1. 函数对应者一个电子样板
2. 电子样板的实例,  将它的输入端子连接到某些已经有的导线, 这些导线叫做参数
3. 变量就是一个导线

## Currying

多个参数的函数可以变成一个参数函数的叠加, 因为函数可以作为一个表达式返回, 比如:

```scheme
(((lambda (a)
    (lambda (b) 
        (+ a b))) 1) 2)
```

在Haskell中函数的参数类型是这样写的

```haskell
circumference :: Float -> Float -> Float
circumference r = r * r
```

## 闭包

闭包是函数不仅将值返回, 还将变量的环境进行返回

```scheme
(let ([x 7])
    (define f 
        (lambda (a)
            (+ a x)))
    (let ([x 1])
        (f 3)))
```

上面这个函数会返回10, 这充分的说明了scheme是采用静态作用域的


lambda calculus构造任何东西

首先我们构造数
```scheme

```

我们然后去构造函数

## Y-combinator

实现对函数的缓存

```rescript
let memofib = {
    let cache = Hashtbl.create(100)
    (n) => {
        switch Hashtbl.find_opt() {
            | some(x) => x
            | None => {
                let x = fib(n)
                Hashtbl.replace(cache, n, x)
                x
            }
        }
    }
}
```

使用open recisive
```rescript
let myfib = (myfib, n) => {
    switch n {
    | 0 | 1 => 1
    | _ => myfib(n - 1) + myfib(n - 2)
    }
}

let memo = anyFunc => {
    let cache = Hashtbl.create(100)
    let rec fix = (n) => {
        switch Hashtabl.find_opt(cache, n) {
        | Some(x) => x
        | None => {
            let x = anyFunc(fix, n)
            Hashtbl.replace(cache, n, x)
            x
        }
        }
    }
    fix
}
let memofib = memo(myfib)
```
