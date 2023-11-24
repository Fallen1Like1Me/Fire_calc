package com.example.demo20;

import java.util.Scanner;

public class FireCalculator {
    private static final double BASE_EXPENSES = 150000;
    private static final double FIRE_CAPITAL = 45000000;

    private static final double[] MOEX_RATE = Constants.MOEX_RATE;
    private static final double[] INFLATION_RATE = Constants.INFLATION_RATE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите год начала жизни на проценты (от 2002 до 2021):");
        int startYear = scanner.nextInt();

        try {
            double maxWithdrawalPercent = calculateMaxWithdrawalPercent(startYear);
            System.out.println("Максимальный процент изъятия: " + maxWithdrawalPercent);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static double calculateMaxWithdrawalPercent(int startYear) {
        if (startYear < 2002 || startYear > 2021) {
            throw new IllegalArgumentException("Год должен быть в диапазоне от 2002 до 2021");
        }

        double capital = FIRE_CAPITAL;
        int index = startYear - 2002;

        for (int i = startYear; i <= 2021; i++) {
            double expenses = BASE_EXPENSES * 12;
            double inflation = INFLATION_RATE[index] / 100;
            expenses *= (1 + inflation);

            double growthRate = MOEX_RATE[index] / MOEX_RATE[index - 1] - 1;

            capital *= (1 + growthRate);

            double annualWithdrawal = capital * (expenses / capital);
            capital -= annualWithdrawal;

            index++;
        }

        double maxWithdrawalPercent = (BASE_EXPENSES * 12) / capital * 100;

        return Math.floor(maxWithdrawalPercent * 2) / 2;

    }}