package screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	
//	função para salvar os dados no arquivo csv
	public void  escreverDados() {
		
//	     cria uma pasta na area de trabalho para tornar o arquivo portavel
		 File dadosListaDeTarefas = new File("dadosListaDeTarefas");
//		verifica se a pasta existe e se não  utiliza o metodo mkdirs da classe File para criar a pasta
		    if (!dadosListaDeTarefas.exists()) {
		    	dadosListaDeTarefas.mkdirs();
		    }

		    String path = "dadosListaDeTarefas/tarefas.csv";

		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
			for(String linha : tarefas) {
				bw.write(linha);
				bw.newLine();
			}
		}catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
//	função para "Copiar" o Array list em DefaltListModel para exibição no JList
	public static void converterListModel() {
		modelTarefas.clear();
		for (String tarefa : tarefas) {
//		     Formata a tarefa para exibição (Remove o ponto e deixa a a  tarefa com tamanho de 35 caracteres
			 String[] partes = tarefa.split(",");
			 String linhaFormatada = String.format("%-35s %s", partes[0], partes[1]);
	         modelTarefas.addElement(linhaFormatada);
	    }
		
	}
	
//	funcção para Carregar os dados do arquivo fonte CSV
	public static void carregarDados() {
		String path = "dadosListaDeTarefas/tarefas.csv";
		
//		limpa o array e o DefaltList Atual
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
		
//		Chama a função que converte em defaltList para exbição
		converterListModel();
	}
	
	
//	função para atualizar o status da tarefa (pendente/concluido)
	public static void atualizarStatus(Boolean chkStatus) {
		
//		divide a string a partir da virgula, ou seja, retira o status
		String tarefa = tarefas.get(itemSelecionado);
		String[] partes = tarefa.split(",");
		
//		atualiza a tarefa do array list com o novo status
		if(chkStatus == true) {
			tarefas.set(itemSelecionado, partes[0]+ ",Concluido");
			converterListModel();
		}else {
			tarefas.set(itemSelecionado, partes[0]+ ",Pendente");
			converterListModel();
		}
	}
		
	
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
		
		JList<String> listTarefas = new JList<>(modelTarefas);
		listTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTarefas.setForeground(new Color(0, 0, 0));
		listTarefas.setFont(new Font("Courier New", Font.PLAIN, 14));
		listTarefas.setBounds(29, 137, 396, 368);
		contentPane.add(listTarefas);
		
		JLabel lblTitle = new JLabel("Lista de Tarefas");
		lblTitle.setFont(new Font("Segoe UI Black", Font.PLAIN, 40));
		lblTitle.setBounds(29, 11, 379, 49);
		contentPane.add(lblTitle);
		
		JLabel lblNewLabel = new JLabel("Tarefas");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblNewLabel.setBounds(28, 99, 135, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblAdicionarTarefa = new JLabel("Adicionar Tarefa");
		lblAdicionarTarefa.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblAdicionarTarefa.setBounds(489, 137, 232, 28);
		contentPane.add(lblAdicionarTarefa);
		
		JLabel lblEditarTarefa = new JLabel("Editar Tarefa");
		lblEditarTarefa.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblEditarTarefa.setBounds(490, 298, 232, 28);
		contentPane.add(lblEditarTarefa);
		
		JLabel lblStatusDaTarefa = new JLabel("Status da tarefa");
        lblStatusDaTarefa.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
        lblStatusDaTarefa.setBounds(489, 435, 232, 28);
        contentPane.add(lblStatusDaTarefa);
		
        JTextField tfAddtarefa = new JTextField();
		tfAddtarefa.setBounds(487, 176, 232, 39);
		contentPane.add(tfAddtarefa);
		tfAddtarefa.setColumns(10);
		
		JTextField txEditarTarefa = new JTextField();
		txEditarTarefa.setColumns(10);
		txEditarTarefa.setBounds(488, 337, 232, 39);
		contentPane.add(txEditarTarefa);
		
		JCheckBox chckbxStatus = new JCheckBox("Feito?");
		chckbxStatus.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        chckbxStatus.setBackground(new Color(119, 207, 255));
        chckbxStatus.setBounds(489, 470, 85, 35);
        contentPane.add(chckbxStatus);
		
//      Botão para adicionar uma tarefa
		JButton btnAddTarefa = new JButton("Adicionar Tarefa");
		btnAddTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//se o  textField estiver vazio, mostra um erro
				if(tfAddtarefa.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O campo não pode estar vazio", "Aviso", JOptionPane.WARNING_MESSAGE);
				}else{
//					pega o texto do textField, adiciona o status e o salva no arraylist
					tarefas.add(tfAddtarefa.getText() + ",Pendente");
//					chama a função de conversão para exibição
					converterListModel();
					tfAddtarefa.setText("");
				}
			}
		});
		btnAddTarefa.setBounds(729, 177, 154, 38);
		contentPane.add(btnAddTarefa);
		
