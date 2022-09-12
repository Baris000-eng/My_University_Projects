#include "io.h";
#include "age.h";


int main(){
  int birthYear= ask_birth_year();
  int age=calculate_age(birthYear);
  print_age(age);
  return 0;
}