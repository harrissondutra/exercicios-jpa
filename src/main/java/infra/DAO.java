package infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DAO<E> {
	
	private static EntityManagerFactory emf; //CRIAR ATRIBUTO ENTITYMANAGER FACTORY
	private EntityManager em; // CRIAR ATRIBUTO ENTITYMANAGER
	private Class<E> classe; // CRIAR UM ATRIBUTO PARA CLASSE GEN�RICA
	
	//CRIAR O ENTITYMANAGER FACTORY COM TRY/CATCH EST�TICO
	static {
		try {
			emf = Persistence
					.createEntityManagerFactory("exercicios-jpa"); // INSTANCIANDO O ENTITY MANAGER
		} catch (Exception e) {
			// logar -> log4j
		}
	}
	
	//CRIAR CONSTRUTORES
	
	public DAO() {
		this(null);
	}
	
	public DAO(Class<E> classe) {
		this.classe = classe;
		
		em = emf.createEntityManager(); // ATRIBUIR O MANAGER FACTORY PARA O ENTITY MANAGER
	}
	
	//CRIAR M�TODO PARA ABRIR TRANSA��O
	public DAO<E> abrirT() {
		em.getTransaction().begin(); //ATEN��O AOS M�TODOS AQUI USA BEGIN, PARA ABRIR
		return this;
	}
	
	//FECHAR TRANSA��O
	public DAO<E> fecharT() {
		em.getTransaction().commit();//ATEN��O AOS M�TODOS AQUI USA COMMIT, PARA FECHAR
		return this;
	}
	//CRIA M�TODO PARA INCLUIR - CRIANDO ATRIBUTO ENTIDADE E DEPOIS USA M�TODO PERSIST A ENTIDADE AO ENTITY MANAGER
	public DAO<E> incluir(E entidade) {
		em.persist(entidade);
		return this;
	}

	//INCLUIR AT�MICO � UM M�TODO QUE FAZ TUDO JUNTO, ABRE, INCLUI E FECHA A TRANSA��O
	public DAO<E> incluirAtomico(E entidade) {
		return this.abrirT().incluir(entidade).fecharT();
	}
	
	// OBTER POR ID, CRIA UM ATRIBUTO ID E REORNA A CLASSE E O ID PARA O ENTITY MANAGER
	public E obterPorID(Object id) {
		return em.find(classe, id);
	}
	
	
	// CRIAR LISTA PARA OBTER TODOS OS DADOS PAGINADOS
	
	public List<E> obterTodos(int qtde, int deslocamento) {
		if(classe == null) {
			throw new UnsupportedOperationException("Classe nula."); // CRIAR EXCEPTION SE A CLASSE FOR NULA
		}
		
		String jpql = "select e from " + classe.getName() + " e"; // CRIAR QUERY, ATEN��O QUE N�O ACEITA * E CONCATENA COM O NOME DA CLASSE
		TypedQuery<E> query = em.createQuery(jpql, classe); // CRIAR UMA CLASSE DO TIPO TYPEDQUERY<E> E ATRIBUIR O ENTITYMANAGER PASSANDO O (JPQL, CLASSE)
		query.setMaxResults(qtde); // USANDO O JPQL PRA SETAR A QUANTIDADE, CHAMADO PELA VARIAVEL CRIADA
		query.setFirstResult(deslocamento); // INFORMANDO O PRIMEIRO ID A ASER CHAMADO
		return query.getResultList(); // RETORNA UM RESULTLIST DOS DADOS
	}
	
	// OBTER TODOS OS DADOS SENDO TRAZIDOS DA LISTA CRIADA obterTodos
		public List<E> obterTodos() {
			return this.obterTodos(10, 0); // RETORNA THIS.LISTA CRIADA
		}
	
		//
	public List<E> consultar(String nomeConsulta, Object... params) {
		TypedQuery<E> query = em.createNamedQuery(nomeConsulta, classe);
		
		for (int i = 0; i < params.length; i += 2) {
			query.setParameter(params[i].toString(), params[i + 1]);
		}
		
		return query.getResultList();
	}
	
	public E consultarUm(String nomeConsulta, Object... params) {
		List<E> lista = consultar(nomeConsulta, params);
		return lista.isEmpty() ? null : lista.get(0);
	}
	
	//M�TODO PARA FECHAR O DAO
	public void fechar() {
		em.close();
	}
}
