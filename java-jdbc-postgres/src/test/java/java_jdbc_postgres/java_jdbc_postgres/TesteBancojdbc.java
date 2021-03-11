package java_jdbc_postgres.java_jdbc_postgres;

import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.BeanUserfone;
import model.Telefone;
import model.UserPosJava;

public class TesteBancojdbc {
	@Test
	public void initBanco() {
		UserPosDAO userPosDAO = new UserPosDAO();
		UserPosJava userposjava = new UserPosJava();

		userposjava.setNome("Chatricia");
		userposjava.setEmail("Chatricia@gmail.com");

		userPosDAO.salvar(userposjava);
	}

	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();

		try {
			List<UserPosJava> list = dao.listar();

			for (UserPosJava userposjava : list) {
				System.out.println("ID: " + userposjava.getId());
				System.out.println("Nome: " + userposjava.getNome());
				System.out.println("E-mail: " + userposjava.getEmail());
				System.out.println("------------------------------------------");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();

		try {
			UserPosJava userposjava = dao.buscar(1L);
			System.out.println("ID: " + userposjava.getId());
			System.out.println("Nome: " + userposjava.getNome());
			System.out.println("E-mail: " + userposjava.getEmail());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void initAtualizar() {

		try {

			UserPosDAO dao = new UserPosDAO();

			UserPosJava objetoBanco = dao.buscar(4L);
			objetoBanco.setNome("Nome atualizado");
			dao.atualizar(objetoBanco);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void initDeletar() {
		try {
			UserPosDAO dao = new UserPosDAO();
			dao.deletar(9L);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// telefone

	@Test
	public void testeInsertTelefone() {

		try {
			UserPosDAO dao = new UserPosDAO();
			Telefone telefone = new Telefone();

			telefone.setNumero("(61) 98756-5254");
			telefone.setTipo("trabalho");
			telefone.setUsuario(2L);

			dao.salvarTelefone(telefone);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testeCarregaFoneUser() {
		UserPosDAO dao = new UserPosDAO();

		List<BeanUserfone> beanUserfones = dao.listUserFone(1L);

		for (BeanUserfone beanUserfone : beanUserfones) {
			System.out.println(beanUserfone);
			System.out.println("------------------------------------------");

		}
	}
	// Delete table filhas e pai
	@Test
	public void testeDeleteUserFone() {
		try {
			UserPosDAO dao = new UserPosDAO();
			dao.deleteFonesPorUser(3L);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
