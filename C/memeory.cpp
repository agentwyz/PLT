#include <iostream>
#include <cstdio>

using namespace std;

//堆上的内存分配
bar* make_bar(...) {
    try {
        bar* ptr = new bar();
    }
    catch () {
        delete ptr;
        throw;
    }
    return ptr;
}

void foo() {
    bar* ptr = make_bar(...);
    delete ptr;         //
}


//析构函数
class Obj {
    public: 
        Obj() { puts("Obj");}
        ~Obj() { puts("Obj");}  //这个函数一定会执行的
};


enum class shape_type {
    circle,
    triangle,
    rectangle,
};

//下面是继承
class shape {...};
class circle: public shape {...};
class triangle : public shape {...};
class rectangle : public shape {...};

//注意返回值
shape* create_shape(shape_type type) {
    switch (type) {
        case shape_type::circle;
        return new circle();
    }
}

//实现智能指针
class shape_wrapper {
    public: 
        explict shape_wrapper(
            shape* ptr = nullptr)
        : ptr_(ptr) {}

        ~shape_wrapper() {
            delete ptr_;    //delete是删除那一片指向的类型
        }

        shape* get() const { return ptr_; }
    private:
        shape* ptr_;
};

template <typename T>
class smart_ptr {
    public:
        explict smart_ptr(T* ptr = nullptr)
            : ptr_(ptr) {}
        ~smart_ptr() {
            delete ptr_;
        }

        /*
        但是目前这个类的行为还是有一点问题的
        1. 不可以使用*运算符号解引用
        2. 不可以使用->运算符指向对象成员
        3. 不可以像指针一样使用在布尔表达式中
        */
        T* get() const { return ptr_; }
        T& operator* () const {return ptr_; }
        operator bool() const {return ptr_; }
    
    private:
        T* ptr_;
};


