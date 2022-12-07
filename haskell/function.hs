--输入三个整数, 返回一个整数
addThree :: Int -> Int -> Int -> Int
addThree x y z = x + y + z

--输入
factorial :: Integer -> Integer
factorial n = product [1..n]

circumference :: Float -> Float
circumference r = 2 * pi * r

circumference' :: Double -> Double
circumference' r = 2 * pi * r

---(==) :: Eq a => a -> a -> Bool
---表示类型限定
--(==) 1 1

lucky :: (RealFloat a) => a -> String
lucky 7.1 = "LUCKY"
lucky x = "Sorry, you're out of luck, pal!"

--模式匹配
factorial1 :: (Integral a) => a -> a
factorial1 0 = 1
factorial1 n = n * factorial1 (n - 1)

charName :: Char -> String
charName 'a' = "Alert"
charName 'b' = "Broseph"
charName 'c' = "Cecil"

--使用Tuple可以使用模式匹配
addVectors :: (Num a) => (a, a) -> (a, a) -> (a, a)
addVectors a b = (fst a + fst b, snd a + snd b)

---实现三元组的匹配
--返回第一个元素

first :: (a, b, c) -> a
first (x, _, _) = x

---返回第二个元素
second :: (a, b, c) -> b
second (_, y, _) = y

---返回第三个元素
third :: (a, b, c) -> c
third (_, _, c) = c

---使用模式匹配进行匹配链表
---


bmiTell :: (RealFloat a) => a -> String
bmiTell bmi
    | bmi <= 18.5 = "18.5"
    | bmi <= 25.0 = "25.0"
    | bmi <= 30.0 = "30.0"
    | otherwise = "You're whale, congratulations"