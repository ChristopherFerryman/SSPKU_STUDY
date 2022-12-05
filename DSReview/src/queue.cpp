#include <iostream>
#include <vector>

using std::vector;

void queue_dynamic() {
    std::vector<int> queue;

    auto enqueue = [&queue](int x) {
        queue.push_back(x);
    };

    auto dequeue = [&queue]() {
        int x = queue.at(0);
        queue.erase(queue.begin());
        return x;
    };

    enqueue(1);
    enqueue(2);
    std::cout << "size of queue is " << queue.size() << '\n';

    while (!queue.empty()) {
        std::cout << dequeue() << ' ';
    }
    std::cout << '\n';

}

void queue_static() {
    int Q[100];
    int front,rear;
    front=rear=0;

    auto empty=[](int front,int rear) {
        return rear<=front;
    };

    auto enqueue=[](int x,int Q[],int &rear) {
        Q[rear++]=x;
    };

    auto dequeue=[&empty](int Q[],int &front,int &rear) {
        if (!empty(front,rear)) {
            int f = Q[front++];
            return f;
        }
    };


    enqueue(1,Q,rear);
    enqueue(2,Q,rear);
    std::cout << "size of queue is " << rear-front << '\n';

    while (!empty(front,rear)) {
        std::cout << dequeue(Q,front,rear) << ' ';
    }
    std::cout << '\n';

}
