package buildIBMi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class main {

	public static void main(String[] args) {
		try (InputStream input = new FileInputStream("./config.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			String librery = "BDCARDONAS";
			String path = "/home/BDCARDONAS/SPIKEAS400/";
			buildIBMi ibmi = new buildIBMi();
			ibmi.cargarFuentes("../SPIKEAS400/");

			sshConection ssh = new sshConection(prop.getProperty("db.user"), prop.getProperty("db.password"),
					prop.getProperty("db.server"));
			
			for (int i = 0; i < ibmi.getPF().size(); i++) {
				System.out.println("Creando: " + ibmi.getPF().get(i));
				ssh.build("system -s \"BOBTOOLS/EXECWLIBS LIB(" + librery + ") CMD(BOBTOOLS/crtfrmstmf obj(" + librery
						+ "/" + ibmi.getPF().get(i) + ") cmd(CRTPF) srcstmf('" + path + "QDDSSRC/" + ibmi.getPF().get(i)
						+ ".PF') parms('AUT(*EXCLUDE) DLTPCT(*NONE) REUSEDLT(*NO) SIZE() OPTION(*EVENTF *SRC *LIST) TEXT(''UTL: Evaluacion Qrys Resultado DSPFD'')'))\" ");

			}
			
			for (int i = 0; i < ibmi.getLF().size(); i++) {
				System.out.println("Creando: " + ibmi.getLF().get(i));
				ssh.build("system -s \"BOBTOOLS/EXECWLIBS LIB(" + librery + ") CMD(BOBTOOLS/crtfrmstmf obj(" + librery
						+ "/" + ibmi.getLF().get(i) + ") cmd(CRTLF) srcstmf('" + path + "QDDSSRC/" + ibmi.getLF().get(i)
						+ ".LF') parms('AUT(*EXCLUDE) OPTION(*EVENTF *SRC *LIST) TEXT(''UTL: Evaluacion Qrys Resultado DSPFD'')'))\" ");

			}
			for (int i = 0; i < ibmi.getRPGLE().size(); i++) {
				System.out.println("Creando: " + ibmi.getRPGLE().get(i));
				ssh.build("system -s \"BOBTOOLS/EXECWLIBS CMD(CRTBNDRPG PGM(" + librery + "/" + ibmi.getRPGLE().get(i)
						+ ") SRCSTMF('" + path + "QRPGLESRC/" + ibmi.getRPGLE().get(i)
						+ ".RPGLE') TEXT('UTL: Evaluacion Qrys Resultado DSPFD') OPTION(*EVENTF) DBGVIEW(*ALL) OUTPUT(*PRINT) AUT(*EXCLUDE)) LIB("
						+ librery + ")\"");
			}
			ssh.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
