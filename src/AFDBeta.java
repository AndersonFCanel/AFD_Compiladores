import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument.BranchElement;

public class AFDBeta {
	public static void main(String[] args) {

		// ENTRADA DO ALFABETO
		String alfabeto = "a,b";

		String[] alfArray = splitVirgula(alfabeto);

		char[] alf = new char[alfArray.length];
		for (int k = 0; k < alfArray.length; k++) {
			alf[k] = alfArray[k].charAt(0);
		}

		// ENTRADA DO CONJUNTO DE ESTADOS
		String estad = "A,B,C,D,E";// = "q0,q1,q2,q3";
		boolean validEst = false;
	
		String[] estArray = splitVirgula(estad);

		int estados = estArray.length;
		int[] est = new int[estados];

		for (int k = 0; k < estados; k++) {
			est[k] = k;
		}
		// --------------------------------------------------------------------------------------------------------------
		// ENTRADA FUNCAO DE TRANSIȃO modelo formal
		String delta = null;
		String[] funcDeltaAux = new String[alfArray.length * estArray.length];

		boolean valFunc2 = false;
		boolean valFunc = false;

		int c = 0, cont1 = 0;
		tutorialTransicao();
		for1: for (c = 0; c < (alfArray.length * estArray.length); c++) {
			delta = entraFunc();

			try {
				if (delta.equals(null)) {
				}
			} catch (Exception e) {
				entradaInvalida();
				JOptionPane.showMessageDialog(null, "Para sair use a tecla 's'!");
				c--;
				continue for1;
			}

			sair: if (delta.equalsIgnoreCase("S")) {
				break for1;
			}

			tutorial: if (delta.equalsIgnoreCase("I")) {
				tutorialTransicao();
				c--;
				continue for1;
			}

			anteriores: if (delta.equalsIgnoreCase("A")) {
				JOptionPane.showMessageDialog(null,
						"Transições informadas até o momento:\n" + Arrays.toString(funcDeltaAux));
				c--;
				continue for1;
			}

			valFunc = checkFunc(delta, estArray);
			// valFunc2 = checkEqualsFunc(delta, alfArray,
			// estArray,funcDeltaAux); //CORRIGIR VALIDADOR

			if (!valFunc) {
				funcDeltaAux[c] = delta;
				cont1++;
			} else {
				entradaInvalida();
				c--;
			}

		}
		// ---------TRATAMENTO FUNÇÃO DE TRANSIÇÃO
		String[] funcDelta = new String[alfArray.length * estArray.length];
		for (c = 0; c < cont1; c++) {
			funcDelta[c] = funcDeltaAux[c];
		}

		// -->>

		String[] estadoPartidaS = new String[funcDelta.length];
		String[] caracConsumidoS = new String[funcDelta.length];
		String[] estadoDestinoS = new String[funcDelta.length];

		int[] estadoPartida = new int[cont1];
		int[] estadoDestino = new int[cont1];
		char[] le = new char[cont1];

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

		for (int p = 0; p < cont1; p++) {

			String aux = estadoPartidaS[i];
			estadoPartida[i] = Integer.parseInt(String.valueOf(aux.charAt(1)));

			aux = estadoDestinoS[i];
			estadoDestino[i] = Integer.parseInt(String.valueOf(aux.charAt(1)));

			aux = caracConsumidoS[i];
			le[i] = aux.charAt(0);
			i++;

		}

		// ----APOS O TRATAMENTO----------
		System.out.println("EP: " + Arrays.toString(estadoPartida));
		System.out.println("CC: " + Arrays.toString(le));
		System.out.println("ED: " + Arrays.toString(estadoDestino));

		// -------------------------------------------------
		// ENTRA COM ESTADO INICIAL
		String estadoi = "A";

		// -------------------------------------------------
		// ENTRA COM ESTADOS FINAIS
		
		String estadosFinais = "E"; 

		String[] estFin = splitVirgula(estadosFinais);
		ArrayList lista = new ArrayList(Arrays.asList(estArray));


		int qestadosf = 0;
		qestadosf = estFin.length;
		int[] estadosf = new int[qestadosf];

		for (int p = 0; p < qestadosf; p++) {
			String aux = estFin[p];
			estadosf[p] = Integer.parseInt(String.valueOf(aux.charAt(1)));
		}

		// -------------------------------------------------
		imprimirAutomato(alfArray, estArray, funcDelta, estadoi, estFin);
		// -------------------------------------------------
		// ------------------INSERE E VERIFICA PALAVRA
		String palavraS;
		boolean flagPal;
		// PALAVRAS
		do {
			int teste = 0;
			int teste1 = 0; // #CRIADO
			palavraS = JOptionPane.showInputDialog(null,
					"Entre com a palavra a ser verificada: \nPara conferir suas entradas digite anteriores digite 'I'\nPara sair digite s");
			if (palavraS.equalsIgnoreCase("s")) {
				break;
			}
			if (palavraS.equalsIgnoreCase("I")) {
				imprimirAutomato(alfArray, estArray, funcDelta, estadoi, estFin);
				palavraS = JOptionPane.showInputDialog(null,
						"Entre com a palavra a ser verificada: \nPara conferir suas entradas digite anteriores digite 'I'\nPara sair digite s");
				if (palavraS.equalsIgnoreCase("s")) {
					break;
				}
			}

			// -----VALIDAÇÃO DA PALAVRA
			flagPal = VerificaPalavra(palavraS, alfArray);
			if (!flagPal) {
				System.out.println("INVALIDA PALAVRA");
			} else {

				char[] palavra = palavraS.toCharArray();
				int palavralen = palavra.length;
				int estadoa = estadoi;
				int aux = 0;

				for (int p = 0; p < palavralen; p++) {
					int mudou = 0;
					int j = 0;

					for (int k = 0; k < cont1; k++) {

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
						// JOptionPane.showMessageDialog(null,
						// "Nao tem regra para esse simbolo, ele não pertenbce
						// ao alfabeto informado.");
						teste1 = 1;// #ALTERADO
						break;
					}

					for (int k = 0; k < qestadosf; k++) {
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
					JOptionPane.showMessageDialog(null, "PALAVRA NÃO ACEITA PELO AUTOMATO\n\n");
					// break;
				}
			}
		} while (!palavraS.equalsIgnoreCase("s"));
		JOptionPane.showMessageDialog(null, "Voce finalizou a aplicaçao, obrigado!");
	}

	// ---------------METODOS-------------------------------------
	// ---- METODOS DE IMPRESSÃO DEINFORMAÇOES -

	// ENTRADA INVALIDA
	private static void entradaInvalida() {
		JOptionPane.showMessageDialog(null, "ENTRADA INVALIDA", "WARNING", JOptionPane.WARNING_MESSAGE);
	}

	// ENTRADA VALIDA
	private static void entradaValidada() {
		JOptionPane.showMessageDialog(null, "Entrada VERIFICADA\n");
	}

	// FUNÇAÕ DE TRANSIÇÃO INVALIDA
	private static void funcInval(String delta) {
		JOptionPane.showMessageDialog(null, "Sua entrada esta fora do modelo permitido!\n");
		tutorialTransicao();
	}

	// TUTORIAL DE ENTRADA PARA FUNȿO DE TRANSIȃO
	private static void tutorialTransicao() {
		JOptionPane.showMessageDialog(null, "ATENÇÃO AO MODELO PARA ENTRADA DA FUNÇÃO DE TRANSIÇÃO DE ESTADOS\n"
				+ "PASSO 1: Primeiro entre com o estado inicial - EX: q0\n" + "PASSO 2: DIGITE UMA VIRGULA \",\"\n"
				+ "PASSO 3: Entre com o caracter a ser consumido pelo estado inicial - EX: a\n"
				+ "PASSO 4: DIGITE PONTO E VIRGULA. \";\"\n" + "PASSO 5: Entre com o estado de destino - EX: q1\n"
				+ "PASSO 6: APERTE ENTER\n" + "A entrada deve estar da forma do exemplo abaixo\n" + "EX: q0,a;q1",
				"WARNING", JOptionPane.WARNING_MESSAGE);
	}

	// IMPRIME ENTRADAS
	private static void imprimirAutomato(String[] alf, String[] est, String[] funcDelta, String estIn, String[] estFin) {
		JOptionPane.showMessageDialog(null,
				"**************************************************\n" + "\tIMPRIMINDO ENTRADAS DO AUTOMATO\n\n"
						+ "\tO alfabeto: Σ \n" + "\tOs estados: Q = {S1, S2...}\n" + "\tAs transicoes: (δ: Q × Σ → Q)\n"
						+ "\tO  estado Ininicial: q0\n" + "\tO conjunto dos estados finais: F\n"
						+ "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n" + "\tΣ   = " + Arrays.toString(alf) + "\n" + ""
						+ "\tQ   = " + Arrays.toString(est) + "\n" + "\tδ   = " + Arrays.toString(funcDelta) + "\n" + ""
						+ "\tq0  = q" + estIn + "\n" + "" + "\tF   = " + Arrays.toString(estFin) + "\n" + ""
						+ "**************************************************");
	}

	// ---------------------------METODOS DE ENTRADAS DE DADOS


	// ENTRAR PALAVRA
	private static String entrarPalavra() {
		String palavra = JOptionPane.showInputDialog(null, "entre com a palavra a ser verificada pelo afd:\n"
				+ "caso queira sair, digite 's'\n" + "caso queira imprimir o automato digite 'i' \n" + " ");
		return palavra;
	}

	// ENTRADA DA FUNÇÃO DE TRANSIÇAO
	private static String entraFunc() {

		String delta = JOptionPane.showInputDialog(null,
				"\nEntre com as transiçãos de estado (δ: Q × Σ → Q):\n" + "\nPara ver o tutorial  novamente : 'i'"
						+ "\nPara sair : 's'" + "\nVer entradas anteriores 'a'" + "\n ");
		return delta;
	}

	// SPLIT PARA SEPARAR ENTRADAS QUE CONTEM VIRGULA
	private static String[] splitVirgula(String valor) {
		return valor.split(",");// Divide a String quando ocorrer ","
	}

	// ---------METEDOS VALIDADORES

	
	

	

	// VALIDADOR PARA FUNÇÃO DE TRANSIÇÃO
	private static boolean checkFunc(String delta, String[] estArray) {
		boolean validador = false;

		try {
			if (delta.equals(null)) {
			}
		} catch (Exception e) {
			entradaInvalida();
			return validador = true;
		}

		if (estArray.length > 9) {
			if (delta.length() < 7 || delta.length() > 9) {
				funcInval(delta);
				return validador = true;
			}
		} else {

			if (delta.length() < 7 || delta.length() > 7) {
				funcInval(delta);
				return validador = true;
			}
		}

		validadorDeVirgulaEPtEVirgula: if (delta.charAt(2) != ',' || delta.charAt(4) != ';') {
			funcInval(delta);
			return validador = true;

		}

		return false;
	}

	// VERIFICA IGUALDADE DAS FUNÇÕE DE TRANSIÇÃO -- CORRIGIR
	private static boolean checkEqualsFunc(String delta, String[] alfArray, String[] estArray, String[] funcDeltaAux) {
		boolean validador = false;
		for1: for (int i = 0; i < alfArray.length * estArray.length - 1; i++) {
			for (int j = i + 1; j < alfArray.length * estArray.length; j++) {
				if (funcDeltaAux[i] == funcDeltaAux[j]) {
					JOptionPane.showMessageDialog(null, "ENTRADAS IGUIAS " + funcDeltaAux[i] + " e " + funcDeltaAux[j],
							"WARNING", JOptionPane.WARNING_MESSAGE);
					// funcInval(delta);
					validador = true;
					break for1;
				}
			}

		}
		return validador;
	}

	// VERIFICA SE PALAVRA PERTENCE AO ALFABETO
	private static boolean VerificaPalavra(String palavra, String[] alf) {

		int cont = 0;
		for1: for (int x = 0; x < palavra.length(); x++) {
			String caracPalavra = "" + palavra.charAt(x);
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
			JOptionPane.showMessageDialog(null, "CONTÉM SIMBOLOS NÃO PERTENCENTES AO ALFABETO!!!\n\n", "WARNING",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}