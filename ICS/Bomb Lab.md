# Bomb Lab

*基于ubuntu20.04*

## Intro

bomb lab分成6阶段，每个部分都需要你输入一个特定的字符串，如果是正确，就进入下一阶段，否则炸弹就会爆炸。

## 准备工作

* 安装gdb

  ```shell
  sudo apt-install gbb
  ```

* 安装objdump

## 文件

* `bomb.c`

  这是C程序文件，注意看，在每一个阶段，都需要通过`read_line()`输入内容，将该内容`input`作为参数传到`phase_1`里。如果你传的参数是正确的，那么就可以进入下一个阶段，不正确的话，就会爆炸。有趣的是，在文件里并没有给出`phase_1`函数的定义（事实上就是它没有给出包含了函数的头文件）。所以我们面临的本质来说就是一个黑盒，怎么拆炸弹(输入正确的参数)就要看`bomb`

  ```c
   input = read_line();             /* Get input                   */
   phase_1(input);                  /* Run the phase               */
   phase_defused();                 /* Drat!  They figured it out! */
  ```

  

* `bomb` 是一个二进制的可执行文件，我们需要反汇编它得到汇编文件才能阅读

  ```shell
  objdump -d bomb > bomb.txt
  ```

  这时候就得到了`bomb.txt`。打开`bomb.txt`就可以阅读`bomb.c`中`phase_1`函数对应的汇编部分了

  

## Solution

### phase_1

来看`bomb.txt`的`phase_1` 部分

```assembly
0000000000400ee0 <phase_1>:
  400ee0:	48 83 ec 08          	sub    $0x8,%rsp
  400ee4:	be 00 24 40 00       	mov    $0x402400,%esi
  400ee9:	e8 4a 04 00 00       	callq  401338 <strings_not_equal>
  400eee:	85 c0                	test   %eax,%eax
  400ef0:	74 05                	je     400ef7 <phase_1+0x17>
  400ef2:	e8 43 05 00 00       	callq  40143a <explode_bomb>
  400ef7:	48 83 c4 08          	add    $0x8,%rsp
  400efb:	c3                   	retq   
```

我们可以先回顾一下寄存器传参数的时候传第一个参数使用的是`%rdi`寄存器，所以这时候我们输入的`input`已经在`%rdi`里。

* 压栈不再赘述

* 然后把0x402400放进`%rsi`寄存器里

