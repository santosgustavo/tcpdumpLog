package multiportal.firewall.log;

import java.io.IOException;
import multiportal.firewall.utils.Log;


/**
 * Lê arquivo log de acesso gerado pelo tcpdump
 * formata com os parametros HORARIO > ORIGEM(IP) > DESTINO(Dominio) > TAMANHO(Byte)
 * Grava em um arquivo de txto
 */
public class App {
	public static void main(String[] args) throws IOException {
		
	System.out.println("Inicio.");		
		Log log = new Log();
		log.processar();
	System.out.println("Fim! - Gerou arquivo");	
					 
	}
}
