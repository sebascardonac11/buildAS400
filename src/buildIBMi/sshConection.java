package buildIBMi;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class sshConection {

	private int port = 22;
	private JSch jsch;
	private Session session;
	private ChannelSftp sftpChannel;
	private Channel channelShell;
	private Scanner scanner;

	public sshConection(String user, String password, String host) {
		try {
			this.jsch = new JSch();
			this.session = jsch.getSession(user, host, port);
			this.session.setPassword(password);
			this.session.setConfig("StrictHostKeyChecking", "no");
			System.out.println("Establishing Connection...");
			this.session.connect();
			System.out.println("Connection established.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void putProject() {
		try {
			this.sftpChannel.put("../spikeas400/");
		} catch (SftpException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getDetail() {
		/*System.out.println("Crating SFTP Channel.");
		 this.sftpChannel = (ChannelSftp) session.openChannel("shell");
		 this.sftpChannel.connect();
		System.out.println("SFTP Channel created.");*/
		String line = "";
		String remoteFile = "/home/BDCARDONAS/log.log";
		try {
			InputStream inputStream = this.sftpChannel.get(remoteFile);
			scanner = new Scanner(new InputStreamReader(inputStream));
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return line;
	}

	public void build(String command) {
		try {
			this.channelShell = this.session.openChannel("exec");
			((ChannelExec) channelShell).setCommand(command);
			channelShell.setInputStream(null);
			((ChannelExec) channelShell).setErrStream(System.err);
			InputStream input = this.channelShell.getInputStream();
			this.channelShell.connect();

			InputStreamReader inputReader = new InputStreamReader(input);
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
			inputReader.close();
			;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void close() {
		this.channelShell.disconnect();
		this.session.disconnect();
	}

}