* 调用`string_not_equal`，这个函数比较复杂，我们先从名称上可以判断这是一个比较两个字符串是否相等的函数。鉴于 `%rdi`保存着我们传入的参数，`%rsi`也保存了值，那么我们可以猜测==该函数比较的正是这两个值作为地址指向的字符串==。（关于这个函数的汇编解释写在最后）

  **在C语言中，字符串就是地址**

  *复习一下，函数传参数的寄存器顺序如下，比如phase_1(input)的参数就保存在%rdi里面，string_not_equal(x,y)两个参数分别就应该保存在%rdi,%rsi寄存器里*

  <img src="http://cdn.zhengyanchen.cn/img202210071523516.png" alt="截屏2022-10-07 15.23.22" style="zoom:33%;" />

  现在通过gdb来看看`0x402400`指向的字符串是什么， shell键入`gdb bomb`

  依次键入以下指令（不需要`c`orz）

  <img src="http://cdn.zhengyanchen.cn/img202210141822577.png" alt="截屏2022-10-14 18.22.44" style="zoom:25%;" />

  * 在`phase_1`处设置断点
  * `r` 运行
  * 会出现`Have a nice day` ,现在随便输入什么都可以，回车，这时候就进断点了

  现在查看内存

  ![截屏2022-10-14 18.25.00](http://cdn.zhengyanchen.cn/img202210141825178.png)

  好的，找到字符串了。

*  `test   %eax,%eax
   je     400ef7 <phase_1+0x17>`

  做的就是判断`string_not_equal` 返回值是否为0(即两个字符串相等)，如果为0，则跳到最后弹栈返回，否则，就会进入`explode_bomb`, 爆炸！

所以现在我们知道，只有键入相同的字符串才能不爆炸。试一试，退出gdb，回到shell,键入

```shell
./bomb
```

<img src="http://cdn.zhengyanchen.cn/img202210141828557.png" alt="截屏2022-10-14 18.28.15" style="zoom: 25%;" />

再输入我们找到的字符串就过了第一关了。

### phase_2

来继续进入`phase_2`,

```c
0000000000400efc <phase_2>:
  400efc:	55                   	push   %rbp
  400efd:	53                   	push   %rbx
  400efe:	48 83 ec 28          	sub    $0x28,%rsp
  400f02:	48 89 e6             	mov    %rsp,%rsi
  400f05:	e8 52 05 00 00       	callq  40145c <read_six_numbers>
```

* 将`%rbp`和`%rbx`压栈
* 改变栈指针`%rsp`
* ==`%rsi`=`%rsp`==,注意`$rsi`这个寄存器将用在`read_six_numbers`
* 下面进入`read_six_numbers`

```c
000000000040145c <read_six_numbers>:
  40145c:	48 83 ec 18          	sub    $0x18,%rsp //栈移动24个字节
  401460:	48 89 f2             	mov    %rsi,%rdx 
 	//%rdx=%rsi
  401463:	48 8d 4e 04          	lea    0x4(%rsi),%rcx 
  //%rcx=%rsi+0x4
  401467:	48 8d 46 14          	lea    0x14(%rsi),%rax
  40146b:	48 89 44 24 08       	mov    %rax,0x8(%rsp) 
  //M[%rsp+0x8]=%rsi+0x14
  401470:	48 8d 46 10          	lea    0x10(%rsi),%rax
  401474:	48 89 04 24          	mov    %rax,(%rsp)
  //M[%rsp]=%rsi+0x10
  401478:	4c 8d 4e 0c          	lea    0xc(%rsi),%r9 
  //%r9=%rsi+0xc
  40147c:	4c 8d 46 08          	lea    0x8(%rsi),%r8
  //%r8=%rsi+0x8
  401480:	be c3 25 40 00       	mov    $0x4025c3,%esi
  //%rsi=0x4025c3
  401485:	b8 00 00 00 00       	mov    $0x0,%eax
  //%eax=0
  40148a:	e8 61 f7 ff ff       	callq  400bf0 <__isoc99_sscanf@plt>
  40148f:	83 f8 05             	cmp    $0x5,%eax
  401492:	7f 05                	jg     401499 <read_six_numbers+0x3d>
  401494:	e8 a1 ff ff ff       	callq  40143a <explode_bomb>
  401499:	48 83 c4 18          	add    $0x18,%rsp
  40149d:	c3                   	retq   
```

​	这个函数在为系统调用函数`__isoc99_sscanf@plt`（用户空间不可见）做准备，`__isoc99_sscanf`定义如下,作用就是将`s`字符串传进`arg`的参数指向的地址里

由于`%rdi`和`%rsi`已经作为第一和第二个参数传递，所以之后`arg`（具体到`read_six_numbers`就有6个）依次为==(我将内存地址为a的数据简化为M[a] )==

1. `%rdx` 
2. `%rdx`
3. `%r8`
4. `%r9`
5. `M[%rsp]`
6. `M[%rsp+0x8]`

```c
int
__isoc99_sscanf (const char *s, const char *format, ...)
{
  va_list arg;
  int done;

  va_start (arg, format);
  done = __isoc99_vsscanf (s, format, arg);
  va_end (arg);

  return done;
}
```

1. 为了让传参更直观一点，还可以这样不严谨的写成

   `__isoc99_vsscanf(%rdi,%rsi,%rdx,%rcx,%r8,%r9,M[%rsp],M[%rsp+8])`

2. 还记得`phase_2`最初的传进的地址(字符串)还一直保存在`%rdi`，这一点并没有变，它将作为`__isoc99_vsscanf `的第一个参数。

3. 这里会把6个参数放到`phase_2`的栈上，==此时`%rsi`保存的是`phase_2`里的`%rsp`==,所以这样传参。

   ```assembly
   %rdx=%rsi
   %rcx=%rsi+0x4
   %r8=%rsi+0x8 
   %r9=%rsi+0xc
   M[%rsp]=%rsi+0x10
   M[%rsp+0x8]=%rsi+0x14
   ```

<img src="http://cdn.zhengyanchen.cn/img202210142015900.png" alt="截屏2022-10-14 20.15.17" style="zoom:40%;" />

说实话因为函数`__isoc99_sscanf`对用户不显示(它将地址为`%rdi`的字符串，分别传给6个参数)，我们并不需要知道`phase_2`调用`read_six_numbers`的具体做法。==实际效果是，调用完`read_six_numbers`回到`phase_2` 的时候，栈上的已经得到这6个参数（如上图），每个参数是4个字节==。

**到这里，我们可以知道这次过第二关需要的是6个参数**

现在可以继续读`phase_2`

```assembly
	400f0a:	83 3c 24 01          	cmpl   $0x1,(%rsp)
  400f0e:	74 20                	je     400f30 <phase_2+0x34>
  400f10:	e8 25 05 00 00       	callq  40143a <explode_bomb>
```

* 比较M[%rsp]和1的值，（==特别注意，这里是`cmpl`，这意味比较了4个字节，因为数据是4字节的==）如果相等，则跳到0x400f30,否则下一步又进入`explode_bomb`,bomb,爆炸！到这里，似乎又找到拆弹的key。

  <img src="http://cdn.zhengyanchen.cn/img202210142217772.png" alt="截屏2022-10-14 22.17.07" style="zoom: 60%;" />

  即放在M[%rsp]*4个字节*的应该是0x1,这意味着第一个参数为1。

跳到0x400f30后

```assembly
 	400f30:	48 8d 5c 24 04       	lea    0x4(%rsp),%rbx
  400f35:	48 8d 6c 24 18       	lea    0x18(%rsp),%rbp
  400f3a:	eb db                	jmp    400f17 <phase_2+0x1b>
```

最开始的压栈的两个寄存器`%rbx`和`%rbp`现在派上了用场

跳到0x400f17后,

```assembly
	400f17:	8b 43 fc             	mov    -0x4(%rbx),%eax
  400f1a:	01 c0                	add    %eax,%eax
  400f1c:	39 03                	cmp    %eax,(%rbx)
  400f1e:	74 05                	je     400f25 <phase_2+0x29>
  400f20:	e8 15 05 00 00       	callq  40143a <explode_bomb>
```

* <img src="http://cdn.zhengyanchen.cn/img202210142217772.png" alt="截屏2022-10-14 22.17.07" style="zoom: 60%;" />

  做的就是比较2*M[%rsp]和M[%rsp+4]的值，如果相等就可以不爆炸，这样我们就可以知道第二个参数为第一参数的两倍。

跳到0x400f25

```assembly
	400f25:	48 83 c3 04          	add    $0x4,%rbx
  400f29:	48 39 eb             	cmp    %rbp,%rbx
  400f2c:	75 e9                	jne    400f17 <phase_2+0x1b>
  400f2e:	eb 0c                	jmp    400f3c <phase_2+0x40>
```

* 结合0x400f17的代码，逻辑可以表述为

  ```c
  for(int %rbx=%rsp+4;%rbx<%rsp+24;%rbx+=4){
    if (M[%rbx-4]*2!=M[%rbx]){
      bomb!
  	}
  }
  ```

  不断迭代，需要每个参数都是前一个的两倍才能不爆炸，`phase_2`的答案已经显而易见。这次在`input.txt`文件里写

  ```
  Border relations with Canada have never been better.
  1 2 4 8 16 32
  ```

  再键入`./bomb input.txt`就通过了第二关

<img src="http://cdn.zhengyanchen.cn/img202210142246826.png" alt="截屏2022-10-14 22.46.37" style="zoom:50%;" />

### phase_3















