package com.ll;

public class Calc {
  public static int run(String exp) {
   
    //좌우 공백제거
   exp = exp.trim();
    
    // '()'괄호를 제거하기위한 메소드
    exp = stringOuterBracket(exp);
    
    //강사님이 알려준 숫자 그대로 출력 -> 연산기호 없을시
    //괄호제거 밑인이유 (100) 이렇게 올경우 대비
//    if(!exp.contains(" ")){
//      return Integer.parseInt(exp);
//    }
    //이렇게 해도됨, 괄호안에 아무것도 없을떄.
    if(!exp.contains(" ")) return Integer.parseInt(exp);

    boolean needToMultiply = exp.contains(" * ");
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");
    boolean needToCompound = needToMultiply && needToPlus;
    boolean needToSplit = exp.contains("(") || exp.contains(")");
    //boolean needToMinus = !needToMultiply;

    //(20 + 20) + 20 일떄 괄호가 아닌 뒤의 +20부터 쪼갤려고 만듬.
    //공백 포함 총 14글자임.
    //(20 + 20) 와 20 으로 나눌려고함
    //charAt은 0부터 8인덱스까지 for문을 돌면서 어디서 자를지 인덱스구한다.
    if(needToSplit){
      int bracketCount = 0;
      int splitPointIndex = -1;   //인덱스 시작은 0부터이니 -1로 초기화해줌.

      //bracketCount는 ()가 열고닫고를 찾아서 짝을 찾는중.
      for(int i = 0; i<exp.length(); i++){
        if(exp.charAt(i)=='('){
          bracketCount++;
        }
        else if(exp.charAt(i)==')') {
          bracketCount--;
        }
        if(bracketCount==0) {
          splitPointIndex = i; //i는 8이된다.
          break;
        }
      }
      // i는 8이였으니 8까지하면 닫는괄호가 빠지니 +1해줘서 닫는괄호까지 포함.
      String firstExp = exp.substring(0, splitPointIndex+1);
      //하나만 있으면 시작점만 알려줌
      //+3은 (20+20)+20 뒤의 +20부분임.
      String secondExp = exp.substring(splitPointIndex+3);

      return Calc.run(firstExp) + Calc.run(secondExp);
    }



    //재귀함수
    else if(needToCompound){
      String[] bits = exp.split(" \\+ ");
      return Integer.parseInt(bits[0]) + run(bits[1]);    // todo(개선)
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

    else if (needToMultiply) {
      String[] bits = exp.split(" \\* ");

      int rs = 1;

      for (int i = 0; i < bits.length; i++) {
          rs *= Integer.parseInt(bits[i]);
      }
      return rs;
    }
    
    //그냥 숫자 입력시 그숫자 바로출력 내가한것
    else if(!needToCompound){
      return Integer.parseInt(exp);
    }
   throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");

  }
  
  //위에서 선언한 메소드를 함수로 만들어서 괄호제거후 반환
  private static String stringOuterBracket(String exp) {

    //근데 (20 + 20) + 20 처럼 괄호로 시작했으나 괄호로 끝나지 않을때 문제가 생김.

    //다른방법으로도 가능, 카운트 추가
    int outerBracketCount = 0;

    while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
      outerBracketCount++;
    }
    if (outerBracketCount == 0) {
      return exp;
    }
    //소괄호가 여러개일때 반복문
//    while(exp.charAt(0) == '(' && exp.charAt(exp.length()-1)==')'){
//      exp = exp.substring(1, exp.length()-1);
//    }
//    초기 소괄호 1개일때
//    if(exp.charAt(0) == '(' && exp.charAt(exp.length()-1)==')'){
//      exp = exp.substring(1, exp.length()-1);
//    }
    //return exp;
    return exp.substring(outerBracketCount, exp.length() - outerBracketCount);
  }
}