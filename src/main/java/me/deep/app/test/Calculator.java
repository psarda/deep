package me.deep.app.test;

import java.applet.Applet;

import javax.swing.JOptionPane;

public class Calculator extends Applet {

  public static void main(String[] args) {
    openProgram();
  }

  private static void openProgram() {
    String userInput = JOptionPane.showInputDialog(
            " enter 'add' for addition \n enter 'sub' for subtraction \n enter 'mul' for multiplication \n enter 'div' for division \n enter 'sq' for finding the square root \n enter 'square' for the finding the the square \n to find the average enter 'avg");
    String userInputNoCaps = userInput.toLowerCase();
    if (userInputNoCaps.equals("add")) {
      addition();
    } else if (userInputNoCaps.equals("sub")) {
      subtarction();
    } else if (userInputNoCaps.equals("mul")) {
      multiplication();
    } else if (userInputNoCaps.equals("div")) {
      division();
    } else if (userInputNoCaps.equals("sq")) {
      squareRoot();
    } else if (userInputNoCaps.equals("square")) {
      square();
    } else if (userInputNoCaps.equals("avg")) {
      average();
    } else {

      openProgram();
    }
  }

  private static void addition() {
    String userInput = JOptionPane.showInputDialog(" enter the number of numbers ");
    Integer userInputInt = Integer.parseInt(userInput);
    Integer output = userInputInt;
    Integer count = 1;
    Double answer = 0.00;
    while (userInputInt > 0) {
      String userInput1 = JOptionPane.showInputDialog(" enter the " + count + "out of " + output + " number/s");
      Double newNumber = Double.parseDouble(userInput1);
      answer = answer + newNumber;
      count++;
      userInputInt--;
    }
    JOptionPane.showMessageDialog(null, "the sum is " + answer);
    askAgain();
  }

  private static void subtarction() {
    String userInput = JOptionPane.showInputDialog(" enter the  first number ");
    Double newNumber = Double.parseDouble(userInput);
    String userInput1 = JOptionPane.showInputDialog(" enter the  second number ");
    Double nextNumber = Double.parseDouble(userInput1);
    Double answer = newNumber - nextNumber;
    JOptionPane.showMessageDialog(null, "the diffrence is " + answer);
    askAgain();
  }

  private static void multiplication() {
    String userInput = JOptionPane.showInputDialog(" enter the number of numbers ");
    Integer userInputInt = Integer.parseInt(userInput);
    Integer output = userInputInt;
    Integer count = 1;
    Double answer = 1.00;
    while (userInputInt > 0) {
      String userInput1 = JOptionPane.showInputDialog(" enter the " + count + "out of " + output + " number/s");
      Double newNumber = Double.parseDouble(userInput1);
      answer = answer * newNumber;
      count++;
      userInputInt--;
    }
    JOptionPane.showMessageDialog(null, "the product is " + answer);
    askAgain();
  }

  private static void division() {
    String userInput = JOptionPane.showInputDialog(" enter the  first number ");
    Double newNumber = Double.parseDouble(userInput);
    String userInput1 = JOptionPane.showInputDialog(" enter the  second number ");
    Double nextNumber = Double.parseDouble(userInput1);
    Double answer = newNumber / nextNumber;
    Double answer2 = newNumber % nextNumber;
    JOptionPane.showMessageDialog(null, "the quitient is " + answer + "the remainder is " + answer2);
    askAgain();
  }

  private static void askAgain() {
    String userInput = JOptionPane.showInputDialog(" enter 1 to do another calculation else enter 0");
    Integer userInputInt = Integer.parseInt(userInput);
    if (userInputInt == 1) {
      openProgram();
    } else if (userInputInt == 0) {

    } else {
      askAgain();
    }

  }

  private static void squareRoot() {
    String userInput = JOptionPane.showInputDialog(" enter the number");
    Double userInputDouble = Double.parseDouble(userInput);
    double answer = Math.sqrt(userInputDouble);
    JOptionPane.showMessageDialog(null, "the square root is " + answer);
  }

  private static void square() {
    String userInput = JOptionPane.showInputDialog(" enter the number");
    Double userInputDouble = Double.parseDouble(userInput);
    double answer = userInputDouble * userInputDouble;
    JOptionPane.showMessageDialog(null, "the square is " + answer);
  }

  private static void average() {
    String userInput = JOptionPane.showInputDialog(" enter the number of numbers ");
    Integer userInputInt = Integer.parseInt(userInput);
    Integer output = userInputInt;
    Integer count = 1;
    Double sum = 0.00;

    Integer noOfNumbers = userInputInt;

    while (userInputInt > 0) {
      String userInput1 = JOptionPane.showInputDialog(" enter the " + count + "out of " + output + " number/s");
      Double newNumber = Double.parseDouble(userInput1);
      sum = sum + newNumber;
      count++;
      userInputInt--;
    }
    Double answer = sum / noOfNumbers;
    JOptionPane.showMessageDialog(null, "the average is " + answer);
    askAgain();
  }
}