#include <iostream>
#include <memory>
#include <utility>

//此处为为了防止忘记delete而加以改装的版本，可以略过
using T = int;
class ListNode {
public:
    using Ptr = std::unique_ptr<ListNode>;
    T data;
    Ptr next_;
    ListNode(): next_(nullptr) {}
    ListNode(T val): data(val), next_(nullptr) {}
    ~ListNode() {std::cout << '*' << data << " destruct.* ";}

    void setNext(Ptr &&p) { next_ = std::move(p);}
    ListNode* getNext() { return next_.get();}
    static Ptr createNode(int data) {
        if (data == -1) {
            return std::make_unique<ListNode>();
        }
        else {
            return std::make_unique<ListNode>(data);
        }
    }

};

//以下是上面的等价形式
class ListNode_ {
public:
    using Ptr = ListNode_*;
    int data;
    Ptr next;
    ListNode_():next(nullptr) {}
    ListNode_(int data) : data(data),next(nullptr) {}
    ~ListNode_() {std::cout << '*' << data << " destruct.* ";}

    void setNext(Ptr p) { next = p;}
    Ptr getNext() { return next;}
    static Ptr createNode(int data) {
        if (data == -1) {
            return new ListNode_();
        }
        else {
            return new ListNode_(data);
        }
    }
};

void list() {
    ListNode head; //构造时确保next=nullptr

    //增加
    {

        head.setNext(ListNode::createNode(1));

        ListNode* ptr = head.getNext();
        for (int i=2;i <= 5;++i) {

            ptr->setNext(ListNode::createNode(i));
            ptr = ptr->getNext();
        }

    }
    //删除
    { //别看这个，这个只是用来运行的
        ListNode::Ptr &p = head.next_;
        head.setNext(std::move(p->next_));
        //p析构自动释放
    } //看下面的实现
//    {
//        ListNode_::Ptr p=head.next;
//        head.setNext(p->next);
//        delete p;
//    }

    //遍历
    auto traverse = [&head]() {
        for (ListNode* p = head.getNext();
             p != nullptr; p = p->getNext()) {
            std::cout << p->data << ' ';
        }
        std::cout << '\n';
    };
    traverse();

}