length :: [a] -> Intger
length [] = 0
length (x:xs) = 1 + length xs

head :: [a] -> a
head (x : xs) = x

tail :: [a] -> a
tail (x : xs) = xs


--the a is too generial
--the integer is so spacific

--user defined types
--we can defined own types in haskell using a data declearaton
--which we introduce via a series of examples


data Bool = False | True
--type bool is an example of a (nullary)
--in computer programming a nullary constructor that takes no arguments
--Also known as a 0-argument constructor or no-argument construtors

--public class Example {
--    protected int data;

--    public Example() {
--        this(0);
--    }

--}

data Color = Red | Green | Blue | Indigo | Violet

data Point a = Pt a a
