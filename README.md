# AndroidEOF
Code snippet for the server:
```java
public static void main(String[] args) {
	try {
		ServerSocket serverSocket = new ServerSocket(12345);
		System.out.println("Started");
		while (true) {
			Socket socket = serverSocket.accept();
			DataInputStream dataIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

			int amountOfBytesToGenerate = dataIn.readInt();
			byte[] randomBytes = new byte[amountOfBytesToGenerate];
			new Random().nextBytes(randomBytes);

			System.out.println("Sending " + amountOfBytesToGenerate + " bytes to client " + socket.toString());

			dataOut.write(randomBytes);
			dataOut.flush();

			socket.close();
		}
	} catch(Exception ex) {
		ex.printStackTrace();
	}
}
```
