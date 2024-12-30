

#include <stdio.h>
#include <float.h> // provides FLT_EPSILON


int main(){

  // "simple constant"
  // note: a=0.1 cannot be represented exactly, because 
  //       it's not a power of two! (Try a=0.5 instead.)

  float a = .1;  printf("simple constant: a = %.15e\n",a);


  // value close to the smallest denormalized value. For IEEE754 
  // single precision standard, that value is close to 1.4e-45. 
  // The next smallest value that can be represented is twice that!

  //  float b = 1.4e-45;   printf("very small value: b = %.15e\n",b);


  // value close to the largest number that can be represented. For IEEE754 
  // single precision standard, that value is close to 3.403e+38. 
  // If you increase the value slightly, you get infinity

  //  float c = 3.402e38;  printf("very large value: c = %.15e\n",c);


  // machine epsilon: eps=2^{-24}=5.96046e-08
  // rd(1+5.96e-08) = 1, but
  // rd(1+5.97e-08) > 1.

  //  float d = 1+5.97e-08;  printf("Check Machine Epsilon: eps = %.15e\n",d);

  // note: float.h provides a machine epsilon according
  // to definition "spacing of floating point numbers 
  // to the right of 1" (i.e. eps=2^{-23})

  //  printf("float.h Epsilon: eps = %.15e\n",FLT_EPSILON);
  
   
   // when is Dealt(x)=1? depends on the length of the 
   // significand (mantissa): 2^24 for single precision

  /* float x = 1; */
  /* int i = 0; */
  /* while (x != x+1) { */
  /*   x = x*2; */
  /*   i = i+1; */
  /* } */
  /*
  /* printf("Delta(x) > 1 for x > %.15e\n",x); */
  
}
