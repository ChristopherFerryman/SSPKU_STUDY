# Data Lab

*基于Ubuntu20.04.4*

## 补充的环境问题

* 使用ubuntu虚拟机，使用`make`出现以下的问题

  ```shell
  fatal-error-bits-libc-header-start-h-no-such-file-or-directory-while-compiling
  ```

  解决方法在[solution](https://stackoverflow.com/questions/54082459/fatal-error-bits-libc-header-start-h-no-such-file-or-directory-while-compili),这样就安装好了32位的库，可以`make`了。

  ```shell
  sudo apt-get install gcc-multilib
  ```

* <img src="http://cdn.zhengyanchen.cn/img202210061933324.png" alt="截屏2022-10-06 19.32.50" style="zoom:50%;" />

  由于linux gcc编译器支持的C89标准，所以如果有定义变量需要定义函数的最前面

  

  

## 自我验证

* 每次写完一个函数之后，都需要在`shell`输入：

```shell
make
```

 进行重新编译。

现在输入

```shell
./btest
```

这样就可以进行查看所有函数的得分，如果希望查看单个函数的得分,比如`bitXor`输入

```
./btest -f bitXor
```

* 由于Lab有格式要求和操作符限制，可以输入

```shell
./dlc bits.c
```

查看是否有格式或者操作符的问题。如果无事发生，就说明一切OK。

## 关于使用更少的操作数

[该网站列出了使用最少的操作数](http://rtsys.informatik.uni-kiel.de/~rt-teach/ss09/v-sysinf2/dlcontest.html)

## 参考解答

网上资源很多，只是分享个人的参考解答，欢迎来和我交流。

### bitXor

* `x^y= (~x&y)|(~y&x)=~~((~x&y)|(~y&x))=~((~(~x&y))&(~(~y&x)))`

```c
int bitXor(int x, int y) {
  return ~((~(~x&y))&(~(~y&x)));
}
```

### tmin

* 最小的int的二进制表达是1000 0000 0000 0000 0000 0000 0000 0000

```c
int tmin(void) {
  return 1<<31;
}
```

### isMax

* 最大的int的二进制表达是0111 1111 1111 1111 1111 1111 1111 1111	，两倍数值相加+1刚好等于0x00000000，然后进行逻辑取反就可以了。

```c
int isTmax(int x) {
  return !(~(x+x+1)) & (!!(x+1));
}
```

1. 第一部分是主要思路。
2. 第二部分是排除0xFFFFFFFF

### allOddBits

一开始的思路是这样的

```c
int allOddBits(int x) {  
	return !((0xAAAAAAAA&x)^0xAAAAAAAA);
}
```

* 让0xAAAAAAAA这个所有奇数位都为1，偶数为0的数字和x做&运算，再判断它是否和0xAAAAAAAA相等。

* 判断x,y 是否相等的方法：`!(x^y)`

但验证时常数限制，只能使用0x00~0xFF的常数，改为如下

```c
int allOddBits(int x) {  
  int a=(0xAA<<8)+0xAA;
  int b=(a<<16)+a;
  return !((b&x)^b);
}
```

### negate

```c
int negate(int x) {
  return (~x)+1;
}
```

* 这就不多解释了

### isAsciiDigit

 ```c
 int isAsciiDigit(int x) {
   return  (!(x>>6))&(!(x>>3^0x6)|!(x>>1^0x1C)); 
 }
 ```

* 0x30~0x39对应的二进制范围为(只看最后的6bit)110000 ~ 111001
* 1. 首先需要满足第一个条件那就是前26位为0---`!(x>>6))`
  2. 现在继续观察这个范围的数字
     * 110开头的，后面无所谓---`!(x>>3^0x6)`
     * 111开头的，最后一位是1或者0，中间两位为0---`!(x>>1^0x1C)`

### conditional

 ```c
 int conditional(int x, int y, int z) {
   return  ((((1<<31)>>31)+!x)&y)+((((1<<31)>>31)+(!!x))&z);
 }
 ```

* 由于没法使用大常量，只能先通过`((1<<31)>>31)`获得0xFFFFFFFF

* 我的思路是 ：如果x为非0值，要获得y消去z

  1. x逻辑取反加0xFFFFFFFF得到0xFFFFFFFF，用以保留y
  2. x两次逻辑取反加0xFFFFFFFF得到0x00000000，用以消除z

  反之亦然。

* 这里可以通过把`((1<<31)>>31)`先赋给一个变量来减少操作符

###  isLessOrEqual

 ```c
 int isLessOrEqual(int x, int y) {
    return (!(x+((~y)+1))) | ((x>>31)& (!(y>>31))) | (!((x>>31)^(y>>31)) & !((y+(~x+1))>>31)); 
 }
 ```

x<=y，分三种情况考虑

* 如果x和y相等 ：`!(x+((~y)+1)))`

* 如果x为负数,y为正数：` ((x>>31)& (!(y>>31))) `

* 1. 如果x和y同符号，`!((x>>31)^(y>>31))`

     且

  2. y-x>0 ,`!((y+(~x+1))>>31)`

### logicalNeg

```c
int   logicalNeg(int x) {
  return ((x|((~x)+1))>>31)+1;
}
```

* 可以考虑这样一件事，任何非零数的符号位都应该是和它的相反数符号位相异的，这意味着非零x和~x+1符号位必然有一个为1。而0相反数就是自己，符号位始终为0。

### howManyBits

这道题我无法做到在限制的操作符数内完成，还是使用了for循环，可以参考其他的答案。

```c
int howManyBits(int x) {
    int find=(x>>31);
  int ans=0;
  int con=(1<<31)>>31;
  int i;
  for ( i = 31; i>=0; i--)
  {
      int boo=!((x>>i)^find);
      ans=(((!boo+con)&(i+1)) | ((boo+con)&ans));
  }
  return ans;
}
```

之后再思考思考

*在做浮点数前简单复习一下*

### Review Float

<img src="http://cdn.zhengyanchen.cn/img202209161610856.png" alt="截屏2022-09-16 16.10.05" style="zoom:33%;" />

浮点数(32bit)的表示分为三个部分

* sign bit `s` (1 bit	)

* exponent `exp` (8 bit)

* frac`M` (23 bit)

  *bias=127 (32 bit float)*

计算公式为$v=(-1)^s*M*2^{E}$,其中`E=exp-bias`，在32bit浮点数中，bias=127. 

根据`exp`的值，分为三种情况

| exp               | 名称       | E                   | M      | Val                                              |
| ----------------- | ---------- | ------------------- | ------ | ------------------------------------------------ |
| 00000000          | 非规格化数 | 1-bias(not  0-bias) | 0.frac | $v=(-1)^s*M*2^{E}$                               |
| 00000001~11111110 | 规格化数   | exp-bias            | 1.frac | $v=(-1)^s*M*2^{E}$                               |
| 11111111          | 特殊值     |                     |        | if frac=000..00, 则$v=(-1)^s\infty$;else $v=NaN$ |



### floatScale2

```c
unsigned floatScale2(unsigned uf) {

  int sign=uf>>31;
  int frac=uf&0x007FFFFF;
  int exp=(uf>>23)&0xFF;
  if (exp==0xFF){
    if (frac!=0) return uf;
  }else if(exp==0x00){
    if (frac==0) {
      sign=0;
      return uf;
    }
    if ((frac+frac)>>23){
      exp+=1;
      frac<<=1;
    }else{
      frac<<=1;
    }   
  }else{      
    exp+=1;
  }
  return (frac | exp<<23 | sign<<31);

}
```

思路如下

* 对特殊值的处理

* 对非规格数：（这里需要考虑多种情况）

  1. frac=0。直接返回原值就好

  2. 乘2后，非规格数过渡到规格数

     这里举一个8bit例子来观察，可以看到由于非规格数E的值和exp=1的规格数E的值相同，所以非规格数$\dfrac{7}{512}$乘2得到规格数$\dfrac{14}{512}$就等效于exp和frac两部分左移了一位。

     | 位表示     | Exp  | E    | M               | V                 |
     | ---------- | ---- | ---- | --------------- | ----------------- |
     | 0 0000 111 | 0    | -6   | $\dfrac{7}{8}$  | $\dfrac{7}{512}$  |
     | 0 0001 000 | 1    | -6   | $\dfrac{8}{8}$  | $\dfrac{8}{512}$  |
     | 0 0001 110 | 1    | -6   | $\dfrac{14}{8}$ | $\dfrac{14}{512}$ |

  3. 其他非规格数，左移一位frac就好了

* 对规格数，直接exp加1就达到浮点数乘2的效果了



### floatFloat2Int



```c
int floatFloat2Int(unsigned uf) {
  int sign=uf>>31;
  int frac=uf&0x007FFFFF;
  int exp=(uf>>23)&0xFF;
  int ans;
  int move;
  if(exp==0xFF)return 0x80000000;
  if (exp!=0x00) frac+=0x800000;//1
  move=exp-127-23;//2
  
  // find the highest bit==1
  int highbit=24;
  while (!(frac>>(highbit-1)))
  {
    highbit--;
    if (highbit==0) break;
  }
  
  if ((highbit+move)<=0){
    ans=0;
  }else if ((highbit+move)>31){
    return 0x80000000;
  }else{
    if (move<=0)ans=frac>>(-move);
    else ans=frac<<move; 
  }
  
  if (sign && ans!=0) ans=-ans;
  
  return ans;
}
```

这题我觉得是比较麻烦的。具体来说，需要考虑这样几个问题

1. uf是否为规格数，这决定了是否需要对frac做处理 ，见1处。
2. frac相当于已经左移了23位，所以实际的移位需要减去23，见2处。
3. 需要找到frac最高为1的位，因为这一位决定了移位后是否会溢出。
4. 最后考虑符号位。



### floatPower2

```c
unsigned floatPower2(int x) {
    if(x>127) return 0x7F800000;
    if(x<-149) return 0;
    int frac=0x800000;
    int exp;
    if (x>=-126 && x<=127){
      frac=0;
      exp=x+127;
    }else{
      exp=0;
      frac>>=(-126-x);
    }
    return (exp<<23 | frac);
}

```

* 最后这一道反而简单了,只需要判断$2^n$的上下限
  1. 上限 exp=127,M=1.000...0
  2. 下限 exp=-126,M=0.000...1

* 然后对非规格数和规格数做分类讨论即可

* 最后提交的时候显示超时，修改一下`btest.c`的时间限制为20就pass了

  ```c
  #define TIMEOUT_LIMIT 20
  ```



## 验证结果

* 形式上除了`howManybits`都是OK的<img src="http://cdn.zhengyanchen.cn/img202210061942207.png" alt="截屏2022-10-06 19.42.20" style="zoom:50%;" />

* 验证结果都是ok<img src="http://cdn.zhengyanchen.cn/img202210061944893.png" alt="截屏2022-10-06 19.44.02" style="zoom:50%;" />
