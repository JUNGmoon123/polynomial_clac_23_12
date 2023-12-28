package com.ll;

public class Calc {

  public static boolean recursionDebug = true; // 내가 디버그 모드를 켜겠다 할때는 true로 변경

  public static int runCallCount = 0;

  public static int run(String exp) {   //매개변수 exp에 문자열을 받는다.
    runCallCount++;
    //좌우 문자열양끝 공백제거
    exp = exp.trim();

    // '()'괄호를 제거하기위한 메소드
    //괄호제거 이유 (100) 이렇게 올경우 대비
    //밑에 needToSplit와 차이점은 계산식이 소괄호만 있을때와 아닐때이다.
    // (10 + 20) ==30 ,  (10 + 20) + 30 == 60
    exp = stringOuterBracket(exp);


    //맨앞에 부호 '-'가 붙어서 시작하는 식일떄 실행되는 메소드.
    //음수 괄호 패턴 -( 이면, 기존에 갖고있던 해석 패턴과는 맞이 않으니 패턴을 변경.
    if (isNegativeCaseBracket(exp)) {
      exp = exp.substring(1) + " * -1";
    }


    if (recursionDebug) {
      System.out.printf("exp(%d) : %s\n", runCallCount, exp);
    }


    if (!exp.contains(" ")) {
      return Integer.parseInt(exp);
    }

    //boolean타입으로 true/false 를 판단, 조건문을 실행한다.
    boolean needToMultiply = exp.contains(" * ");   //exp문자열에 공백과 *문자를 포함하면 true를 Multiply에 전달한다.
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");  //exp문자열에 공백을 포함한 +문자와 -문자를 OR로 true를 판단하고 Plus에 전달한다.
    boolean needToCompound = needToMultiply && needToPlus;    //Multilply와 Plus 가 AND논리연산자로 서로 true라면 Compound로 true를 전달한다.
    boolean needToSplit = exp.contains("(") || exp.contains(")");   //exp문자열에 소괄호'()'가 포함되면 true를 Split로 전달한다.
    //boolean needToMinus = !needToMultiply;

    //(20 + 20) + 20 일떄 괄호가 아닌 뒤의 +20부터 쪼갤려고 만듬.
    //공백 포함 총 14글자임.
    //(20 + 20) 와 20 으로 나눌려고함
    //charAt은 0부터 8인덱스까지 for문을 돌면서 어디서 자를지 인덱스구한다.
    if (needToSplit) {      //true면 조건물 실행.
      int splitPointIndex = findSplitPointIndex(exp);   //exp문자열을 findSplitPointIndex로 전달한다.
      //인덱스로 받은 숫자가 -1일시 소괄호가 없다의미
      //인덱스로 받은 숫자가 0이상일때 소괄호가 존재하고 ()이후 이전 으로 나눈다.
      // ()소괄호를 판단하지 못함--> 문제발생
//      for(int i = 0; i<exp.length(); i++) {
//        if(exp.charAt(i)=='('){
//          bracketCount++;
//        }
//        else if(exp.charAt(i)==')') {
//          bracketCount--;
//        }
//        if(bracketCount==0) {
//          splitPointIndex = i;
//          break;
//        }
//      }

      String firstExp = exp.substring(0, splitPointIndex);    // 문자열의 인덱스 0부터 +/*전까지 문자열을 자른다.
      String secondExp = exp.substring(splitPointIndex + 1);  //문자열의 +/*후부터 끝까지 자른다.

      // splitPointIndex 이 가리키고 있는건 연산자가 된다.
      char operator = exp.charAt(splitPointIndex);      //전달받은 인덱스의 번호로 */+를 추출하여 op변수에 저장한다.
      //exp는 문자열이니까 String으로 다 엮어버린다.
      //위의 operator이용
      exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);  //재귀함수를 이용 run()에 자른 문자열을 넣어서 결과값을 exp에 저장한다.
      //밑에 Plus/Multiply/Compound 에서 리턴된 값들이 전달된다.

      return Calc.run(exp);   //값을 CalcTest로 전달 Calc함수를 종료한다.
    }

