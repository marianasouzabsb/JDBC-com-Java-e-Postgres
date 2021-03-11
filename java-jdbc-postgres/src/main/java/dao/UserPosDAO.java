package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserfone;
import model.Telefone;
import model.UserPosJava;

public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	// Metodo INSERT
	public void salvar(UserPosJava userposjava) {
		try {
			String sql = "insert into userjava(nome,email) values (?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());
			insert.execute();
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback(); // revert a operação
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	// Metodo CONSULTA todos os itens do BD
	public List<UserPosJava> listar() throws Exception {

		ArrayList<UserPosJava> list = new ArrayList<UserPosJava>();

		String sql = "SELECT * FROM public.userjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			UserPosJava userposjava = new UserPosJava();

			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			list.add(userposjava);
		}

		return list;
	}

	// metodo consulta um objeto especifico
	public UserPosJava buscar(Long id) throws Exception {

		UserPosJava retorno = new UserPosJava();

		String sql = "SELECT * FROM public.userjava where id = " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));

		}
		return retorno;
	}
	//INNER JOIN
	
	public List<BeanUserfone> listUserFone (Long idUSer){
		
		List<BeanUserfone> beanUserFones = new ArrayList<BeanUserfone>();
		
		String sql= "SELECT nome,numero,email FROM telefoneuser as fone ";
		sql += "inner join userjava as userpos ";
		sql += "on fone.usuariopessoa = userpos.id ";
		sql += "where userpos.id = " + idUSer;
		
		try {
			PreparedStatement statement= connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				BeanUserfone userfone = new BeanUserfone();
				userfone.setNome(resultSet.getString("nome"));
				userfone.setNumero(resultSet.getString("numero"));
				userfone.setEmail(resultSet.getString("email"));
				beanUserFones.add(userfone);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return beanUserFones;
	}

	// Metodo atualizar UPDATE

	public void atualizar(UserPosJava userposjava) {

		try {
			String sql = "UPDATE public.userjava set nome = ? where id = " + userposjava.getId();
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setString(1, userposjava.getNome());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			
			try {
				connection.rollback();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
			e.printStackTrace();
		}

	}
	
	// DELETE
	public void deletar(Long id) {
		try {
			
			String sql = "delete from userjava where id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	//DELETE FONES
	
	public void deleteFonesPorUser(Long IdUser) {
		try {
			
			String sqlfone= "delete from telefoneuser where usuariopessoa =  " + IdUser;
			String sqlUser = "delete from userjava where id =  " + IdUser;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlfone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement= connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		
		
	}
	
	// INSERT na tabela filha
	public void salvarTelefone(Telefone telefone) {
		
		try {
			
			String sql = "insert into telefoneuser (numero, tipo, usuariopessoa) values (?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	

}
