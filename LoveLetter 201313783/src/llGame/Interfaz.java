package llGame;

import java.util.Scanner;

class Interfaz {
	Scanner sc = new Scanner(System.in);
	public int tokens=7;
	static public String reglas []={"--Carta(Valor): descripcion<# de cartas en el maso>--"
			, "Guard [1]: trata de adivinar la carta del oponente, si es correcto el oponente pierde la ronda!<5>"
			, "Priest [2]: ve la carta del oponente!<2>"
			, "Baron [3]: compara tu carta y la del oponente, la carta mas alta gana la ronda!<2>"
			, "HandMaid [4]: Protege al usuario de los efectos del oponente por 1 turno!<2>"
			, "Prince [5]: El jugador descarta su mano o la del oponente, sin aplicar los efectos!<2>"
			, "King [6]: Intercambia las manos de los jugadores!<1>"
			, "Countess [7]: Carta mas alta, se debe descartar si hay un rey o principe en la mano!<1>"
			, "Princess [8]: Si se descarta a la princesa se pierde la ronda!<1>"};
	static Juego juego;
	boolean debug=false;
	
	public Interfaz(){
		menu1();
	}
	
	void menu1(){
		String op;
		System.out.println("-=Bienvenido a Love Letter=-\n"
				+ "(1) Jugar partida \n"
				+ "(2) Seleccionar el numero de Tokens a Jugar\n"
				+ "(3) Mostrar las reglas del juego\n"
				+ "(4) Salir");
		System.out.print("¿Que desea hacer? ");
	
		op = sc.next();
		
		switch(op){
		case "1":
			/*try{
				Runtime.getRuntime().exec("cls");
			}catch(IOException e){
				e.printStackTrace();
			}*/
			juego = new Juego(tokens, debug);
			juego.barajear();
			break;
		case "2":
			System.out.println("Seleccione el numero de tokes a jugar:\n "
					+ "(numero impar entre 1 y 7)");
			tokens = sc.nextInt();
			if((tokens % 2) == 0){
				System.out.println("Cantidad de tokens par\n "
						+ "Se cambiara el numero de tokens a "+ (tokens-1));
				tokens--;
				menu1();
			}
			this.menu1();
			break;
		case "3":
			instrucciones();
			break;
		case "4":
			System.exit(0);
			break;
		case "debug":
			debug=true;
			this.menu1();
			break;
		default:
			System.out.println("Ingrese una opcion valida");
			debug=false;
			this.menu1();
			break;
		}
	}
	
	void instrucciones(){
		System.out.print("--Inicio del juego--\n"
				+ "Se descarta 1 carta del mazo, se reparta una carta para cada jugador y se descartan 3 cartas\n"
				+ "boca arriba para que ambos jugadores sepan que cartas estan fuera.\n"
				+ ":-Inicio del Turno-:\n"
				+ "El jugador toma 1 carta del mazo y debe descartar otra, aplicando sus efectos sobre si mismo o sobre el contrincante.\n"
				+ "Continuar? (s/n)");
		String de = sc.next();
		System.out.println(de);
		if(de.contains("s")){
			for(int i=0;i<=8;i++){
				System.out.println(reglas[i]);
			}
			System.out.println("Continuar? ");
			System.out.println(sc.next());
			this.menu1();
		}else{
			this.menu1();
		}
	}

}