//		botão para excluir Tarefa
		JButton btnExcluirTarefa = new JButton("Excluir Tarefa");
		btnExcluirTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				faz uma condição com o item selecionado do Jlist e apaga o item selecionado com base no indice
		        if (itemSelecionado != -1 ) {	  
		            tarefas.remove(itemSelecionado);
		            converterListModel();
		        }else {
//		        	se nenhuma tarefa estiver selecionado aparece um erro
		            JOptionPane.showMessageDialog(null, "Selecione uma tarefa para remover.");
		        }
		    
			}
		});
		btnExcluirTarefa.setBounds(28, 526, 154, 38);
		contentPane.add(btnExcluirTarefa);
		
//		Botão de editar Tarefa
		JButton btnEditarTarefa = new JButton("Editar Tarefa");
		btnEditarTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				se o textField de tarefa estiver vazio mostra um erro
				if(txEditarTarefa.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Selecione uma tarefa!", "Aviso", JOptionPane.WARNING_MESSAGE);
				}else{
//					Edita o texto da tarefa com base no index do item selecionado na lista
					tarefas.set(itemSelecionado, txEditarTarefa.getText()+ ",Pendente");
					converterListModel();
				}
			}
		});
		btnEditarTarefa.setBounds(730, 338, 154, 38);
		contentPane.add(btnEditarTarefa);
		
//		Botão de salvar no arquivo CSV
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
//					 	chama a função de salvar dados e mostra pop ups de aviso
			          	escreverDados();
			            JOptionPane.showMessageDialog(null, "Tarefas salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(null, "Erro ao salvar tarefas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			        }
			}
		});
        btnSalvar.setBounds(205, 526, 97, 38);
        contentPane.add(btnSalvar);
        
//      Botão  de carregar dados que chama a função de carregar dados
        JButton btnCarregar = new JButton("Carregar");
        btnCarregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		carregarDados();
        	}
        });
        btnCarregar.setBounds(312, 526, 113, 38);
        contentPane.add(btnCarregar);
        
//      evento que coleta o item selecionado sempre que um item é selecionado
        listTarefas.addListSelectionListener(e -> { 
        	
	        itemSelecionado = listTarefas.getSelectedIndex();
	        
//	        se algum item estiver selecionado, adiciona o texto dele ao textField de editar tarefa para edição
	        if (itemSelecionado != -1) {
//	        	remove o status 
	            String[] partes = tarefas.get(itemSelecionado).split(",");
	            txEditarTarefa.setText(partes[0]);
	            
//	            Atualiza o checkbox baseado no status atual da tarefa
	            if(partes[1].equals("Pendente")) {
	            	chckbxStatus.setSelected(false);
	            }else {
	            	chckbxStatus.setSelected(true);
	            }
	            
	            
	        }else {
	        	txEditarTarefa.setText("");
	        }
	        
        });
        
//      evento que monitora o checkbox e chama a função de atualizar status
        chckbxStatus.addActionListener(e -> {
            if(itemSelecionado < 0) {
            	chckbxStatus.setSelected(false);
            	
            	
            	
            } else {
                atualizarStatus(chckbxStatus.isSelected());
            }
        });
        

	}
}
