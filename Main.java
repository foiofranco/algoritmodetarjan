import java.io.*;
import java.util.*;

// Esta classe representa um grafo direcionado usando representação de lista de adjacência:
class Graph {

	// Nº de Vértices:
	private int V;

	// Lista de Adjacência:
	private LinkedList<Integer> adj[];
	private int Time;

	// Construtor:
	@SuppressWarnings("unchecked") Graph(int v)
	{
		V = v;
		adj = new LinkedList[v];

		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList();

		Time = 0;
	}

	// Função para adicionar uma aresta no gráfico:
	void addEdge(int v, int w) { adj[v].add(w); }

	// Uma função recursiva que encontra e imprime componentes fortemente conectados usando DFS.
	// u: O vértice a ser visitado em seguida.
  	// disc[]: Armazena os tempos de descoberta dos vértices visitados.
	// low[]: Primeiro vértice visitado (o vértice com tempo mínimo de descoberta), que pode ser alcançado a partir da subárvore enraizada no vértice atual.
	// st: Para armazenar todos os ancestrais conectados (pode ser parte do SCC).
	// stackMember[]: Verificação mais rápida se um nó está na pilha.

	void SCCUtil(int u, int low[], int disc[],
				boolean stackMember[], Stack<Integer> st)
	{

		// Inicializar tempo de descoberta e valor do low.
		disc[u] = Time;
		low[u] = Time;
		Time += 1;
		stackMember[u] = true;
		st.push(u);

		int n;

		// Captação dos vértices adjacentes.
		Iterator<Integer> i = adj[u].iterator();

		while (i.hasNext()) {
			n = i.next();

			if (disc[n] == -1) {
				SCCUtil(n, low, disc, stackMember, st);

				// Verifica se a subárvore enraizada com v tem uma conexão com um dos ancestrais de u.
				low[u] = Math.min(low[u], low[n]);
			}
			else if (stackMember[n] == true) {

				// Atualiza o valor 'u' de low somente se 'v' ainda estiver na pilha (ou seja, for uma aresta posterior, não uma aresta cruzada).
				low[u] = Math.min(low[u], disc[n]);
			}
		}

		// Nó principal encontrado, percorra a pilha e imprime um SCC para armazenar os vértices extraídos da pilha.
		int w = -1;
		if (low[u] == disc[u]) {
			while (w != u) {
				w = (int)st.pop();
				System.out.print(w + " ");
				stackMember[w] = false;
			}
			System.out.println();
		}
	}

	// A função para realizar a busca em profundidade.
	// It uses SCCUtil()
	void SCC()
	{

		// Marca todos os vértices como não visitados e inicializa os arrays pais e visitados juntamente de ap (articulation point - ponto de articulação).
		int disc[] = new int[V];
		int low[] = new int[V];
		for (int i = 0; i < V; i++) {
			disc[i] = -1;
			low[i] = -1;
		}

		boolean stackMember[] = new boolean[V];
		Stack<Integer> st = new Stack<Integer>();

		// Chama a função recursiva auxiliar para encontrar pontos de articulação na árvore DFS com raiz no vértice 'i'.
		for (int i = 0; i < V; i++) {
			if (disc[i] == -1)
				SCCUtil(i, low, disc, stackMember, st);
		}
	}

	// Início
	public static void main(String args[])
	{

		// Instânciar Grafo
		Graph g1 = new Graph(5);

		g1.addEdge(1, 0);
		g1.addEdge(0, 2);
		g1.addEdge(2, 1);
		g1.addEdge(0, 3);
		g1.addEdge(3, 4);
		System.out.println("CFC no primeiro grafo: ");
		g1.SCC();

		Graph g2 = new Graph(4);
		g2.addEdge(0, 1);
		g2.addEdge(1, 2);
		g2.addEdge(2, 3);
		System.out.println("\nCFC no segundo grafo: ");
		g2.SCC();

		Graph g3 = new Graph(7);
		g3.addEdge(0, 1);
		g3.addEdge(1, 2);
		g3.addEdge(2, 0);
		g3.addEdge(1, 3);
		g3.addEdge(1, 4);
		g3.addEdge(1, 6);
		g3.addEdge(3, 5);
		g3.addEdge(4, 5);
		System.out.println("\nCFC no terceiro grafo: ");
		g3.SCC();

		Graph g4 = new Graph(11);
		g4.addEdge(0, 1);
		g4.addEdge(0, 3);
		g4.addEdge(1, 2);
		g4.addEdge(1, 4);
		g4.addEdge(2, 0);
		g4.addEdge(2, 6);
		g4.addEdge(3, 2);
		g4.addEdge(4, 5);
		g4.addEdge(4, 6);
		g4.addEdge(5, 6);
		g4.addEdge(5, 7);
		g4.addEdge(5, 8);
		g4.addEdge(5, 9);
		g4.addEdge(6, 4);
		g4.addEdge(7, 9);
		g4.addEdge(8, 9);
		g4.addEdge(9, 8);
		System.out.println("\nCFC no quarto grafo: ");
		g4.SCC();

		Graph g5 = new Graph(5);
        	g5.addEdge(0, 1);
        	g5.addEdge(1, 2);
        	g5.addEdge(2, 3);
        	g5.addEdge(2, 4);
        	g5.addEdge(3, 0);
        	g5.addEdge(4, 2);
        	System.out.println("\nCFC no quinto grafo: ");
        	g5.SCC();
	}
}
