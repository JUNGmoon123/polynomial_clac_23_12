package com.ll;

public class Calc {
  public static int run(String exp) {

    //-일때는 +/*둘다 false가 떠서 조건문 모두 해당하지않아서임.
    // 이제 이 고장난 기능을 고쳐야함.
    //전과 다른점은 ""안의 부호양옆에 공백을 추가해줬음
    // -5를 false로 보고 - 5 일때는 true로 봐서 서로 다른부분임.
    //만약 붙여쓸경우 ||부분에서 True가 나온다.
 //   boolean needToMultiply = exp.contains("*");
    boolean needToMultiply = exp.contains(" * ");
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");
    boolean needToCompound = needToMultiply && needToPlus;
    //boolean needToMinus = !needToMultiply;

    //재귀함수
    if(needToCompound){
      String[] bits = exp.split(" \\+ ");
      return Integer.parseInt(bits[0]) + run(bits[1]);
    }
    if (needToPlus) {
      exp = exp.replaceAll("\\- ", "\\+ \\-");

      String[] bits = exp.split(" \\+ ");

      int sum = 0;

      for (int i = 0; i < bits.length; i++) {
        sum += Integer.parseInt(bits[i]);
      }

      return sum;
    }

//    else if(needToMinus){
//      exp = exp.replaceAll("\\- ", "\\+ \\-");
//
//      String[] bits = exp.split(" \\+ ");
//      int sum = 0;
//
//      for (int i = 0; i < bits.length; i++) {
//        sum += Integer.parseInt(bits[i]);
//      }
//
//      return sum;
//    }

    else if (needToMultiply) {
      String[] bits = exp.split(" \\* ");

      int rs = 1;

      for (int i = 0; i < bits.length; i++) {
          rs *= Integer.parseInt(bits[i]);
      }
      return rs;
    }
   throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");

  }
}