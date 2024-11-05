#include <stdio.h>
#include <math.h>
int main()	
{
	int a,b,c;
	float p,q,x1,x2;
  printf("ax*x+bx+c=0\nPlease enter data(int).\na=");
  scanf("%d",&a);
  printf("b=");
  scanf("%d",&b);
  printf("c=");
  scanf("%d",&c);
    if(b*b-4*a*c>=0)
    {
	p=-b/(2*a);
    q=sqrt(b*b-4*a*c)/(2*a);
    x1=p-q;
    x2=p+q;
    printf("x1=%.2f\nx2=%.2f",x1,x2);
    }
    else printf("This equation hasn't real roots.");
  return 0;  
}
