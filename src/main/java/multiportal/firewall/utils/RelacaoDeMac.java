package multiportal.firewall.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author mportal-gustavo
 */
public class RelacaoDeMac {
	private String linha;
	private String MAC = null;
	private BufferedReader read;
	FileReader file;

	public RelacaoDeMac() throws FileNotFoundException {
		
	}
	
	public void processar() {
		
	}

	public String getIP(String MAC) throws IOException {
		file = new FileReader("C:\\Users\\mportal\\Desktop\\parametros\\relacao-ip");
		read = new BufferedReader(file);
		String ip = null;
	

		this.linha = read.readLine();
		while (linha != null) {
			if (linha.contains(MAC)) {
				ip = linha.substring(0, 20).trim();
			}

			linha = read.readLine();
		}

		file.close();
		return ip;
	}

	public String getMAC(String IP) throws IOException {
		//file = new FileReader("C:\\Users\\mportal\\Desktop\\parametros\\relacao-ip"); //Windows
		//file = new FileReader("/root/parametros/relacao-ip"); // Linux
		read = new BufferedReader(file);
		this.linha = read.readLine();
		while (linha != null) {

			if (linha.contains("(incompleto)") || (linha.contains("HWaddress"))) {
				// Não faz nada
			} else if (linha.contains(IP)) {
				MAC = linha.substring(32, 50);
			}
			linha = read.readLine();
		}
		return MAC;
	}

}
