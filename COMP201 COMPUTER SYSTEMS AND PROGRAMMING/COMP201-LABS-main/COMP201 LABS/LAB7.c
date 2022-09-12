

int foo3(int num1,int num2){

  return num1*num2;

}


int foo2(int a,int b,int c){
   return foo3(a,b+c);
}

int foo() {
   int input1= 1;
   int input2= 2;
   int input3= 3;
   return foo2(input1,input2,input3);
  
}
