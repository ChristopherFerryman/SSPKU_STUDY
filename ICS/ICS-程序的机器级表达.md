# ICS-程序的机器级表达

## 从Data Lab说起

我想先从我自己的角度浅谈一下为什么要学习程序的机器级表达------在本章其实也就是汇编语言了。C语言已经是很底层的高级语言，然而有时候如果我们进行一些危险的“极限操作”时，会出现一些意料之外的结果，举一个例子，做`Data Lab`的时候，我想这样完成`isTmax`函数。

```c
int isTmax(int x) {
  return !(x+x+2);
}
```

但奇怪的是`INTMAX+INTMAX+2`的结果是0，`!(INTMAX+INTMAX+2)`的结果也还是0。看C程序我完全不理解这个结果（虽然这个行为确实很危险），我甚至猜测是不是在硬件上做逻辑否运算的时候它会考虑进位的`1`。现在让	我们来看看编译成汇编语言的结果

<img src="http://cdn.zhengyanchen.cn/img202210020047745.png" alt="截屏2022-10-02 00.47.16" style="zoom:50%;" />

1. 移动栈指针`%rsp`。
2. 把 `INTMAX`放进`Memory[%rbp-4]`
3. 比较`Memory[%rbp-4]`也就是`INTMAX`和-1的值
4. 如果两值相等，设置`%al`为1
5. 然后把`%al`的值移到`%eax`寄存器，`%eax`给出的就是需要返回的结果。

编译器的逻辑是这样的：`!(x+x+2)`这样表达式只有x=-1才能等于1，于是只要知道x和-1是否相等就可以给出结果了呀。这和我C程序的逻辑显然大相径庭，所以根本没必要把硬件想得很复杂。事实上，去做`INTMAX+INTMAX+2`这样溢出的危险操作在C语言里是未定义的，出现意外的结果才不意外，但倘若只告诉我们这种行为是**未定义的**这几个字恐怕是不能叫人信服的，编译结果给了我们最直观的解答。





## 算数和移动的操作符

<img src="http://cdn.zhengyanchen.cn/img202210141325927.png" alt="截屏2022-10-14 13.25.03" style="zoom:50%;" />

* 这都是双操作数的
* 









## Condition moves(`cmov`)及其坏结果

 为了便于流水线作业，编译器可能会让两分支都会运算，最后使用`cmov`(condition moves)指令来优化`if else`。

<img src="http://cdn.zhengyanchen.cn/img202209301734075.png" alt="截屏2022-09-30 17.34.45" style="zoom:30%;" />

`cmov`指令也可以用于优化`?:`表达式，但在一些例子里会导致出现一些坏结果。尤其是下图的第三种情况，如果使用`cmov`, 那么x将会参与多次运算，影响最后结果。

<img src="http://cdn.zhengyanchen.cn/img202209301620499.png" alt="截屏2022-09-30 16.20.11" style="zoom: 33%;" />

我运行了gcc(Ubuntu 9.4.0-1ubuntu1~20.04.1)编译器，先赋值`x=1`,再运行上图第三个语句的实际编译结果如下，可以看到在把x的初始化值放在栈区以后，做的第一件事就是比较x和0的大小，并没有先计算再使用`cmov` 。所以事实上，如果编译器足够聪明，它并不会去利用`cmov`对`?:`做优化。

```assembly
cmpl $0 -8(%rbp)//比较x和0的大小
```

<img src="http://cdn.zhengyanchen.cn/img202209301744162.png" alt="截屏2022-09-30 17.44.02" style="zoom:33%;" />

但以防万一，我们可以使用`volatile`关键字,这样就可以避免编译器利用`cmov`的优化。

```c
volatile int x,val;
val=x>0 ? x*=7 : x+=3;
```

