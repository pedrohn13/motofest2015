package br.edu.ufcg.embedded.motofest.utils;


public class ItemMenu {

    private final String nome;
    private final int image;

    public ItemMenu(String mNome, int mImage) {
        this.nome = mNome;
        this.image = mImage;
    }

    public String getNome() {
        return nome;
    }

    public int getImage() {
        return image;
    }
}
