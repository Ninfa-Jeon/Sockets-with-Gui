import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Date;
public class ServerWindow extends javax.swing.JFrame {
    public ServerWindow() {
        try {
            ServerWindow.sendLog = new FileOutputStream("C:\\Users\\HP\\Desktop\\serverLog.txt",true);
        } catch (FileNotFoundException ex) {
        }
        initComponents();
    }
    public static PrintWriter pwPrintWriter;
    public static String message;
    public static BufferedReader input = null;//get userinputOutputStream sendLog 
    public static OutputStream sendLog;
    
        
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        jFrame1.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SERVER");
        setBackground(new java.awt.Color(0, 51, 51));
        setForeground(new java.awt.Color(255, 255, 255));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0, 51, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setText("Server waiting for connection on port 444");
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(0, 51, 51));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setToolTipText("Enter your message");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(9, 9, 9))
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        message = jTextField1.getText();
        InputStream targetStream = new ByteArrayInputStream(message.getBytes());
        //Date object
        Date date= new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class 
        Timestamp ts = new Timestamp(time);
        try {
            sendLog.write(("[ME - "+ts+"]: "+message+'\n').getBytes());
        } catch (IOException ex) {
        }
        input = new BufferedReader(new InputStreamReader(targetStream));
        pwPrintWriter.println(message);//send message to client with PrintWriter
        pwPrintWriter.flush();//flush the PrintWriter
        jTextArea1.append(">>>"+message+"\n");
        jTextField1.setText("");
       
    }                                        
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException  {                
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerWindow().setVisible(true);
            }
        });
	final int port = 444;
	ServerSocket ss = new ServerSocket(port);
	Socket clientSocket = ss.accept();
        jTextArea1.append("\nReceived connection from "+clientSocket.getInetAddress()+" on port "+clientSocket.getPort()+"\n");
	//create two threads to send and receive from client
	ReceiveFromClientThread receive = new ReceiveFromClientThread(clientSocket);
	Thread thread = new Thread(receive);
	thread.start();
	SendToClientThread send = new SendToClientThread(clientSocket);
	Thread thread2 = new Thread(send);
	thread2.start();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextArea1;
    public static javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}
class ReceiveFromClientThread implements Runnable{
    Socket clientSocket=null;
    BufferedReader brBufferedReader = null;
    ReceiveFromClientThread(Socket clientSocket){
	this.clientSocket = clientSocket;
    }//end constructor
    @Override
    public void run() {
	try{
            brBufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));			
            String messageString;
            while(true){
		while((messageString = brBufferedReader.readLine())!= null){//assign message from client to messageString
                    if(messageString.equals("EXIT"))
			break;//break to close socket if EXIT
                    //Date object
                    Date date= new Date();
                    //getTime() returns current time in milliseconds
                    long time = date.getTime();
                    //Passed the milliseconds to constructor of Timestamp class 
                    Timestamp ts = new Timestamp(time);
                    ServerWindow.jTextArea1.append("From Client: " + messageString+"\n");//print the message from client
		    ServerWindow.sendLog.write(("[CLIENT - "+ts+"]: "+messageString+'\n').getBytes());
                }
                this.clientSocket.close();
                System.exit(0);
            }		
	}catch(Exception ex){ServerWindow.jTextArea1.append(ex.getMessage());}
    }
}//end class ReceiveFromClientThread
class SendToClientThread implements Runnable{
    Socket clientSock = null;	
    SendToClientThread(Socket clientSock){
	this.clientSock = clientSock;
    }
    @Override
    public void run() {
	try{
            ServerWindow.pwPrintWriter =new PrintWriter(new OutputStreamWriter(this.clientSock.getOutputStream()));//get outputstream
        }catch(Exception ex){ServerWindow.jTextArea1.append(ex.getMessage());}	
    }//end run
}//end class SendToClientThread
