package br.com.msm.librarydatabaseunidadepmam.util;

import android.text.TextUtils;
import android.util.Patterns;


public class validacoes {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {

        if ((cpf == null) || (cpf.length() != 11)) return false;
        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) return false;
        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (!TextUtils.isEmpty(target)) {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        } else {
            return false;
        }
    }

    public final static boolean isValidPhone(CharSequence target) {


        if (!TextUtils.isEmpty(target)) {
            return Patterns.PHONE.matcher(target).matches();

        } else {
            return true;
        }


    }

    public final static boolean isValidCep(CharSequence target) {

        if (!TextUtils.isEmpty(target)) {
            return target.length() == 8;

        } else {
            return true;
        }

    }

    public final static boolean isValidaPassword(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() >= 6  && target.length() <= 50;
    }
    public final static boolean isValidaName(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() >= 2 && target.length() <= 100;
    }

    public final static boolean isValidacodigo(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() == 36;
    }

    public final static boolean isValidaNameRoom(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() >= 2 && target.length() <= 100;
    }

    public final static boolean isValor_off(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() > 0 && target.length() <= 4;
    }

    public final static boolean isValor_on(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() == 1;
    }



    public final static boolean isValidaEntrada(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() >= 4 && target.length() <= 5;
    }

    public final static boolean isValidaFuncoa(CharSequence target) {
        if (!TextUtils.isEmpty(target)) {
            return target.length() > 3;
        } else {
            return true;
        }
    }


    public static String DiaDaSemana(int i, int tipo) {
        String diasem[] = {"Domingo", "Segunda-feira", "Terça-feira",
                "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"};
        if (tipo == 0)
            return (diasem[i - 1]); // extenso
// o método "substring" retorna os 3 primeiros caracteres de "diasem[i]"
        else return (diasem[i - 1].substring(0, 3));
    }


}