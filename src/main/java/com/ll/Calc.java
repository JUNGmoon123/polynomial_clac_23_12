package com.ll;

public class Calc {
  public static int run(String exp) {
  
    exp = exp.replaceAll("\\- ", "\\+ \\-"); //- 를 + - 문자로 바꿈, Test에 수식에 띄어쓰기 있어서 포함시킴

    //replaceAll 로인해 true/false가 필요없어짐. 밑에 if조건문 true판단이 필요없어짐.
//    boolean needToPlus = exp.contains("+");   //contains 많이 쓸것 같으니 꼭 기억
//    boolean needToMinus = exp.contains("-");

   // String[] bits = null;
    String[] bits = exp.split(" \\+ ");   //항상 쪼개기전에 띄어쓰기공간 똑같이 해줄것.
//    if (needToPlus) {
//      bits = exp.split(" \\+ ");    //메타문자  역슬래쉬 필요
//    } else if (needToMinus) {
//      bits = exp.split(" - ");      // -는 역슬래쉬 필요없음.
//    }
    int[] num = null;
    int sum = 0;
    for(int i = 0; i<bits.length; i++){
      sum +=  Integer.parseInt(bits[i]);
    }
//    int a = Integer.parseInt(bits[0]);
//    int b = Integer.parseInt(bits[1]);
//    int c = 0;
//
//    if (bits.length > 2){
//      c = Integer.parseInt(bits[2]);
//    }

    return sum;

//   if(needToPlus){
//      return a + b + c;
//    }
//    else if (needToMinus) {
//      return a - b - c;
//    }

    //throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }
}