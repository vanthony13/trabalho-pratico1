package po.leit;

import po.leit.ui.Le;
import po.leit.ui.MyCommand;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TP1 {

    private static MyCommand interC;
    static final int MAX_ALUNOS = 35;
    private static int alunosLidos=0;
    private static int notaMax = 0;
    private static int notaMin = 0;
    private static int notaAvg = 0;
    private static int n=0;

    private static String[] nomeAlunos = new String[MAX_ALUNOS];
    private static int[] notasAlunos = new int[MAX_ALUNOS];

    public static void main(String[] args) {
        boolean querSair=false;

        interC=new MyCommand();

        do {
            interC.limparEcra();
            interC.showPrompt();
            String[] cmdEscrito = interC.lerComando();
            ArrayList<String> cmd = interC.validarComando(cmdEscrito);

            if (cmd == null) {
                interC.showMsg("Comando inválido. Digite help para ajuda");

            } else {
                if  ( cmd.get(0).equalsIgnoreCase("carregar") ) {
                    alunosLidos = loadData(nomeAlunos, "turmaLeit.txt");
                    int notA = loadData(notasAlunos);
                    if ( alunosLidos != notA ) {
                        System.out.println("alunos = " + alunosLidos);
                        System.out.println("notaA = " + notA);
                        interC.showMsg("Erro carregando dados");
                    }
                        
                    else

                        interC.showMsg("Dados carregados OK!");
                } else if (cmd.get(0).equalsIgnoreCase("listar") ) {
                    mostrarAlunos();

                } else if (cmd.get(0).equalsIgnoreCase("paginar") ) {
                    String input = JOptionPane.showInputDialog("Nũmeros estudantes por pãgina :");
                    //Scanner sc=new Scanner(System.in);
                    int numeroU = Integer.parseInt(input);
                   // int numeroU = sc.nextInt();
                    mostrarAlunos(numeroU);

                } else if (cmd.get(0).equalsIgnoreCase("mostrarp") ) {
                    mostrarPauta();

                } else if (cmd.get(0).equalsIgnoreCase("mostrarr") ) {
                    mostraResumo();

                } else if (cmd.get(0).equalsIgnoreCase("top") ) {
                    mostrarTop();

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnome") ) {
                    String nomePesq = JOptionPane.showInputDialog("O que procura  :");
                    pesquisar(nomePesq);

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnota") ) {
                    String vaPesq = JOptionPane.showInputDialog("O que procura  :");
                    int notaPesq = Integer.parseInt(vaPesq);
                    pesquisar(notaPesq);
                } else if (cmd.get(0).equalsIgnoreCase("help") ) {
                    interC.showHelp();

                } else if (cmd.get(0).equalsIgnoreCase("terminar") ) {
                    querSair = true;
                }
            }

        } while (!querSair);

    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array e um ficheiro
     * Lẽ cada linha do ficheiro e guarda no array. Retorna o número
     * de linhas que forma lidas do ficheiro.
     * @param lAlunos
     * @param nomeFicheiro
     * @return quantos nomes foram lidos do ficheiro -1 se não possível ler ficheiro
     */
    public static int loadData(String[] lAlunos, String nomeFicheiro) {
        Scanner in = null;
        File inputFile = new File(nomeFicheiro);
        //PrintWriter out = new PrintWriter(outputFileName);
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (in.hasNextLine()) {
            String nomeAl = in.nextLine();
            if ( (nomeAl != null) && !(nomeAl.isBlank()) && !(nomeAl.isEmpty() ) ) {
                lAlunos[i] = nomeAl;
                i++;
            }

        }
        in.close();
        return i;
    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array de inteiros e vai gerar aleatoriamente valores inteiros entre
     * 0 e 20 que representam a nota de cada aluno.
     * @param lNotas
     * @return how much name was read from the files -1 if was not able to read the file
     */
    public static int loadData(int[] lNotas) {
        Random rand = new Random();
        int cont = 0;
        for (cont=0; cont < alunosLidos; cont++) {
            int randomNum = rand.nextInt(20) + 1;
            notasAlunos[cont] = randomNum;
        }
        return cont;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados"
     * @param
     * @return
     */
    public static void mostrarAlunos() {
       for (int i=0; i<nomeAlunos.length-4; i++){

            System.out.println(nomeAlunos[i]);
        }


        interC.showMsg(" ...");
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados".
     * Neste método os dados não são mostrados todos de uma só vez. Devem ser apresentados até encher a tela.
     * Vamos supor que 10 nomes enchem a tela. Então deverá ser apresentados de 10 em 10. Esse número
     * que indica quantos nomes enchem a tela é um parâmetro do método.
     * @param tela é um inteiro que indica quantos alunos são mostrados.
     */
    public static void mostrarAlunos(int tela) {

        int tamanho = nomeAlunos.length;

        for( int i=0; i<tamanho; i++){


            if(i % tela == 0){
                interC.showMsg("Enter para continuar...");


            }

            System.out.println(nomeAlunos[i]);
        }

            interC.showMsg("...");
        }


    /**
     * Método a ser implementando no TP1.
     * O método deverá percorrer o array de notas, calcular o valor da média aritmética de notas, a nota máximo e
     * a nota mínima.
     * Os valores calculados devem ser guaraddos na variáveis notaAVG (média),
     * notaMax (nota máxima) e notaMin(nota mínima)
     * Devem validar se o array de notas tem elementos. Se estiver vazio devem somente apresentar
     * a mensagem "Não há dados"
     */
    private static void calcularMaxMinAvg() {
        int posicao = 0, soma=0 ,i ;


        for( i = 0 ; i <notasAlunos.length-4 ; i++){


            soma+=notasAlunos[i];
            notaAvg=soma/notasAlunos.length;
        }
            System.out.println("A nota media = "+ notaAvg);

        notaMin=notasAlunos[0];
        for( i = 0 ; i <notasAlunos.length-4 ; i++){
            if(notasAlunos[i] < notaMin){
                notaMin = notasAlunos[i];
                posicao = i;
            }}

        System.out.println("A nota minima = "+ notaMin);


        for( i = 0 ; i <notasAlunos.length ; i++){

            if(notasAlunos[i] >= notaMax){
                notaMax = notasAlunos[i];
                posicao = i;
            }}
            System.out.println("A nota maxima = "+ notaMax);




        interC.showMsg(" ...");

    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar um resumo da avaliação;
     * Nota máxima, Nota mínima, Nota média. Número de alunos com nota superior a média e número de alunos com
     * nota inferior a média.
     * a mensagem "Não há dados"
     */
    public static void mostraResumo() {
        int cont=0,soma=0,qntd=0, qntd2=0;


        for(int i = 0 ; i <notasAlunos.length ; i++){
            if(nomeAlunos[0]==null){
                interC.showMsg("Nao ha dados");
                break;}

            cont=cont+1;
        }
            System.out.println("Total = "+ cont);


        for( int i = 0 ; i <notasAlunos.length ; i++){
            soma+=notasAlunos[i];
            notaAvg=soma/notasAlunos.length;

            if(notasAlunos[i]!=notaAvg && notasAlunos[i]<notaAvg){
                 qntd=qntd+1;
            }else

            if(notasAlunos[i]!=notaAvg && notasAlunos[i]>notaAvg){
                qntd2=qntd2+1;
            }

        }
        System.out.println("O numero de notas menor que a media ="+ qntd);
        System.out.println("O numero de notas maior que a media ="+ qntd2);

            calcularMaxMinAvg();

        interC.showMsg("...");

    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar o nome dos três alunos que têm as melhores notas.
     */
    public static void mostrarTop() {

        int notaMax2=0, notaMax3=0 ,i , posicao=0;
        for(   i = 0 ; i <notasAlunos.length ; i++){

            if(notasAlunos[i] >= notaMax){
                posicao=i;
                notaMax = notasAlunos[i];

            }}
        System.out.println("A primeira melhor nota = " + notaMax + " e pertence a " + nomeAlunos[posicao]);


        for( i = 0 ; i <notasAlunos.length ; i++ ){

            if(notasAlunos[i]!=notaMax)
                if(notasAlunos[i] >= notaMax2){
                    posicao=i;
                    notaMax2 = notasAlunos[i];
                   //System.out.println(nomeAlunos[i]);

            }}

        System.out.println("A segunda melhor nota = " + notaMax2 + " e pertence a " + nomeAlunos[posicao]);

        for(  i = 0 ; i <notasAlunos.length ; i++ ){

            if(notasAlunos[i]!=notaMax && notasAlunos[i]!=notaMax2)

                if(notasAlunos[i] >= notaMax3){
                    posicao=i;
                    notaMax3 = notasAlunos[i];

                }}

        System.out.println("A terceira melhor nota = " + notaMax3 + "  pertence a " + nomeAlunos[posicao]);


        interC.showMsg("...");

    }
    /**
     * Método a ser implementando no TP1.
     * Apresentar a pauta com nomes dos alunos e á frente cada nome a respectiva nota obtida.
     */
    public static void mostrarPauta() {

        for (int i=0; i<nomeAlunos.length-4; i++){

            if(i % 10==0){
                interC.showMsg("Enter para continuar...");


            }

            System.out.print(nomeAlunos[i]+"...."); System.out.println(notasAlunos[i]);
        }
        interC.showMsg("...");

    }
    /**
     * Método a ser implementando no TP1
     * Apresentar para um aluno específico em que o nome é dado como parâmetro a nota de avaliação
     * @param nome é uma string contendo o nome do aluno que queremos apresentar a sua nota
     * @return
     */
    public static void mostrarDetalhesAluno(String nome) {
        interC.showMsg("A ser implementado ...");

    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(String nome) {
        for(String aluno:nomeAlunos){
            String student=aluno;
            if(aluno==null)
                continue;

            else{
                String apelido;
                String [] strSp=student.split(" ",2);
                String first=strSp[0];
                 apelido=strSp[1];

                if(nome.equalsIgnoreCase(first) || nome.equalsIgnoreCase(apelido)){
                    System.out.println(aluno);
                }

            }
        }
        interC.showMsg("...");

    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(int nota) {

        for (int i=0; i<notasAlunos.length; i++){
            if(notasAlunos[i]==nota){
                System.out.print(nomeAlunos[i]+"....");
                System.out.print(notasAlunos[i]);

            }
        }

        interC.showMsg("");

    }

    private String[] searchByName(String nome) {
        return null;
    }

}
