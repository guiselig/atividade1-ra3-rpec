import java.util.Random;

public class TabelaHashEncadeamento {

    class No {
        int dado;
        No proximo;

        No(int elemento) {
            this.dado = elemento;
            this.proximo = null;
        }
    }

    int tamanho = 100;
    No[] tabela = new No[tamanho];
    int[] ultimos = new int[3];

    public int funcaoHash(int chave) {
        return chave % tamanho;
    }

    public void insereElemento(int elemento) {
        int posicao = funcaoHash(elemento);
        if (tabela[posicao] == null) {
            tabela[posicao] = new No(elemento);
        } else {
            insereOrdenado(tabela[posicao], elemento);
            ultimos[0] = ultimos[1];
            ultimos[1] = ultimos[2];
            ultimos[2] = elemento;
        }
    }

    public void insereOrdenado(No cabeca, int elemento) {
        No novo = new No(elemento);
        if (cabeca.proximo == null) {
            cabeca.proximo = novo;
        } else if (elemento < cabeca.proximo.dado) {
            novo.proximo = cabeca.proximo;
            cabeca.proximo = novo;
        } else {
            No ultimo = cabeca.proximo;
            while (ultimo.proximo != null) {
                ultimo = ultimo.proximo;
            }
            if (elemento >= ultimo.dado) {
                ultimo.proximo = novo;
            } else {
                No atual = cabeca.proximo;
                while (atual.proximo.dado <= elemento) {
                    atual = atual.proximo;
                }
                novo.proximo = atual.proximo;
                atual.proximo = novo;
            }
        }
    }

    public int contaElementos(int posicao) {
        int quantidade = 0;
        No atual = tabela[posicao];
        while (atual != null) {
            quantidade++;
            atual = atual.proximo;
        }
        return quantidade;
    }

    public void exibeLista(int posicao) {
        System.out.print("Lista: ");
        No atual = tabela[posicao];
        while (atual != null) {
            System.out.print(atual.dado + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }

    public void exibeMaiorLista() {
        int maiorQuantidade = contaElementos(0);
        for (int i = 1; i < tamanho; i++) {
            int quantidade = contaElementos(i);
            if (quantidade > maiorQuantidade) {
                maiorQuantidade = quantidade;
            }
        }
        for (int i = 0; i < tamanho; i++) {
            if (contaElementos(i) == maiorQuantidade) {
                System.out.println("Maior lista encadeada: posicao " + i + " com " + maiorQuantidade + " elementos.");
                exibeLista(i);
            }
        }
    }

    public int contaListasEncadeadas() {
        int listas = 0;
        for (int i = 0; i < tamanho; i++) {
            if (tabela[i] != null && tabela[i].proximo != null) {
                listas++;
            }
        }
        return listas;
    }

    public int contaPosicoesVazias() {
        int vazias = 0;
        for (int i = 0; i < tamanho; i++) {
            if (tabela[i] == null) {
                vazias++;
            }
        }
        return vazias;
    }

    public void exibeUltimos() {
        System.out.print("Ultimos valores inseridos em listas encadeadas (do mais antigo ao mais recente): ");
        for (int i = 0; i < 3; i++) {
            System.out.print(ultimos[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TabelaHashEncadeamento hash = new TabelaHashEncadeamento();
        Random random = new Random(1090);
        for (int i = 0; i < 1000; i++) {
            hash.insereElemento(random.nextInt(100001));
        }
        hash.exibeMaiorLista();
        System.out.println("Listas encadeadas geradas: " + hash.contaListasEncadeadas() + ".");
        System.out.println("Posicoes vazias: " + hash.contaPosicoesVazias() + ".");
        hash.exibeUltimos();
    }
}
