package com.ll;

public class Calc {
  public static int run(String exp) {

    boolean needToPlus = exp.contains("+");   //contains 많이 쓸것 같으니 꼭 기억
    boolean needToMinus = exp.contains("-");

    String[] bits = null;

    if (needToPlus) {
      bits = exp.split(" \\+ ");    //메타문자  역슬래쉬 필요
    } else if (needToMinus) {
      bits = exp.split(" - ");      // -는 역슬래쉬 필요없음.
    }

    int a = Integer.parseInt(bits[0]);
    int b = Integer.parseInt(bits[1]);
    int c = 0;

    if (bits.length > 2){
      c = Integer.parseInt(bits[2]);
    }

    if (needToPlus) {
      return a + b + c;
    } else if (needToMinus) {
      return a - b - c;
    }

    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }
}