*[此处参考](https://stackoverflow.com/questions/50455515/is-the-conditional-move-optimization-against-the-c-standard)*

## 跳转表

<img src="http://cdn.zhengyanchen.cn/img202209301655110.png" alt="截屏2022-09-30 16.55.06" style="zoom:40%;" />

向陈老师确定后，此处的`goto *JTab[x]`的注释是不严谨的。`Jab[x]`和`*(Jab+x)`等效，都可以表示取跳转表第x个地址，使用这两个取代注释都是可以。















## 栈

### the structure of stack

<img src="http://cdn.zhengyanchen.cn/img202210071416650.png" alt="截屏2022-10-07 14.16.28" style="zoom:20%;" />zzz

栈是虚拟内存的一块空间,栈的增长方向是由高到低，第一个栈的栈底位置是固定的，栈顶的地址由`%rsp`寄存器保存。

*src和dest是内存地址或者寄存器*

*  `pushq src`,`%rsp`减8，把src的val存在Memory[`%rsp`]。
*  `popq  dest`,把Memory[`%rsp`]放到dest里(通常是寄存器)。

### 通过栈控制指令流：callq和retq

 一个函数(`multstore`)调用另一个函数(`mult2`)需要使用  `callq`指令。

<img src="http://cdn.zhengyanchen.cn/img202210071449477.png" alt="截屏2022-10-07 14.49.41" style="zoom:20%;" />

*`%rip`是pc，存储当前执行指令的地址*

`callq` 做这几件事

* 将参数按照下图的顺序传递到寄存器里

  <img src="http://cdn.zhengyanchen.cn/img202210071523516.png" alt="截屏2022-10-07 15.23.22" style="zoom:33%;" />

  比如调用`mult2(x,y,z)`，则x放到`%rdi`,y放到`%rsi`,放到`%rdx`,以此类推

  但这里只有6个参数，如果调用的函数需要更多的参数怎么办？可以放到栈上。

*  将本函数中下一条指令的地址(400549)--return address，放到栈上

  `push %rip+1`(不严谨的表达)

​	**到这里为止，都还是调用者(caller) 的stack frame**

<img src="http://cdn.zhengyanchen.cn/img202210071540327.png" alt="截屏2022-10-07 15.40.25" style="zoom:25%;" />

* (Optional)将原来的caller栈底地址放在 `%rbp`( 栈底寄存器)现在指向的地址（return addr的下面）

* 跳转到`callq`后面跟的地址(400550)

  `jX 400550 `,此时`%rip`由400544变为400550

  

`retq`做镜像操作

* 弹出被调用函数(callee)的所有数据，`%rsp`减回`return address`的位置。
* 跳转到return address的位置，即caller的下一条指令。

###  通过栈存放寄存器数据和局部变量



<img src="http://cdn.zhengyanchen.cn/img202210071540327.png" alt="截屏2022-10-07 15.40.25" style="zoom:25%;" />

还是这张图，局部变量和寄存器的数值可以放在栈上，下一节哪些寄存器放在caller或者callee上。

### Caller saved and Callee saved

<img src="http://cdn.zhengyanchen.cn/img202210071602093.png" alt="截屏2022-10-07 16.02.43" style="zoom:23%;" />

* 数据保存在caller frame的寄存器

  <img src="http://cdn.zhengyanchen.cn/img202210071605818.png" alt="截屏2022-10-07 16.05.30" style="zoom:20%;" />

* 数据保存在callee frame的寄存器

  <img src="http://cdn.zhengyanchen.cn/img202210071609611.png" alt="截屏2022-10-07 16.09.18" style="zoom:20%;" />

  

### 通过栈实现递归

*插播复习一下，testq reg reg做的让reg和自己做`^`运算，并把结果放在ZF寄存器*

最后通过递归的例子来对这里进行总结

<img src="http://cdn.zhengyanchen.cn/img202210141306145.png" alt="截屏2022-10-14 13.06.49" style="zoom:43%;" />

本质来说，递归和前面所说的函数调用函数做的事没有任何区别，只是调用的次数我们事先没有预设好。观察三个寄存器

* `%rdi`,这是一个用于传递参数的寄存器，因为递归调用函数还需要它，所以需要它需要把参数交给`%rbx`保存。

  ```assembly
  movq %rdi %rbx
  ```

* `%rbx`这是一个callee saved  register,因为它需要保存这一层`%rdi`的数值，所以需要把它推到栈上。

* `%rax`这是一个保存函数返回值的寄存器，因为递归每次是自底而上地返回，每次都是caller覆盖callee,所以无需担心保存问题。

最后补充一个问题，**栈区的大小都是有限的（事实上，是被设定好了大小的）。所以如果出现无限递归的情况，那么很快就会出现栈溢出(stackflow)的错误，某种程度上来说，这是操作系统保护计算机的一种方式。**

这是一个例子：

<img src="http://cdn.zhengyanchen.cn/img202210141631408.png" alt="截屏2022-10-14 16.30.42" style="zoom:25%;" />



## Array

### Machine word

* 对于不同的计算机，word的大小不同(word大小和寄存器大小相同)

<img src="http://cdn.zhengyanchen.cn/img202210141411755.png" alt="截屏2022-10-14 14.11.43" style="zoom:33%;" />

* 字节内部排序不变，字节间排序分大小端[网络使用大端]，下图表示0x01234567

<img src="http://cdn.zhengyanchen.cn/img202210141415752.png" alt="截屏2022-10-14 14.15.13" style="zoom:24%;" />

### 不同的数组表示

<img src="http://cdn.zhengyanchen.cn/img202210141453719.png" alt="截屏2022-10-14 14.53.54" style="zoom:33%;" />

* nested array
* multi-level array





## Struct

* 结构体的内存结构

  <img src="http://cdn.zhengyanchen.cn/img202210141512443.png" alt="截屏2022-10-14 15.12.21" style="zoom:30%;" />

  

### Align(对齐)

<img src="http://cdn.zhengyanchen.cn/img202210141531098.png" alt="截屏2022-10-14 15.31.11" style="zoom:25%;" />

* 结构内部的对齐：如图是从unaligned data到aligned data,**对齐保证数据和它的地址是可以整除的**。比如int为4字节，则地址就需要为4的整数倍。不同的数据规则如下：

  <img src="http://cdn.zhengyanchen.cn/img202210141535521.png" alt="截屏2022-10-14 15.35.46" style="zoom: 20%;" />

* 结构本身在内存的对齐：

  <img src="http://cdn.zhengyanchen.cn/img202210141537540.png" alt="截屏2022-10-14 15.37.13" style="zoom:25%;" />

  规则和结构体内的规则类似，只不过现在要保证结构体的地址和**结构体内最大基本数据的长度**为整数倍

### 存在对齐，如何节省空间

<img src="http://cdn.zhengyanchen.cn/img202210141544689.png" alt="截屏2022-10-14 15.44.37" style="zoom:20%;" />



## Memory

<img src="http://cdn.zhengyanchen.cn/img202210141609682.png" alt="截屏2022-10-14 16.08.52" style="zoom:30%;" />

进程的内存布局 

* stack和heap的大小不确定，所以一个向下增长，一个向上增长。

### buffer overflow



