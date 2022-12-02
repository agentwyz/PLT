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