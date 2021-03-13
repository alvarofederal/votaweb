package br.com.votacao.votaweb.utils;

public class GeradorSequenciais {
    private static int ID = 0;
    public static int getProximoSequencial() {
        return ID++;
    }
}
