#include<stdio.h>
#include <malloc.h>
#include<stdlib.h>
struct Node {
	int Data;						// 存放节点中数据类型，此时为整型			
	struct Node* next;				//存放后继节点
	struct Node* prev;				//存放前驱节点，不一定用到
};
typedef struct Node* LinkList;		//定义指向结构体的指针类型
void GenerateNode(LinkList& L, int data);//生成单链表的函数，data为节点个数
void GenerateDulNode(LinkList& L, int data);//生产双链表的函数，data为节点个数
void Sort(LinkList& L,int nodenum);//排序，这里用冒泡
/*生成链表的基本操作*/

void DeleteMaxMin(LinkList& L, int max, int min); //作业第五题，采用双链表操作
void ReturnMaxNode(LinkList& L);//作业第四题
void ReverseList(LinkList& L);//作业第三题
void ConcatTwoList(LinkList& L1, LinkList& L2);//作业第二题

int main()
{


	/*********************以下为作业内容***********************/


	printf("\n/*********************作业第一题***********************/\n");
	LinkList ds1, ds2,out;
	int nodenum1 = 12, nodenum2 = 11;
	printf("第一个随机生成的链表为：\n");
	GenerateNode(ds1, nodenum1);
	printf("\n");
	printf("第二个随机生成的链表为：\n");
	GenerateNode(ds2, nodenum2);
	printf("\n");
	Sort(ds1, nodenum1);
	Sort(ds2, nodenum2);
	ConcatTwoList(ds1, ds2);
	printf("按照要求处理好的新链表为：\n");
	out = ds1;
	while (out)
	{
		printf("%d ", out->Data);
		out = out->next;
	}
	printf("\n/*********************作业第一题***********************/\n\n");
	

	printf("\n/*********************作业第二题***********************/\n");
	ReverseList(ds1);
	printf("\n/*********************作业第二题***********************/\n\n");

	printf("\n/*********************作业第三题***********************/\n");
	LinkList s;
	int nodenum = 17;
	GenerateNode(s, nodenum);
	ReturnMaxNode(s);
	printf("\n/*********************作业第三题***********************/\n\n");

	printf("\n/*********************作业第四题***********************/\n");
	LinkList h4;
	GenerateNode(h4, 14);
	ReverseList(h4);
	printf("\n/*********************作业第四题***********************/\n\n");




	printf("\n/*********************作业第五题***********************/\n");
	LinkList h5;
	GenerateDulNode(h5, 15);
	Sort(h5,15);
	DeleteMaxMin(h5, 182, 31);
	printf("\n/*********************作业第五题***********************/\n");

}
void GenerateNode(LinkList& L, int data)
{
	L = (LinkList)malloc(sizeof(Node));
	LinkList r, h;
	r = h = L;
	do
	{
		LinkList s = (LinkList)malloc(sizeof(Node));
		s->Data = rand()/100;
		r->next = s;
		r = s;
		data--;
	} while (data > 0);
	L = L->next;
	h->next = NULL;
	free(h);
	r->next = NULL;
	LinkList out = L;
	while (out)
	{
		printf("%d ", out->Data);
		out = out->next;
	}
	printf("\n");
}
void GenerateDulNode(LinkList& L, int data)
{
	L = (Node*)malloc(sizeof(Node));
	LinkList temp = L;
	temp->next = temp->prev = NULL;
	data;
	for (; data > 0; data--)
	{
		Node* s = (Node*)malloc(sizeof(Node));
		s->Data = rand() / 100;
		temp->next = s;
		s->prev = temp;
		temp = s;
	}
	temp->next = NULL;
	LinkList head = L;
	L = L->next;
	head->next = NULL;
	free(head);
	L->prev=NULL;
	LinkList out = L;
	while (out)
	{
		printf("%d ", out->Data);
		out = out->next;
	}
	printf("\n");
}

void ReturnMaxNode(LinkList& L)
{
	LinkList p1 = L;
	LinkList p2 = L;
	int temp = 0, max = 0,count=0;
	while (p1)
	{
		temp++;
		if (p1->Data > max)
		{
			max = p1->Data;
			count = temp;
		}
		if (p1->next)
			p1 = p1->next;
		else
			break;
	}
	printf("\n该链表中的最大值为%d,位于第%d位", max, count);
}
void ReverseList(LinkList& L)
{
	LinkList p1, p2, temp, out = L;
	printf("Before Reverse:\n");
	while (out)
	{
		printf("%d ", out->Data);
		out = out->next;
	}
	if (L->next == NULL)
	{
		printf("\nReverse Error!");
		return;
	}
	temp = (LinkList)malloc(sizeof(Node*));
	p1 = L, p2 = L->next;
	p1->next = NULL;
	while (p2->next)
	{
		temp = p2->next;
		p2->next = p1;
		p1 = p2;
		p2 = temp;
		if (temp->next)
			temp = temp->next;
	}
	p2->next = p1;
	L = p2;
	printf("\nAfter Reverse:\n");
	while (p2)
	{
		printf("%d ", p2->Data);
		p2 = p2->next;
	}
}
void DeleteMaxMin(LinkList& L, int max, int min)
{
	LinkList p = L;
	printf("\nBefore Delete(range:[%d,%d]):\n", min, max);
	while (p)
	{
		printf("%d ", p->Data);
		p = p->next;
	}
	L = L->next;
	while (L->next)
	{
		if ((L->Data >= min) && (L->Data <= max))
		{
			if (L->next)
			{
				LinkList a;
				a = L;
				a->prev->next = a->next;
				a->next->prev = a->prev;
			}
		}
		if (L->next)
			L = L->next;
	}
	while (L->prev)
		L = L->prev;
	LinkList out = L;
	printf("\nAfter Delete(range:[%d,%d]):\n", min, max);
	while (out)
	{
		printf("%d ", out->Data);
		out = out->next;
	}
}
void ConcatTwoList(LinkList& L1, LinkList& L2)
{
	LinkList head = (LinkList)malloc(sizeof(Node));
	LinkList cur = head;
	while (L1 && L2)
	{
		if (L1->Data < L2->Data)
		{
			if (cur->Data == L1->Data)
				L1 = L1->next;
			else
			{
				cur->next = L1;
				cur = cur->next;
				L1 = L1->next;
			}
		}
		else
		{
			if (cur->Data == L2->Data)
				L2 = L2->next;
			else
			{
				cur->next = L2;
				cur = cur->next;
				L2 = L2->next;
			}	
		}
	}
	if (L1)
	{
		while (L1)
		{
			if (L1->Data == cur->Data)
				L1 = L1->next;
			else
			{
				cur->next = L1;
				L1 = L1->next;
			}
		}
	}
	if (L2)
	{
		while (L2)
		{
			if (L2->Data == cur->Data)
				L2 = L2->next;
			else
			{
				cur->next = L2;
				L2 = L2->next;
			}
		}
	}
	L1 = head->next;
	head->next = NULL;
	free(head);
}
void Sort(LinkList& L,int nodenum)
{
	LinkList p1 = L, p2 = L;
	int i=nodenum,temp=0;
	for (; i > 0; i--)
	{
		while (p2)
		{
			if (p2->Data < p1->Data)
			{
				temp = p2->Data;
				p2->Data = p1->Data;
				p1->Data = temp;
			}
			if (p2->next)
				p2 = p2->next;
			else
				break;
			if (p1->next)
				p1 = p1->next;
		}
		p1 = L, p2 = L->next;
	}
}