package views;

import models.Candidato;
import models.Cargo;
import services.GeradorSumula;
import services.UrnaEletronica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TelaUrna extends JFrame implements ActionListener {
    
    private UrnaEletronica urna;
    
    // Controle do Fluxo de Votação
    private List<Cargo> ordemVotacao;
    private int indiceCargoAtual;
    private Cargo cargoAtual;
    private int qtdEleitoresQueVotaram = 0; // O nosso "segurança" da porta
    
    // Componentes visuais
    private JTextField displayNumero;
    private JLabel lblCargo;
    private JLabel lblNomeCandidato;
    private JLabel lblPartidoCandidato;
    private JLabel lblFotoPlaceholder;
    private JButton btnBranco, btnCorrige, btnConfirma, btnEncerrar;

    public TelaUrna(UrnaEletronica urna) {
        this.urna = urna;
        
        // Define a ordem em que o eleitor vai votar
        this.ordemVotacao = Arrays.asList(Cargo.SENADOR, Cargo.GOVERNADOR, Cargo.PRESIDENTE);
        this.indiceCargoAtual = 0;
        this.cargoAtual = ordemVotacao.get(indiceCargoAtual);
        
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setTitle("Simulador de Urna Eletronica");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(220, 220, 220));
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        painelCentral.setBackground(new Color(220, 220, 220));
        painelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ==========================================
        // LADO ESQUERDO: O Visor Branco
        // ==========================================
        JPanel painelVisor = new JPanel();
        painelVisor.setLayout(new BoxLayout(painelVisor, BoxLayout.Y_AXIS));
        painelVisor.setBackground(Color.WHITE);
        painelVisor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 3),
                new EmptyBorder(20, 20, 20, 20)
        ));

        lblCargo = new JLabel("CARGO: " + cargoAtual);
        lblCargo.setFont(new Font("SansSerif", Font.BOLD, 22));
        
        JPanel painelNumero = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNumero.setBackground(Color.WHITE);
        JLabel lblNumTxt = new JLabel("Numero: ");
        lblNumTxt.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        displayNumero = new JTextField(5);
        displayNumero.setFont(new Font("SansSerif", Font.BOLD, 36));
        displayNumero.setEditable(false);
        displayNumero.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painelNumero.add(lblNumTxt);
        painelNumero.add(displayNumero);

        lblNomeCandidato = new JLabel("Nome: ");
        lblNomeCandidato.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        lblPartidoCandidato = new JLabel("Partido: ");
        lblPartidoCandidato.setFont(new Font("SansSerif", Font.PLAIN, 18));

        lblFotoPlaceholder = new JLabel("[ FOTO ]");
        lblFotoPlaceholder.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        lblFotoPlaceholder.setPreferredSize(new Dimension(110, 140));
        lblFotoPlaceholder.setMaximumSize(new Dimension(110, 140));
        lblFotoPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);

        painelVisor.add(lblCargo);
        painelVisor.add(Box.createVerticalStrut(20));
        painelVisor.add(painelNumero);
        painelVisor.add(Box.createVerticalStrut(10));
        painelVisor.add(lblNomeCandidato);
        painelVisor.add(Box.createVerticalStrut(10));
        painelVisor.add(lblPartidoCandidato);
        painelVisor.add(Box.createVerticalStrut(20));
        painelVisor.add(lblFotoPlaceholder);

        painelCentral.add(painelVisor);

        // ==========================================
        // LADO DIREITO: Teclado Preto
        // ==========================================
        JPanel painelDireita = new JPanel(new BorderLayout(0, 10));
        painelDireita.setBackground(new Color(220, 220, 220));

        JLabel lblTituloUrna = new JLabel("URNA ELEITORAL", SwingConstants.CENTER);
        lblTituloUrna.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTituloUrna.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        painelDireita.add(lblTituloUrna, BorderLayout.NORTH);

        JPanel painelTecladoFundo = new JPanel(new BorderLayout(10, 10));
        painelTecladoFundo.setBackground(new Color(40, 40, 40)); 
        painelTecladoFundo.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel painelNumeros = new JPanel(new GridLayout(4, 3, 15, 15));
        painelNumeros.setBackground(new Color(40, 40, 40));
        
        for (int i = 1; i <= 9; i++) {
            painelNumeros.add(criarBotaoNumerico(String.valueOf(i)));
        }
        painelNumeros.add(new JLabel("")); 
        painelNumeros.add(criarBotaoNumerico("0")); 
        painelNumeros.add(new JLabel("")); 

        // Painel dos Botões Coloridos de Ação
        JPanel painelAcoes = new JPanel(new GridLayout(1, 3, 10, 0));
        painelAcoes.setBackground(new Color(40, 40, 40));
        painelAcoes.setBorder(new EmptyBorder(20, 0, 0, 0));

        btnBranco = new JButton("BRANCO");
        btnBranco.setBackground(Color.WHITE);
        btnBranco.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnBranco.setContentAreaFilled(false); // Remove efeito nativo
        btnBranco.setOpaque(true);             // Garante a cor personalizada
        btnBranco.addActionListener(this);
        
        btnCorrige = new JButton("CORRIGE");
        btnCorrige.setBackground(new Color(255, 102, 0)); // Laranja forte
        btnCorrige.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCorrige.setContentAreaFilled(false);
        btnCorrige.setOpaque(true);
        btnCorrige.addActionListener(this);
        
        btnConfirma = new JButton("CONFIRMA");
        btnConfirma.setBackground(new Color(0, 153, 51)); // Verde clássico
        btnConfirma.setForeground(Color.WHITE);           // Texto branco para destacar
        btnConfirma.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnConfirma.setContentAreaFilled(false);
        btnConfirma.setOpaque(true);
        btnConfirma.addActionListener(this);

        painelAcoes.add(btnBranco);
        painelAcoes.add(btnCorrige);
        painelAcoes.add(btnConfirma);

        painelTecladoFundo.add(painelNumeros, BorderLayout.CENTER);
        painelTecladoFundo.add(painelAcoes, BorderLayout.SOUTH);
        painelDireita.add(painelTecladoFundo, BorderLayout.CENTER);

        painelCentral.add(painelDireita);
        add(painelCentral, BorderLayout.CENTER);

        // BOTÃO MESÁRIO
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTopo.setBackground(new Color(220, 220, 220));
        btnEncerrar = new JButton("Encerrar Sessao (Mesario)");
        btnEncerrar.setBackground(Color.RED);
        btnEncerrar.setForeground(Color.WHITE);
        btnEncerrar.setContentAreaFilled(false);
        btnEncerrar.setOpaque(true);
        btnEncerrar.addActionListener(this);
        painelTopo.add(btnEncerrar);
        
        add(painelTopo, BorderLayout.NORTH);
    }

    private JButton criarBotaoNumerico(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.addActionListener(e -> adicionarDigito(btn.getText()));
        return btn;
    }

    private int getLimiteDigitos(Cargo cargo) {
        if (cargo == Cargo.SENADOR) return 3;
        return 2; 
    }

    private void adicionarDigito(String digito) {
        int limite = getLimiteDigitos(cargoAtual);
        
        if (displayNumero.getText().length() < limite) {
            displayNumero.setText(displayNumero.getText() + digito);
        }
        
        if (displayNumero.getText().length() == limite) {
            int numero = Integer.parseInt(displayNumero.getText());
            Candidato candidato = urna.getPleito().buscarCandidatoPorNumeroECargo(numero, cargoAtual);
            
            if (candidato != null) {
                lblNomeCandidato.setText("Nome: " + candidato.getNome());
                lblPartidoCandidato.setText("Partido: " + candidato.getPartido());
                carregarFotoCandidato(numero);
            } else {
                lblNomeCandidato.setText("Nome: VOTO NULO");
                lblPartidoCandidato.setText("Partido: --");
                lblFotoPlaceholder.setIcon(null);
                lblFotoPlaceholder.setText("[ NULO ]");
            }
        }
    }

    // Carrega a foto dinamicamente com base no numero salvo na pasta "imagens"
    private void carregarFotoCandidato(int numeroCandidato) {
        try {
            String caminhoFoto = "Urna_Eletronica/imagens/" + numeroCandidato + ".jpg";
            File arquivoFoto = new File(caminhoFoto);
            
            // LINHA DE DEBUG: Vai imprimir no terminal o caminho exato que o Java está buscando
            System.out.println(">>> DEBUG: Procurando foto em: " + arquivoFoto.getAbsolutePath());
            
            if (arquivoFoto.exists()) {
                ImageIcon iconeOriginal = new ImageIcon(caminhoFoto);
                Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(110, 140, Image.SCALE_SMOOTH);
                lblFotoPlaceholder.setIcon(new ImageIcon(imagemRedimensionada));
                lblFotoPlaceholder.setText(""); 
            } else {
                lblFotoPlaceholder.setIcon(null);
                lblFotoPlaceholder.setText("[ SEM FOTO ]");
            }
        } catch (Exception ex) {
            lblFotoPlaceholder.setIcon(null);
            lblFotoPlaceholder.setText("[ ERRO FOTO ]");
        }
    }

    private void limparTelaVotacao() {
        displayNumero.setText("");
        lblNomeCandidato.setText("Nome: ");
        lblPartidoCandidato.setText("Partido: ");
        lblFotoPlaceholder.setIcon(null);
        lblFotoPlaceholder.setText("[ FOTO ]");
    }

    private void avancarParaProximoCargo() {
        if (indiceCargoAtual < ordemVotacao.size() - 1) {
            indiceCargoAtual++;
            cargoAtual = ordemVotacao.get(indiceCargoAtual);
            lblCargo.setText("CARGO: " + cargoAtual);
            limparTelaVotacao();
        } else {
            // O eleitor terminou de votar para Presidente. Incrementamos o contador!
            qtdEleitoresQueVotaram++;
            int limite = urna.getPleito().getTotalEleitores();
            
            if (qtdEleitoresQueVotaram >= limite) {
                // Bateu o limite! Encerra tudo automaticamente.
                JOptionPane.showMessageDialog(this, "FIM!\n\nLimite de " + limite + " eleitor(es) atingido.\nA urna sera encerrada automaticamente. Verifique o console.");
                finalizarEGerarSumula();
            } else {
                // Ainda tem gente na fila, reseta para o próximo.
                JOptionPane.showMessageDialog(this, "FIM!\n\nVoto concluido com sucesso.\nProximo eleitor pode se dirigir a urna.");
                indiceCargoAtual = 0;
                cargoAtual = ordemVotacao.get(indiceCargoAtual);
                lblCargo.setText("CARGO: " + cargoAtual);
                limparTelaVotacao();
            }
        }
    }

    // Criei este método auxiliar para não repetirmos código
    private void finalizarEGerarSumula() {
        urna.encerrarEleicao();
        this.dispose(); 
        GeradorSumula sumula = new GeradorSumula(urna);
        sumula.gerarRelatorioFinal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnConfirma) {
                if (displayNumero.getText().length() < getLimiteDigitos(cargoAtual)) {
                    JOptionPane.showMessageDialog(this, "Insira o numero completo ou vote em BRANCO.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                int numero = Integer.parseInt(displayNumero.getText());
                Candidato candidato = urna.getPleito().buscarCandidatoPorNumeroECargo(numero, cargoAtual);
                String destinoVoto = (candidato != null) ? candidato.getNome() : "VOTO NULO";
                
                // Ação de confirmação de voto
                int resposta = JOptionPane.showConfirmDialog(this, 
                        "Deseja confirmar seu voto para: " + destinoVoto + "?", 
                        "Confirmar Voto", JOptionPane.YES_NO_OPTION);
                
                if (resposta == JOptionPane.YES_OPTION) {
                    if (candidato != null) {
                        urna.registrarVotoValido(numero, cargoAtual);
                    } else {
                        urna.registrarVotoNulo(cargoAtual);
                    }
                    avancarParaProximoCargo();
                }
                
            } else if (e.getSource() == btnBranco) {
                int resposta = JOptionPane.showConfirmDialog(this, 
                        "Deseja confirmar seu voto em BRANCO?", 
                        "Confirmar Voto", JOptionPane.YES_NO_OPTION);
                
                if (resposta == JOptionPane.YES_OPTION) {
                    urna.registrarVotoBranco(cargoAtual);
                    avancarParaProximoCargo();
                }
                
            } else if (e.getSource() == btnCorrige) {
                limparTelaVotacao();
                
            } else if (e.getSource() == btnEncerrar) {
                JOptionPane.showMessageDialog(this, "Eleicao Encerrada pelo Mesario.\nVerifique o console para a Sumula Eleitoral.");
                finalizarEGerarSumula();
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Execucao", JOptionPane.ERROR_MESSAGE);
        }
    }
}