package com.ll;

public class Calc {
  public static int run(String exp) {

    boolean needToPlus = exp.contains("+");
    boolean needToMinus = exp.contains("-");

    String[] bits = null;

    if (needToPlus) {
      bits = exp.split(" \\+ ");    //메타문자  역슬래쉬 필요
    } else if (needToMinus) {
      bits = exp.split(" - ");      // -는 역슬래쉬 필요없음.
    }

    int a = Integer.parseInt(bits[0]);
    int b = Integer.parseInt(bits[1]);

    if (needToPlus) {
      return a + b;
    } else if (needToMinus) {
      return a - b;
    }

    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }
}