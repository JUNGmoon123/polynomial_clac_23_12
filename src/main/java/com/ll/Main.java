package com.ll;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    System.out.println("50 - 30 ".replaceAll("\\- ", "\\+ \\-"));
    int sum = 0;
    String sc = "50 - 30";
    sc= sc.replaceAll("\\- ", "\\+ \\-");
    String[] num = sc.split(" \\+ ");
    for(int i = 0; i<num.length; i++) {
      System.out.println(num[i]);
      sum += Integer.parseInt(num[i]);
    }
    System.out.println(sum);
  }
}