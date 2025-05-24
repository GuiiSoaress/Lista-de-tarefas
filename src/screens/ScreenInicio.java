package screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class ScreenInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ArrayList<String> tarefas = new ArrayList<>();
	private static DefaultListModel<String> modelTarefas = new DefaultListModel<>();
	private static int itemSelecionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScreenInicio frame = new ScreenInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void  escreverDados() {
		String path = "src/dados/tarefas.csv";

		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
			for(String linha : tarefas) {
				bw.write(linha);
				bw.newLine();
			}
		}catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public static void converterListModel() {
		modelTarefas.clear();
		for (String tarefa : tarefas) {
			 String[] partes = tarefa.split(",");
			 String linhaFormatada = String.format("%-35s %s", partes[0], partes[1]);
	         modelTarefas.addElement(linhaFormatada);
	    }
		
	}
	
	public static void carregarDados() {
		String path = "src/dados/tarefas.csv";
		
		modelTarefas.clear();
		tarefas.clear();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
	
			String linha = br.readLine();
			
			while(linha != null) {
				tarefas.add(linha);
				linha  = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		converterListModel();
	}
	
	
	
	public static void atualizarStatus(Boolean chkStatus) {
		String tarefa = tarefas.get(itemSelecionado);
		String[] partes = tarefa.split(",");
		
		if(chkStatus == true) {
			tarefas.set(itemSelecionado, partes[0]+ ",Concluido");
			converterListModel();
		}else {
			tarefas.set(itemSelecionado, partes[0]+ ",Pendente");
			converterListModel();
		}
	}
		
	/**
	 * Create the frame.
	 */
	public ScreenInicio() {
		
		
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setTitle("Lista de Tarefas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 934, 668);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(119, 207, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Lista de Tarefas");
		lblTitle.setFont(new Font("Segoe UI Black", Font.PLAIN, 40));
		lblTitle.setBounds(28, 11, 379, 49);
		contentPane.add(lblTitle);
		
		JList<String> listTarefas = new JList<>(modelTarefas);
		listTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTarefas.setForeground(new Color(0, 0, 0));
		listTarefas.setFont(new Font("Courier New", Font.PLAIN, 14));
		listTarefas.setBounds(29, 137, 396, 368);
		contentPane.add(listTarefas);
		
		
		
		JLabel lblNewLabel = new JLabel("Tarefas");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblNewLabel.setBounds(28, 99, 135, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblAdicionarTarefa = new JLabel("Adicionar Tarefa");
		lblAdicionarTarefa.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblAdicionarTarefa.setBounds(489, 137, 232, 28);
		contentPane.add(lblAdicionarTarefa);
		
		JTextField tfAddtarefa = new JTextField();
		tfAddtarefa.setBounds(487, 176, 232, 39);
		contentPane.add(tfAddtarefa);
		tfAddtarefa.setColumns(10);
		
		JButton btnAddTarefa = new JButton("Adicionar Tarefa");
		btnAddTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfAddtarefa.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O campo não pode estar vazio", "Aviso", JOptionPane.WARNING_MESSAGE);
				}else{
					tarefas.add(tfAddtarefa.getText() + ",Pendente");
					converterListModel();
					tfAddtarefa.setText("");
				}
			}
		});
		btnAddTarefa.setBounds(729, 177, 154, 38);
		contentPane.add(btnAddTarefa);
		
		JButton btnExcluirTarefa = new JButton("Excluir Tarefa");
		btnExcluirTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ItemSelecionado = listTarefas.getSelectedIndex();
		        if (ItemSelecionado != -1 ) {	  
		            tarefas.remove(ItemSelecionado);
		            converterListModel();
		        }else {
		            JOptionPane.showMessageDialog(null, "Selecione uma tarefa para remover.");
		        }
		    
			}
		});
		btnExcluirTarefa.setBounds(28, 526, 154, 38);
		contentPane.add(btnExcluirTarefa);
		
		JLabel lblEditarTarefa = new JLabel("Editar Tarefa");
		lblEditarTarefa.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblEditarTarefa.setBounds(490, 298, 232, 28);
		contentPane.add(lblEditarTarefa);
		
		JTextField txEditarTarefa = new JTextField();
		txEditarTarefa.setColumns(10);
		txEditarTarefa.setBounds(488, 337, 232, 39);
		contentPane.add(txEditarTarefa);
		
		JButton btnEditarTarefa = new JButton("Editar Tarefa");
		btnEditarTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txEditarTarefa.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Selecione uma tarefa!", "Aviso", JOptionPane.WARNING_MESSAGE);
				}else{
					tarefas.set(itemSelecionado, txEditarTarefa.getText()+ ",Pendente");
					converterListModel();
				}
			}
		});
		btnEditarTarefa.setBounds(730, 338, 154, 38);
		contentPane.add(btnEditarTarefa);
		
		JCheckBox chckbxStatus = new JCheckBox("Feito?");
		chckbxStatus.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        chckbxStatus.setBackground(new Color(119, 207, 255));
        chckbxStatus.setBounds(489, 470, 85, 35);
        contentPane.add(chckbxStatus);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			          	escreverDados();
			            JOptionPane.showMessageDialog(null, "Tarefas salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(null, "Erro ao salvar tarefas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			        }
			}
		});
        btnSalvar.setBounds(205, 526, 97, 38);
        contentPane.add(btnSalvar);
        
        listTarefas.addListSelectionListener(e -> { 
	        itemSelecionado = listTarefas.getSelectedIndex();
	        if (itemSelecionado != -1) {
	            String[] partes = tarefas.get(itemSelecionado).split(",");
	            txEditarTarefa.setText(partes[0]);
	            
	            if(partes[1].equals("Pendente")) {
	            	chckbxStatus.setSelected(false);
	            }else {
	            	chckbxStatus.setSelected(true);
	            }
	            
	            
	        }else {
	        	txEditarTarefa.setText("");
	        }
	        
        });
        
        
        chckbxStatus.addActionListener(e -> {
        	if(itemSelecionado < 0) {
        		atualizarStatus(chckbxStatus.isSelected());
        	}else {
        		chckbxStatus.setSelected(false);
        	}
            
        });
        
        
        
        JButton btnCarregar = new JButton("Carregar");
        btnCarregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		carregarDados();
        	}
        });
        btnCarregar.setBounds(312, 526, 113, 38);
        contentPane.add(btnCarregar);
        
       
        
        JLabel lblStatusDaTarefa = new JLabel("Status da tarefa");
        lblStatusDaTarefa.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
        lblStatusDaTarefa.setBounds(489, 435, 232, 28);
        contentPane.add(lblStatusDaTarefa);
		
	}
}
