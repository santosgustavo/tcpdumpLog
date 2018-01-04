package multiportal.firewall.log;

import java.io.FileNotFoundException;
import java.io.IOException;
import multiportal.firewall.utils.Log;
import multiportal.firewall.utils.RelacaoDeMac;

/**
 * Lê arquivo log de acesso gerado pelo tcpdump formata com os parametros
 * HORARIO > ORIGEM(IP) > DESTINO(Dominio) > TAMANHO(Byte) Grava em um arquivo
 * de txto
 */
public class App {
	public static void main(String[] args) throws IOException {

		new Thread(t1).start();
		new Thread(t2).start();

	}

	public static Runnable t1 = new Runnable() {

		@Override
		public void run() {

			System.out.println("Inicio.");
			Log log;
			try {
				log = new Log();
				log.processar();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Fim");

		}
	};

	public static Runnable t2 = new Runnable() {

		@Override
		public void run() {
			RelacaoDeMac rmac;
			try {
				rmac = new RelacaoDeMac();
				rmac.processar();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	};
}
