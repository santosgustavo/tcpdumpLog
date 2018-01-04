package multiportal.firewall.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author mportal
 */
public class RelacaoDeMac {

    String linha;
    String MAC = null;

    public RelacaoDeMac() {

    }

    public String getIP(String MAC) {

        return "";
    }

    public String getMAC(String IP) throws IOException {
        FileReader file = new FileReader("C:\\Users\\mportal\\Desktop\\parametros\\relacao-ip");
        BufferedReader read = new BufferedReader(file);
        this.linha = read.readLine();
        while (linha != null) {
            MAC = linha.substring(32, 50);
            if (linha.contains("(incompleto)") || (linha.contains("HWaddress"))) {
                //Não faz nada
            } else if(linha.contains(IP)) {
                System.out.println(MAC);
            }
            linha = read.readLine();
        }
        return MAC;
    }

}
