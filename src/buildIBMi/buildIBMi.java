package buildIBMi;

import java.io.File;
import java.util.LinkedList;

public class buildIBMi {
	private LinkedList<String> PF;
	public LinkedList<String> LF;
	private LinkedList<String> RPGLE;
	private LinkedList<String> CLP;

	public buildIBMi() {
		this.PF= new LinkedList<String>();
		this.LF= new LinkedList<String>();
		this.RPGLE= new LinkedList<String>();
		this.CLP= new LinkedList<String>();
	}
	public void cargarFuentes(String path) {
		File carpeta = new File(path);
		for (final File ficheroEntrada : carpeta.listFiles()) {
			switch (ficheroEntrada.getName()) {
			case "QDDSSRC":
				this.getDDS(ficheroEntrada);
				break;
			case "QRPGLESRC":
				this.getDDS(ficheroEntrada);
				break;
			case "QCLSRC":
				this.getDDS(ficheroEntrada);
				break;
			default:
				break;
			}

		}
	}

	private void getDDS(File carpeta) {
		int lastPeriodPos;
		for (int i = 0; i < carpeta.listFiles().length; i++) {
			String namFile =carpeta.listFiles()[i].getName().toUpperCase();
			lastPeriodPos= namFile.lastIndexOf('.');
			switch (namFile.substring(lastPeriodPos,namFile.length())) {
			case ".LF":
				this.getLF().add(namFile.substring(0, lastPeriodPos));
				break;
			case ".PF":
				this.getPF().add(namFile.substring(0, lastPeriodPos));
				break;
			case ".RPGLE":
				this.RPGLE.add(namFile.substring(0, lastPeriodPos));
				break;
			case ".CLP":
				this.CLP.add(namFile.substring(0, lastPeriodPos));
				break;
			default:
				break;
			}
		}
	}

	public LinkedList<String> getPF() {
		return PF;
	}
	/**
	 * @return the lF
	 */
	public LinkedList<String> getLF() {
		return LF;
	}
	/**
	 * @return the rPGLE
	 */
	public LinkedList<String> getRPGLE() {
		return RPGLE;
	}
	/**
	 * @return the cLP
	 */
	public LinkedList<String> getCLP() {
		return CLP;
	}
	
	
}
