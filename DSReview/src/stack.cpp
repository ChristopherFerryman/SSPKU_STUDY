#include <iostream>
#include <vector>

using std::vector;

void stack_dynamic() {
    //动态数组
    //优点：vector封装友好，仅需手动实现pop; 同时底层实现也很高效
    vector<int> s;

    auto pop = [&s] () {
        int t = s.back();
        s.pop_back();
        return t;
    };

    s.push_back(1);
    s.push_back(2);
    std::cout << "size of stack is " << s.size() << '\n';
    while (!s.empty()) {
        std::cout << pop() << ' ';
    }
    std::cout << '\n';
}

void stack_static_vector() {
    //静态数组
    //几乎与原生数组一致，仅为了解
    vector<int> s2;
    s2.resize(10);
    int p=0;
    auto push_static = [&s2,&p](int x) {
//        s2[p++]=x;
        s2.at(p++)=x; //at自带下标越界检查，更安全
    };
    auto pop_static = [&s2,&p]() {
        return s2[--p];
    };
    auto empty = [&p]() {
        return p <= 0;
    };

    push_static(1);
    push_static(2);
    std::cout << "size of stack is " << p << '\n';
    while (!empty()) {
        std::cout << pop_static() << ' ';
    }

    std::cout << '\n';
}

void stack_static() {
    //原生数组
    //优点：代码少
    //缺点：手动编写时容易出现越界
    int s[10];
    int p=0;
    auto push_static = [&s,&p](int x) {
        s[p++]=x;
    };
    auto pop_static = [&s,&p]() {
        return s[--p];
    };
    auto empty = [&p]() {
        return p <= 0;
    };

    push_static(1);
    push_static(2);
    std::cout << "size of stack is " << p << '\n';
    while (!empty()) {
        std::cout << pop_static() << ' ';
    }

    std::cout << '\n';
}
