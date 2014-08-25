package llGame;

import java.util.Scanner;

import llGame.Interfaz;

public class Juego {
	Scanner sc = new Scanner(System.in);
	int tk;
	String cartas [] = {"1","1","1","1","1","2","2","3","3","4","4","5","5","6","7","8"};
	String mazo[]= new String[16],fuera;
	String [] jugador1=new String[2], jugador2=new String[2];
	String[] manoj= new String[2], manoc = new String [2];
	boolean round;int nmazo=16; int p;
	
	public Juego(int tokens){
		this.tk=tokens;
		for(int i=0; i<=15;i++){
			while(mazo[i]==null){
				//System.out.println("-JUEGO-");
				int random = (int)(Math.random()*16);
				if(cartas[random]!="X"){
					mazo[i]=cartas[random];
					cartas[random]="X";
				}else{
					mazo[i]=null;
				}
			}
		}
		jugador1[1]="0";jugador2[1]="0";
		round=true;
		partida();
	}
	
	void interfaz(){
		System.out.print(jugador2[0]+" (" + jugador2[1]+" tokens)"+" cartas: [X]\n"
				+ "Mazo: "+ nmazo +".\n"
				+ "Cartas fuera de juego: " + fuera + ".\n"
				+ jugador1[0]+" (" + jugador1[1]+" tokens)"+" Tienes en tu mano: \n"
				+ "(1) "+manoj[0].substring(0,manoj[0].indexOf("!")+1)+"\n"
				+ "(2) "+manoj[1].substring(0,manoj[1].indexOf("!")+1)+"\n"
				+ "(3) Terminar el juego.\n"
				+ "�Que desea hacer? ");
		String op = sc.next();
		switch(op){
		case "1":
			carta(manoj[0].substring(manoj[0].indexOf("!")+1), p);
			break;
		case "2":
			carta(manoj[1].substring(manoj[1].indexOf("!")+1), p);
			break;
		case "3":
			System.out.println("Esta seguro que desea salir? (s/n)");
			if(sc.next().contains("s")){
				System.exit(0);
			}else{
				this.interfaz();
			}
			break;
		default:
			System.out.println("Seleccione una opcion valida!");
			this.interfaz();
			break;
		}
	}
	
	void carta(String numero, int t) {
		if(t==1){
			switch(numero){
			case "1":
				
				break;
			case "2":
				System.out.println("La carta del oponete es: " + 
						Interfaz.reglas[Integer.valueOf(manoc[0])]);
				break;
			case "3":
				
				break;
			case "4":
				
				break;
			case "5":
				
				break;
			case "6":
				
				break;
			case "7":
				
				break;
			case "8":
				
				break;
			}
		}else{
			System.out.println(numero);
		}
	}

	void partida(){
		
		System.out.println("Alto ahi!, �Quien es usted?");
		jugador1[0] = sc.next();
		if(jugador1[0]==null)jugador1[0]="Jugador1";
		System.out.println("�Conoces a tu oponente?, �Quien es?");
		jugador2[0] = sc.next();
		if(jugador2[0]==null)jugador2[0]="computadora";
		
		System.out.println("Ve, busca a la princesa o a alguien que le pueda entregar tu carta!\n");
		String oculta = mazo[15];
		fuera = mazo[12]+", "+mazo[13]+", "+mazo[14];
		nmazo = 10;
		for(int i=15;i>=12;i--)mazo[i]="jugada";
		manoj[0]=Interfaz.reglas[Integer.valueOf(mazo[11])]+mazo[11]; 
		manoc[0]=mazo[10];
		mazo[11]="jugada";mazo[10]="jugada";
		p=1;
		while(round){
			turno(p);
		}
	}
	
	void turno(int q){
		if(q==1){
			manoj[1]= Interfaz.reglas[Integer.valueOf(mazo[nmazo-1])]+mazo[nmazo-1];
			mazo[nmazo-1]="jugada";
			nmazo--;
			p++;
			interfaz();
		}else{
			manoc[1]=mazo[nmazo-1];
			p--;
			interfaz();
		}
	}
}

