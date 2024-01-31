package com.cag.twowheeler.config;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;

public class SshServerConfig {

	private static final int SSH_PORT = 22;

	public static void configureSshServer() throws Exception {
		SshServer sshd = SshServer.setUpDefaultServer();
		sshd.setPort(SSH_PORT);
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
		// Add more configurations if needed

		// Start the SSH server
		sshd.start();
	}

}
