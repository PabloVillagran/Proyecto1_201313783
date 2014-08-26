package llGame;

import java.util.Scanner;

import llGame.Interfaz;

public class Juego {
	Scanner sc = new Scanner(System.in);
	int tk;
	String cartas [] = {"1","1","1","1","1","2","2","3","3","4","4","5","5","6","7","8"};
	String mazo[]= new String[16],fuera,op;
	String [] jugador1=new String[2], jugador2=new String[2];
	String[] manoj= new String[2], manoc = new String [2];
	boolean round;int nmazo=16; int p;
	private boolean[] handmaid={false,false};
	
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
		op=sc.next();
		System.out.println("Ha jugado " + op);
		if(op.contains("1")){
			String value = manoj[1];
			if(value.contains("(7) Countess")){
				if(manoj[0].contains("(5) Prince") || value.contains("(6) King")){
					System.out.println("Descarte de Countess obligatorio!");
					carta("7",1);
					sc.next();
				}
			}
			carta(manoj[0].substring(manoj[0].indexOf("!")+1), p);
			fuera = fuera+", " + manoj[0].substring(manoj[0].indexOf("!")+1);
			manoj[0]=manoj[1];
			manoj[1]="vacio";
		}else if(op.contains("2")){
			carta(manoj[1].substring(manoj[1].indexOf("!")+1), p);
			fuera = fuera+", " + manoj[1].substring(manoj[1].indexOf("!")+1);
			manoj[1]="vacio";
		}else if(op.contains("3")){
			System.out.println("Esta seguro que desea salir? (s/n)");
			if(sc.next().contains("s")){
				System.exit(0);
			}else{
				this.interfaz();
			}
		}else{
			System.out.println("Seleccione una opcion valida!");
			this.interfaz();
		}
	}
	
	void carta(String numero, int t) {
		switch(numero){
		case "1":
			System.out.println("Adivine la carta carta de "+ jugador2[0]+":\n"
					+ "(2)Priest, (3)Baron, (4)Handmaid, (5)Prience, "
					+ "(6)King, (7)Contess, (8)Princess\n"
					+ "Ingrese el numero de la carta que desea adivinar.");
			if(sc.next().compareTo(manoc[0])== 0){
				System.out.println("Has descubierto a tu oponente! Ganas el round");
				jugador1[1]= String.valueOf(Integer.valueOf(jugador1[1])+1);
				round = false;
			}else{
				System.out.println("Incorrecto!");
			}
			break;
		case "2":
			System.out.println("La carta del oponete es: " + 
					Interfaz.reglas[Integer.valueOf(manoc[0])]);
			break;
		case "3":
			if(op.contains("1")){
				int valor=Integer.valueOf(manoj[0].substring(manoj[0].indexOf("!")+1));
				int valo2=Integer.valueOf(manoc[0]);
				System.out.println("Tu carta es "
						+Interfaz.reglas[valor].substring(0,Interfaz.reglas[valor].indexOf("<")-1)
						+" La carta de "+jugador2[0]+" es " 
						+Interfaz.reglas[valo2].substring(0,Interfaz.reglas[valo2].indexOf("<")-1)
				);
				if(valor > valo2){
					System.out.println("Tu carta es mayor! ganas la ronda!");
					jugador1[1]= String.valueOf(Integer.valueOf(jugador1[1])+1);
					round = false;
				}
				if(valor < valo2){
					System.out.println("Tu carta es menor! pierdes la ronda!");
					jugador2[1]= String.valueOf(Integer.valueOf(jugador2[1])+1);
					round = false;
				}
			}if(op.contains("2")){
				int valor=Integer.valueOf(manoj[1].substring(manoj[1].indexOf("!")+1));
				int valo2=Integer.valueOf(manoc[0]);
				System.out.println("Tu carta es "
						+Interfaz.reglas[valor].substring(0,Interfaz.reglas[valor].indexOf("<")-1)
						+" La carta de "+jugador2[1]+" es " 
						+Interfaz.reglas[valo2].substring(0,Interfaz.reglas[valo2].indexOf("<")-1)
				);
				if(valor > valo2){
					System.out.println("Tu carta es mayor! ganas la ronda!");
					jugador1[1]= String.valueOf(Integer.valueOf(jugador1[1])+1);
					round = false;
				}
				if(valor < valo2){
					System.out.println("Tu carta es menor! pierdes la ronda!");
					jugador2[1]= String.valueOf(Integer.valueOf(jugador2[1])+1);
					round = false;
				}
			}
			break;
		case "4":
			System.out.println("Estas protegido por 1 turno.");
			handmaid[0]=true;
			break;
		case "5":
			System.out.println("Deseas descartar tu mano o la de tu oponente?\n"
					+ "(1) tu mano?\n (2) su mano?");
			if(sc.next().contains("1") && handmaid[1]){
				manoj[0]=mazo[nmazo-1];
				mazo[nmazo-1]="jugada";
			}else if(sc.next().contains("2") && !handmaid[1]){
				manoc[0]=mazo[nmazo-1];
				mazo[nmazo-1]="jugada";
			}else{
				System.out.println("Ingresa una opcion valida!");
				carta("5",t);
			}
			break;
		case "6":
			if(op.contains("1")){
				System.out.println("Se cambiaran las cartas. ");
				manoc[1]=manoj[1];
				manoj[1]=manoc[0];
				manoc[0]=manoc[1];
				manoc[1]="vacio";
			}if(op.contains("2")){
				System.out.println("Se cambiaran las cartas. ");
				manoj[1]=manoc[0];
				manoc[0]=manoj[0];
				manoj[0]="vacio";
			}
			break;
		case "7":
			System.out.println("Countness descartada!");
			break;
		case "8":
			System.out.println("La princesa ha sido descartada! pierdes la ronda.");
			jugador2[1]= String.valueOf(Integer.valueOf(jugador2[1])+1);
			break;
		}

	}

	@SuppressWarnings("unused")
	void partida(){
		
		System.out.println("Alto ahi!, �Quien es usted?");
		jugador1[0] = sc.next();
		System.out.println("�Conoces a tu oponente?, �Quien es?");
		jugador2[0] = sc.next();
		
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

