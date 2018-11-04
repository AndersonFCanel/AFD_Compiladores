package com.suam.AFD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class AFD {
	static HashMap<Integer, String> conjuntoDeEstadosMap = new HashMap<Integer, String>();
	static HashMap<Integer, String> conjuntoDeEstadosFinaisMap = new HashMap<Integer, String>();

	public static void main(String[] args) {
		String[] nulos = { "{", "}", "," };// identificando carateres de formatação do conjunto

		// ENTRADA DO CONJUNTO DE SIMBOLOS (ALFABETO)
		String alfabeto = "{a,b}";// Enrando com conjundo de caracteres(alfabeto)
		String conjuntodeSimbolos_AlfabetoImprime = alfabeto;

		for (String n : nulos) {
			alfabeto = alfabeto.replace(n, "");
		}

		List<Character> alf = new ArrayList<>(); // ***********
		Character[] conjuntodeSimbolos_Alfabeto = new Character[alfabeto.length()];
		int z = 0;
		for (char ch : alfabeto.toCharArray()) {
			alf.add(ch); // ***********
			conjuntodeSimbolos_Alfabeto[z] = ch;
			z++;
			System.out.println("Simbolo Alfabeto: " + ch);
		}

		// ENTRADA DO CONJUNTO DE ESTADOS
		String conjuntoDeEstadosTerminaisEnaoTerminais = "{A,B,C,D,E}";
		String conjuntoDeEstadosTerminaisEnaoTerminaisImprime = conjuntoDeEstadosTerminaisEnaoTerminais;

		// removendo carateres de formatação do conjunto
		for (String n : nulos) {
			conjuntoDeEstadosTerminaisEnaoTerminais = conjuntoDeEstadosTerminaisEnaoTerminais.replace(n, "");
		}

		int estados = conjuntoDeEstadosTerminaisEnaoTerminais.length();
		int[] conjuntoDeEstados = new int[estados];
		int a = 0;
		for (Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray()) {
			conjuntoDeEstadosMap.put(a, ch.toString());

			conjuntoDeEstados[a] = a;
			a++;
			System.out.println("Estado: " + ch);
		}

		// ENTRADA FUNCAO DE TRANSICAO

		String[] funcDelta = new String[conjuntodeSimbolos_Alfabeto.length
				* conjuntoDeEstadosTerminaisEnaoTerminais.length()];
		funcDelta[0] = "A1,a;B2";
		funcDelta[1] = "A1,b;C3";
		funcDelta[2] = "B2,a;B2";
		funcDelta[3] = "B2,b;D4";
		funcDelta[4] = "C3,a;B2";
		funcDelta[5] = "C3,b;C3";
		funcDelta[6] = "D4,a;B2";
		funcDelta[7] = "D4,b;E5";
		funcDelta[8] = "E5,a;B2";
		funcDelta[9] = "E5,a;C3";

		String[] estadoPartidaS = new String[funcDelta.length];
		String[] caracConsumidoS = new String[funcDelta.length];
		String[] estadoDestinoS = new String[funcDelta.length];

		int[] estadoPartida = new int[funcDelta.length];
		int[] estadoDestino = new int[funcDelta.length];
		char[] le = new char[funcDelta.length];

		// -->>

		for (int i = 0; i < funcDelta.length; i++) {
			if (funcDelta[i] == null) {
				break;
			}
			String[] p1 = funcDelta[i].split(";");
			String[] p2 = p1[0].split(",");
			estadoPartidaS[i] = p2[0];
			caracConsumidoS[i] = p2[1];
			estadoDestinoS[i] = p1[1];
		}

		int i = 0;
		int regra = 0;

		for (int p = 0; p < funcDelta.length; p++) {

			String aux = estadoPartidaS[i];
			estadoPartida[i] = Integer.parseInt(String.valueOf(aux.charAt(1)));

			aux = estadoDestinoS[i];
			estadoDestino[i] = Integer.parseInt(String.valueOf(aux.charAt(1)));

			aux = caracConsumidoS[i];
			le[i] = aux.charAt(0);
			i++;
		}

		// ----APOS O TRATAMENTO----------
		System.out.println("ESTADO PARTIDA: " + Arrays.toString(estadoPartida));
		System.out.println("CARACTER CONSUMIDO: " + Arrays.toString(le));
		System.out.println("ESTADO DESTINO: " + Arrays.toString(estadoDestino));

		// -------------------------------------------------
		// ENTRA COM ESTADO INICIAL

		String estadoIni = "{A}";
		int estadoi = 0;
		// removendo carateres de formatação do conjunto
		for (String n : nulos) {
			conjuntoDeEstadosTerminaisEnaoTerminais = conjuntoDeEstadosTerminaisEnaoTerminais.replace(n, "");
		}

		int u = 0;
		for (Character ch : alfabeto.toCharArray()) {
			if (ch.toString().equals(estadoIni)) {
				estadoi = u;
				System.out.println("Estado Inicial: " + conjuntoDeEstadosMap.get(ch));
			}
			u++;
		}

		// -------------------------------------------------
		// ENTRA COM ESTADOS FINAIS
		String conjuntoEstadosTerminais = "{E}";

		// removendo carateres de formatação do conjunto
		for (String n : nulos) {
			conjuntoEstadosTerminais = conjuntoEstadosTerminais.replace(n, "");
		}

		int[] estadosf = new int[conjuntoEstadosTerminais.length()];
		int b = 0;
		for (Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray()) {
			for (Character ch1 : conjuntoEstadosTerminais.toCharArray()) {
				if (conjuntoDeEstadosMap.get(b).equals(ch1.toString())) {
					conjuntoDeEstadosFinaisMap.put(b, ch1.toString());
					System.out.println("Estado Final: " + ch1);
				}
			}
			b++;
		}

		int tamanhoConjuntoEstadosTerminais = b;

		// -------------------------------------------------
		imprimirAutomato(conjuntodeSimbolos_AlfabetoImprime, conjuntoDeEstadosTerminaisEnaoTerminaisImprime,
				estadoPartida, estadoDestino, le, estadoIni, conjuntoEstadosTerminais);
		// -------------------------------------------------

		// ------------------INSERE E VERIFICA PALAVRA
		String palavraS;
		boolean flagPal;
		// PALAVRAS
		do {
			int teste = 0;
			
			palavraS = JOptionPane.showInputDialog(null,
					"Entre com a palavra a ser verificada: \nPara conferir suas entradas digite anteriores digite 'I'\nPara sair digite s");
			if (palavraS.equalsIgnoreCase("s")) {
				break;
			}
			if (palavraS.equalsIgnoreCase("I")) {
				imprimirAutomato(conjuntodeSimbolos_AlfabetoImprime, conjuntoDeEstadosTerminaisEnaoTerminaisImprime,
						estadoPartida, estadoDestino, le, estadoIni, conjuntoEstadosTerminais);
				palavraS = JOptionPane.showInputDialog(null,
						"Entre com a palavra a ser verificada: \nPara conferir suas entradas digite anteriores digite 'I'\nPara sair digite s");
				if (palavraS.equalsIgnoreCase("s")) {
					break;
				}
			}

			// -----VALIDAÃ‡ÃƒO DA PALAVRA
			flagPal = VerificaPalavra(palavraS, conjuntodeSimbolos_Alfabeto);
			System.out.println("PALAVRA: "+palavraS);
			System.out.println("ARRAY DE CHAR insere: " + Arrays.toString(conjuntodeSimbolos_Alfabeto));
			
			if (!flagPal) {
				System.out.println("INVALIDA PALAVRA");
			} else {

				char[] palavra = palavraS.toCharArray();
				int estadoa = estadoi;
				int aux = 0;

				for (int p = 0; p < palavra.length; p++) {
					int mudou = 0;
					int j = 0;
					System.out.println("conjuntodeSimbolos_Alfabeto: "+conjuntodeSimbolos_Alfabeto[p]);
					for (int k = 0; k < funcDelta.length; k++) {
						
						if ((palavra[p] == le[k]) && (estadoa == estadoPartida[k])) {
							aux = estadoDestino[k];
							mudou = 1;
							break;
						} else {
							mudou = 0;
						}

					}

					if (mudou == 1) {
						estadoa = aux;
					} else {
						break;
					}

					for (int k = 0; k < tamanhoConjuntoEstadosTerminais; k++) {
						if (estadoa == estadosf[k]) {
							teste = 1;
						} else {
							teste = 0;
						}
					}
				}
				if (teste == 1) { // && teste1 !=1){//: #ALTERADO if teste ==
									// 1){
					JOptionPane.showMessageDialog(null, "PALAVRA ACEITA PELO AUTOMATO\n\n");
					// break;
				} else {
					JOptionPane.showMessageDialog(null, "PALAVRA NÃƒO ACEITA PELO AUTOMATO\n\n");
					// break;
				}
			}
		} while (!palavraS.equalsIgnoreCase("s"));
		JOptionPane.showMessageDialog(null, "Voce finalizou a aplicaÃ§ao, obrigado!");
	}

	// ---------------METODOS-------------------------------------
	// ---- METODOS DE IMPRESSÃƒO DEINFORMAÃ‡OES -

	// IMPRIME ENTRADAS
	private static void imprimirAutomato(String alf, String est, int[] estadoPartida, int[] estadoDestino, char[] le,
			String estIn, String conjuntoEstadosFinais) {

		String[] estP = new String[estadoPartida.length];
		;
		int b = 0;
		for (int key : estadoPartida) {
			estP[b] = conjuntoDeEstadosMap.get(key - 1);
			b++;
		}

		String[] estD = new String[estadoDestino.length];
		;
		int c = 0;
		for (int key : estadoDestino) {
			estD[c] = conjuntoDeEstadosMap.get(key - 1);
			c++;
		}

		JOptionPane.showMessageDialog(null,
				"**************************************************\n" + "\tIMPRIMINDO ENTRADAS DO AUTOMATO\n"
						+ "\t\t\t ==>NOTAÇÃO UTILIZADA <== \n" + "\tO conjunto de simbolos - alfabeto: Σ \n"
						+ "\tO conjunto dos estados terminais e não terminais: Q = {S1, S2...}\n"
						+ "\tAs transicoes: (δ: Q × Σ → Q)\n" + "\tO  estado Inicial: q0\n"
						+ "\tO conjunto dos estados terminais: F\n" + "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n"
						+ "\n\t\t ==>DADOS INFORMADOS <==\n" + "\tΣ   = " + alf + "\n" + "" + "\tQ   = " + est + "\n"
						+ "\tδ   = \n" + "ESTADO PARTIDA:         Q" + Arrays.toString(estP) + "\n"
						+ "CARACTER CONSUMIDO: Σ" + Arrays.toString(le) + "\n" + "ESTADO DESTINO:          Q"
						+ Arrays.toString(estD) + "\n" + "" + "\tq0  = " + estIn + "\n" + "" + "\tF   = "
						+ conjuntoEstadosFinais + "\n" + "" + "**************************************************");
	}

	// ---------------------------METODOS DE ENTRADAS DE DADOS

	// VERIFICA SE PALAVRA PERTENCE AO ALFABETO
	private static boolean VerificaPalavra(String palavra, Character[] alf) {
		int cont = 0;
		for (int x = 0; x < palavra.length(); x++) {
			Character caracPalavra =  palavra.charAt(x);
			for (int y = 0; y < alf.length; y++) {
				if (caracPalavra.equals(alf[y])) {
					cont++;
				}
			}
		}

		if (cont == palavra.length()) {
			// JOptionPane.showMessageDialog(null, "TODOS OS SIMBOLOS PERTENCEM
			// AO ALFABETO!!!\n\n");
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "A palavra "+palavra+" contém simbolos não pertencentes ao conjunto de simbolos (alfabeto)!n",
					"WARNING", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}