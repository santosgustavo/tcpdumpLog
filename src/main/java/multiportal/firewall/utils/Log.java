package multiportal.firewall.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Log {

	private String arquivoOrigem;
	private String arquivoDestino;
	private String diretorioLogSujo = "C:\\Users\\mportal\\Desktop\\logs";
	//private String diretorioLogSujo = "/root/logs";
	private File[] listaDeArquivos = new File[254];
	private String nomeArquivoDiretorio;
	private File diretoriosDosArquivos = new File(diretorioLogSujo);
	private BufferedReader read;
	private String[] ipOrigem = new String[2];
        private RelacaoDeMac relacao = new RelacaoDeMac();
        private String mac;

	public Log() {

	}

	public void processar() throws IOException {

		// OBTEM LISTA DE ARQUIVOS DENTRO DO DIRETORIO ONDE CONTEM OS LOGS GERADOS PELO
		// TCPDUMP
		listaDeArquivos = diretoriosDosArquivos.listFiles();
		
		/*
		 * PERCORRE A LISTA DE ARQUIVOS OBTIDA, E CHAMA A FUNÇÃO LimparLog,
		 * arquivoOrigem: CAMINHO DO ARQUIVO DE LOG GERADO PELO TCPDUMP arquivoDestino:
		 * CAMINHO ONDE SERÁ GRAVADO ARQUIVO LIMPO nomeArquivoDiretorio: NOME DO ARQUIVO
		 * É O IP DE ORIGEM DO LOG
		 */
		for (int i = 0; i < listaDeArquivos.length; i++) {
			nomeArquivoDiretorio = listaDeArquivos[i].getName();
			ipOrigem = nomeArquivoDiretorio.split("-");
			

			arquivoOrigem = "C:\\Users\\mportal\\Desktop\\logs\\" + nomeArquivoDiretorio; /* Windows */
			//arquivoOrigem = "/root/logs/" + nomeArquivoDiretorio; /* Linux */

			arquivoDestino = "C:\\Users\\mportal\\Desktop\\files\\" + ipOrigem[0]; /* Windows */
			//arquivoDestino = "/root/multiportal.firewall.log/logs/" + ipOrigem[0]; /* Linux */

                        mac = relacao.getMAC(ipOrigem[0]);
			try {
				LimparLog(arquivoOrigem, arquivoDestino, ipOrigem[0], mac);
				listaDeArquivos[i].delete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void LimparLog(String arquivoOrigem, String arquivoDestino, String origem, String mac) throws IOException {
		FileWriter arquivo = new FileWriter(arquivoDestino, true);
		PrintWriter gravaNoArquivo = new PrintWriter(arquivo);
		FileReader file = new FileReader(arquivoOrigem);
		BufferedReader read = new BufferedReader(file);
		String linha = read.readLine();
		String auxiliar[] = new String[2];
		String horario;
		String destino[] = new String[3];
		String tamanhoPacote[] = new String[2]; 
		String parametros[] = new String[getTotalPacotes(arquivoOrigem)];
		int count = 0;

		while (linha != null) {

			if (linha.contains(">")) {
				auxiliar = linha.split(">");

				if (auxiliar[1].contains(":")) {
					destino = auxiliar[1].split(":");

					if (destino[1].contains("A?")) {

						destino[0] = getDestino(destino[1]);
						System.out.println(destino[1]);
						tamanhoPacote[1] = destino[1].substring(destino[1].length() - 3, destino[1].length() - 1);
						
					} else {
						if (linha.contains("length")) {
							if (destino[0].equals(" http")) {
								//Pula
							} else {
								destino[0] = getDestino(destino[0]);
								tamanhoPacote = linha.split("length");

								if (tamanhoPacote[1].contains(":")) {
									tamanhoPacote = tamanhoPacote[1].split(":");
									tamanhoPacote[1] = tamanhoPacote[0];

								}
							}

						} else {
							tamanhoPacote[1] = "?";
						}
					}

					horario = linha.substring(0, 8);
					
					System.out.println(linha);
					// PARAMETRO PARA GERAR RESUMO - Vetor com os parametros
					parametros[count++] = (horario + "  -->  " + origem + "  -->  " + destino[0] + "  --> "	+ tamanhoPacote[1]);
					// GRAVANDO NO ARQUIVO
					if (tamanhoPacote[1].equals(" 0")) {
						// PULA
					} else {
						gravaNoArquivo.println(horario + " > " + origem + "("+ mac +") > " + destino[0] + " > " + tamanhoPacote[1]);
					}

				}

			}

			linha = read.readLine();
		}
		file.close();
		arquivo.close();
		gerarResumo(parametros);
	}

	public void gerarResumo(String[] listaDeParametros) {

		for (int i = 0; i < listaDeParametros.length; i++) {

			// System.out.println(listaDeParametros[i]);

		}

	}

	public String getDestino(String destinoSujo) {
		String quebraDoSplit[] = new String[254];
		String destinoLimpo = null;

		if (destinoSujo.contains("?")) {
			quebraDoSplit = destinoSujo.split("\\?");
			destinoLimpo = quebraDoSplit[1].substring(0, quebraDoSplit[1].length() - 4);
		} else {
			if (destinoSujo.contains(".com")) {
				quebraDoSplit = destinoSujo.split("\\.");
				for (int i = 0; i < quebraDoSplit.length; i++) {
					if (quebraDoSplit[i].equals("com")) {
						destinoLimpo = quebraDoSplit[i - 1] + ".com.";
					}
				}

			}else {
				if(destinoSujo.contains("https")) {
					quebraDoSplit = destinoSujo.split("\\.");
					char elemento1 = quebraDoSplit[0].charAt(1);
					char elemento2 = quebraDoSplit[1].charAt(0);
					char elemento3 = quebraDoSplit[2].charAt(0);
					
					if(Character.isDigit(elemento1) && Character.isDigit(elemento2) && Character.isDigit(elemento3)) {
						destinoLimpo = destinoSujo;
					}else {
						destinoLimpo = quebraDoSplit[quebraDoSplit.length -3 ]+ "." + quebraDoSplit[ + quebraDoSplit.length - 2] +"."+ quebraDoSplit[quebraDoSplit.length -1];
					}
										
				}else if (destinoSujo.contains("http")) {
					quebraDoSplit = destinoSujo.split("\\.");
					if(quebraDoSplit.length  < 3) {
						destinoLimpo = destinoSujo;
					}else {
											
					char elemento1 = quebraDoSplit[0].charAt(1);
					char elemento2 = quebraDoSplit[1].charAt(0);
					char elemento3 = quebraDoSplit[2].charAt(0);
					
					if(Character.isDigit(elemento1) && Character.isDigit(elemento2) && Character.isDigit(elemento3)) {
						destinoLimpo = destinoSujo;
					}else {
						destinoLimpo = quebraDoSplit[quebraDoSplit.length -3 ]+ "." + quebraDoSplit[ + quebraDoSplit.length -2] +"."+ quebraDoSplit[quebraDoSplit.length -1];
					}
					}					
				}else {
					destinoLimpo = destinoSujo;
				}
				
				
			}

		}

		return destinoLimpo;
	}

	//Método retorna quantidade de linhas(cada linha é um pecote) do arquivo.
	public int getTotalPacotes(String origem) throws IOException {
		FileReader file = new FileReader(origem);
		read = new BufferedReader(file);
		String linha = read.readLine();
		int total = 0;

		while (linha != null) {
			total++;
			linha = read.readLine();
		}
		file.close();
		read.close();
		return total;
	}

}
