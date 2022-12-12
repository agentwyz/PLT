字符串本身就是一个list

```haskell
let str = "Hello"
```

**注意时间的复杂度**

将两个list进行连接使用`++`运算符, 在使用这个操作的时候, Haskell会遍历整个的List
```haskell
[1; 2; 3; 4] ++ [9; 10; 11; 12]
```

我们可以使用`:`运算符号来降低时间的复杂度
```haskell
1 : 2 : []

--上面实际上是[1; 2; 3]的语法糖
```

`!!`用来表示取list中的元素

```haskell
[1]
```




每一个类型都具有一个类型接口

Intergral就是一个类型接口
```haskell
lucky :: (Integral a) => a -> String   
lucky 7 = "LUCKY NUMBER SEVEN!"   
lucky x = "Sorry, you're out of luck, pal!" 
```