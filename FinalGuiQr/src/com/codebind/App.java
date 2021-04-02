package com.codebind;


import com.company.libUtp;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.exception.QRGenerationException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;




public class App {


    private JPanel panelMain;
    private JButton ucodeButton;
    private JButton QRcodeButton;
    private JTextField TOKENTextField;
    private JTextField PORTTextField;


    public App() throws IOException {
        ucodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                libUtp kek = new libUtp();

                String PORTIO = PORTTextField.getText();
                String TOKENIO = TOKENTextField.getText();
                System.out.println(PORTIO);


               // kek.port = "20000";
               // kek.token = "BAC5D0F61C9ACB30710BB738F4866142";

                kek.port = PORTIO;
                 kek.token = TOKENIO;

                String RESOLT = null;

                //last contact PK
                JSONObject PKeyJO = null; //full json string
                try {
                    PKeyJO = new JSONObject(kek.getOwnContact());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }



                System.out.println(PKeyJO); //CHECK FULL

                 JSONObject PKeyOut; // result string

                PKeyOut = PKeyJO.getJSONObject("result");

                System.out.println(PKeyOut); // chek result

                // :)))

               //SONObject PKFinal = new JSONObject(PKeyOut);

               String PKey = PKeyOut.getString("pk");

                System.out.println(PKey);


                // PAST

                try {
                    RESOLT = kek.ucodeEncode(PKey, "350", "BASE64", "JPG");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                System.out.println(RESOLT); //CHECK RESOLT

                JSONObject JO = new JSONObject(RESOLT);


                String JPG_BASE64;

                JPG_BASE64 = JO.getString("result");

                System.out.println(JPG_BASE64); // CHECK BASE64

                //last decode


                Base64.Decoder dec = Base64.getDecoder();
                byte[] decbytes = dec.decode(JPG_BASE64);

                System.out.println(new String(decbytes)); // chek jpg code




                //last img out
                BufferedImage img = null;

                InputStream is = new ByteArrayInputStream(decbytes);
                try {
                    img = ImageIO.read(is);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                ImageIcon icon = new ImageIcon(img);


                JOptionPane.showMessageDialog(null, null, "UCode",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            }





        });
        QRcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                libUtp kek = new libUtp();

                String PORTIO = PORTTextField.getText();
                String TOKENIO = TOKENTextField.getText();
                System.out.println(PORTIO);


               // kek.port = "20000";
               // kek.token = "BAC5D0F61C9ACB30710BB738F4866142";

                kek.port = PORTIO;
                kek.token = TOKENIO;

                String RESOLT = null;

                //last contact PK
                JSONObject PKeyJO = null; //full json string
                try {
                    PKeyJO = new JSONObject(kek.getOwnContact());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }



                System.out.println(PKeyJO); //CHECK FULL

                JSONObject PKeyOut; // result string

                PKeyOut = PKeyJO.getJSONObject("result");

                System.out.println(PKeyOut); // chek result

                // :)))



                String PKey = PKeyOut.getString("pk");

                System.out.println(PKey); //PUBLIC KEY





                BufferedImage img = null;



                try {
                    img = generateQRCodeImage(PKey);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }


                ImageIcon icon = new ImageIcon(img);



                JOptionPane.showMessageDialog(null, null, "QRCode",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            }
        });
    }

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);










    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());

        return ImageIO.read(bis);
    }
}


