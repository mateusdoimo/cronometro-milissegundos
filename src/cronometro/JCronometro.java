/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cronometro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Diego da Silva Parra - 10716550 e Mateus Fernandes Doimo - 10691971
 */
public class JCronometro extends JFrame{
    
    private boolean status; //status do cronometro
    private Thread thread;
    private final Contador cont;
    
    URL caminhoImagem; //icone
    Image iconeTitulo; //icone
    
    //Textos 
    JLabel labelMin = new JLabel("MINUTO", SwingConstants.CENTER);
    JLabel labelSeg = new JLabel("SEGUNDO", SwingConstants.CENTER);
    JLabel labelMil = new JLabel("MILISEGUNDO", SwingConstants.CENTER);
    
    //Caixas de texto 
    JTextField txtMin = new JTextField(10);
    JTextField txtSeg = new JTextField(10);
    JTextField txtMil = new JTextField(10);
    
    //Botões
    JButton btnClose = new JButton("Fechar");
    JButton btnReset = new JButton("Reset");
    JButton btnAcao = new JButton("Iniciar");
    
    //Painel 
    JPanel panel = new JPanel();
    
    public JCronometro() {
        super();
        
        cont = new Contador(this.txtMil, this.txtSeg, this.txtMin);
        thread = null;
        
        this.status = false;
        
        txtMin.setMargin(new Insets(10, 10, 10, 10));
        txtSeg.setMargin(new Insets(10, 10, 10, 10));
        txtMil.setMargin(new Insets(10, 10, 10, 10));
        
        txtMin.setEditable(false);
        txtSeg.setEditable(false);
        txtMil.setEditable(false);
        
        txtMin.setBackground(Color.white);
        txtSeg.setBackground(Color.white);
        txtMil.setBackground(Color.white);
        
        //cores dos botões
        btnAcao.setForeground(Color.white);
        btnAcao.setBackground(new Color(12,158,86));
        btnClose.setForeground(Color.white);
        btnClose.setBackground(Color.GRAY);
        btnReset.setForeground(Color.white);
        btnReset.setBackground(new Color(244,67,54));
        
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(3, 3));
        
        panel.add(labelMin);
        panel.add(labelSeg);
        panel.add(labelMil);
        
        panel.add(txtMin);
        panel.add(txtSeg);
        panel.add(txtMil);
        
        panel.add(btnClose);
        panel.add(btnReset);
        panel.add(btnAcao);
        
        this.add(panel, BorderLayout.CENTER);
        
        this.pack();
        
        //acões dos botões
        btnReset.addActionListener(this::zeraCronometro);
        btnClose.addActionListener(this::fecharCronometro);
        btnAcao.addActionListener(this::acaoCronometro);
        
        //icone
        caminhoImagem = this.getClass().getClassLoader().getResource("icone/icone.png");
        iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoImagem);

        setIconImage(iconeTitulo);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        //zera as caixas de texto
        colocaZero();
    } 
    //Fecha o programa
    private void fecharCronometro(ActionEvent evt) {
        cont.setCont(false);
        System.exit(0);
    }
    
    //Zera o crnometro e seta botões
    private void zeraCronometro(ActionEvent evt) {
        cont.setCont(false);
        status = false;
        thread = null;
        btnAcao.setBackground(new Color(12,158,86));
        btnAcao.setText("Iniciar");
        colocaZero();
    }
    
    //Inicia ou pausa o cronometro
    private void acaoCronometro(ActionEvent evt){
        //inicia
        if(!this.status){
            status = true;
            cont.setPause(false);
            //caso não tenha criado a tharead
            if(thread ==  null){ iniciar(); }
            btnAcao.setBackground(new Color(33,150,243));
            btnAcao.setText("Pausar");
        }
        //pausa
        else{ 
            status = false;
            cont.setPause(true);
            cont.setZero(false);
            btnAcao.setBackground(new Color(12,158,86));
            btnAcao.setText("Iniciar");
        }
    }
    //coloca zero nas caixas de texto
    private void colocaZero(){
        txtMin.setText("00");
        txtSeg.setText("00");
    	txtMil.setText("000");
    }
    
    //cria a thread
    public void iniciar(){
        thread = new Thread(this.cont);
        cont.setCont(true);
        cont.setZero(true);
        thread.start();   
    }
}