    //재귀함수
    else if (needToCompound) {  // Multilply와 Plus 가 ture시 실행
      String[] bits = exp.split(" \\+ ");   //String배열 bits에 공백포함 '+' 연산자를 기준으로 exp를 나누고 저장

      return Integer.parseInt(bits[0]) + run(bits[1]);    // todo(개선)
      //bits[0]은 문자를 int형 타입으로 형 변환
      //bits[1]은 재귀함수, 함수를 인덱스1번에 들어있는 문자로 다시실행.
    }
    if (needToPlus) {
      exp = exp.replaceAll("\\- ", "\\+ \\-");  //exp문자열 안에 -문자가 있으면 -> + - 로 대체하여 exp에 다시저장.

      String[] bits = exp.split(" \\+ ");//String배열 bits에 공백포함 '+' 문자를 기준으로 exp를 나누고 저장

      int sum = 0;

      for (int i = 0; i < bits.length; i++) {
        sum += Integer.parseInt(bits[i]);   // bits의 배열 길이만큼 반복하면서 bits를 int형으로 형변화하여 sum에 누적시킨다.
      }

      return sum;
    } else if (needToMultiply) {
      String[] bits = exp.split(" \\* ");//String배열 bits에 공백포함 '*' 문자를 기준으로 exp를 나누고 저장

      int rs = 1; //곱하기를 하기위에 1로 설정.

      for (int i = 0; i < bits.length; i++) {
        rs *= Integer.parseInt(bits[i]);  //bits배열의 길이만큼 반복하면서 int형으로 형변환 시켜 rs에 누적시킨다.
      }
      return rs;
    }

//    //그냥 숫자 입력시 그숫자 바로출력 내가한것
//    else if(!needToCompound){
//      return Integer.parseInt(exp);
//    }
    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");

  }

  //-로 시작하는지 알아보기위한 메소드
  private static boolean isNegativeCaseBracket(String exp) {
    //바로앞이 -가 -( 처럼 붙어있거나 - 10 처럼 띄우고있거나 10 - (10 처럼의 경우의 수 대비.
    if (exp.startsWith("-(") == false) {
      return false;
    }
    int bracketCount = 0;   //소괄호의 열고 닫음을 판단하기 위한 Count변수

    for (int i = 0; i < exp.length(); i++) {  //i가 exp문자열의 길이만큼 반복한다.
      char c = exp.charAt(i);   //charAt함수를 이용해서 exp문자열을 for문이 반복하는동안 한문자한문자씩 c변수로 넘겨준다.

      if (c == '(') {  //charAt로 넘겨받은 데이터에 '(' 문자가 있는지 판단한다.
        bracketCount++; //위 조건문이 true면 소괄호 ( 열기가 있다고 판단 count+1 해준다.
      } else if (c == ')') {  //charAt로 넘겨받은 데이터에 ')' 문자가 있는지 판단한다.
        bracketCount--; //위 조건문이 true면 소괄호 ) 닫기가 있다고 판단 count-1 해준다.
      }
      //위에서 -가 왔을시 바로 이 조건문으로 간다. 위의 조건문은 다 false임
      if (bracketCount == 0) {    //count가 0이면 true, 추가로 Count가 0이면 소괄호가 열고닫고를 했다의미 -> (10+20) 이후를 뜻한다.
        if (exp.length() - 1 == i) return true;
      }


    }
    return false;
  }

  private static int findSplitPointIndexBy(String exp, char findChar) { // 문자열과 문자+를 전달받음.
    int bracketCount = 0;   //소괄호의 열고 닫음을 판단하기 위한 Count변수

    for (int i = 0; i < exp.length(); i++) {  //i가 exp문자열의 길이만큼 반복한다.
      char c = exp.charAt(i);   //charAt함수를 이용해서 exp문자열을 for문이 반복하는동안 한문자한문자씩 c변수로 넘겨준다.

      if (c == '(') {  //charAt로 넘겨받은 데이터에 '(' 문자가 있는지 판단한다.
        bracketCount++; //위 조건문이 true면 소괄호 ( 열기가 있다고 판단 count+1 해준다.
      } else if (c == ')') {  //charAt로 넘겨받은 데이터에 ')' 문자가 있는지 판단한다.
        bracketCount--; //위 조건문이 true면 소괄호 ) 닫기가 있다고 판단 count-1 해준다.
      } else if (c == findChar) {   //매개변수로 넘겨받은 +문자가 c문자에 똑같이 있을시 true
        if (bracketCount == 0) {    //count가 0이면 true, 추가로 Count가 0이면 소괄호가 열고닫고를 했다의미 -> (10+20) 이후를 뜻한다.
          return i;     //  exp의 문자열의 인덱스번호를 i로 넘겨준다.
        }

      }
    }
    return -1;    //()소괄호 없을시 -1 를 반환한다. 위에서 문자열 인덱스가 0부터 시작하니 -1을 보내줘서 초기화한다.
  }

  private static int findSplitPointIndex(String exp) {   //매개변수로 전달된 데이터 받는다.
    int index = findSplitPointIndexBy(exp, '+');  //findSplitPointIndexB로 exp에 저장된 문자열과 문자+를 전달한다.
    // i변수에 숫자가 있다면 같은 문자인 +가 있다고 판단된다.

    if (index >= 0) return index;     // index가 0보다 클시 index번호를 반환

    return findSplitPointIndexBy(exp, '*'); // index가 -1일때 +문자가 없다고 판단 *문자가 있는지 다시 findSplitPointIndexBy함수를 호출하여 판단한다.
  }

  //위에서 선언한 메소드를 함수로 만들어서 괄호제거후 반환
  // 계산식에 소괄호만 있을경우 실행되는 메소드
  private static String stringOuterBracket(String exp) {

    //근데 (20 + 20) + 20 처럼 괄호로 시작했으나 괄호로 끝나지 않을때 문제가 생김.

    //다른방법으로도 가능, 카운트 추가
    int outerBracketCount = 0;
    //0인 이유는 0번 인덱스부터 (이고 exp문자열의 길이끝의 -1이 )를 만나게 된다.
    while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
      outerBracketCount++;
    }

    if (outerBracketCount == 0) {   //소괄호 없을시 exp문자열 리턴
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
    return exp.substring(outerBracketCount, exp.length() - outerBracketCount);  //소괄호 존재시 ()를 제외한 안쪽부분을 전달한다.
  }
}