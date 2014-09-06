package llGame;

import java.util.Scanner;

import llGame.Interfaz;

public class Juego {
	Scanner sc = new Scanner(System.in);
	int tokens, tokenp1, tokenp2;
	int cartas [] = new int[16];
	int mazo[]= new int[16];
	String jugador1, jugador2, fuera, op, oculta;
	int[] manoj= new int[2], manoc = new int [2];
	boolean round;int nmazo=16; int p;
	boolean[] handmaid={false,false};
	boolean debug;
	
	public Juego(int tokens, boolean debug){
		this.tokens=tokens;
		tokenp1=0; tokenp2=0;
		this.debug=debug;
		p=0;
	}

	void barajear(){
		for(int j=0; j<=15; j++)mazo[j]=0;
		int valor=1;
		for(int k=0;k<=15;k++){
			if(k==5)valor++;
			if(k==7)valor++;
			if(k==9)valor++;
			if(k==11)valor++;
			if(k>12)valor++;
			cartas[k]=valor;
		}
		nmazo=16;
		fuera="";
		handmaid[0]=false;handmaid[1]=false;
		for(int i=0; i<=15;i++){
			while(mazo[i]==0){
				//System.out.println("-JUEGO-");
				int random = (int)(Math.random()*16);
				if(cartas[random]!=-1){
					mazo[i]=cartas[random];
					cartas[random]=-1;
				}else{
					mazo[i]=0;
				}
			}
		}
		
		if(debug){
			System.out.print("Mazo: ");
			for(int i=0;i<=15;i++)System.out.print(mazo[i] +", ");
			System.out.print("\n");
		}
		
		round=true;
		partida();
	}
	
	void partida(){
		
		if(p==0){
			System.out.println("Alto ahi!, �Quien es usted?");
			jugador1 = sc.nextLine();
			System.out.println("�Conoces a tu oponente?, �Quien es?");
			jugador2 = sc.nextLine();
			if((int)((Math.random()*9))<5){
				p=1;
				System.out.println("Comenzara " + jugador1 + "!!");
			}else{
				p=2;
				System.out.println("Comenzara " + jugador2 + "!!");
			}
			System.out.println("El primero en tener "+ tokens+ " tokens gana !");
		}
		oculta = String.valueOf(mazo[15]);
		if(debug)fuera= "oculta["+oculta+"], ";
		fuera = mazo[12]+", "+mazo[13]+", "+mazo[14];
		manoc[0]=mazo[11];
		manoj[0]=mazo[10];
		for(int i=15;i>=10;i--)mazo[i]=0;
		nmazo = 10;
		
		while(round){
			turno(p);
			if(mazo[0]==0){
				System.out.println("------ya no hay cartas en el mazo!------");
				op="2";
				carta(3);
				System.out.println("------ya no hay cartas en el mazo!------");
			}
		}
		
		if(!((tokenp1<tokens) ^ (tokenp2<tokens))){
			barajear();
		}else{
			round=false;
			String ganador="";
			if(p==1)ganador=jugador2;
			if(p==2)ganador=jugador1;
			System.out.println("-|| Fin de la partida! ||-\n"
					+ "-||El ganador es: " + ganador + "||-");
		}
		
	}
	
	void turno(int q){
		if(q==1){
			manoj[1]= mazo[nmazo-1];
			mazo[nmazo-1]=0;
			nmazo--;
			metodoDebug();
			interfaz();
			metodoDebug();
			handmaid[1]=false;
			p++;
		}
		if(q==2){
			manoc[1]=mazo[nmazo-1];
			mazo[nmazo-1]=0;
			nmazo--;
			metodoDebug();
			logica();
			metodoDebug();
			handmaid[0]=false;
			p--;
		}
	}

	void interfaz(){
		
		System.out.print("\n-----------turno "+jugador1+"--------------\n");
		System.out.print(jugador2+" (" + tokenp2 +" tokens)"+" cartas: [X]\n"
				+ "\nN�mero de cartas en el mazo: "+ nmazo +".\n"
				+ "Cartas fuera de juego: " + fuera + ".\n"
				+ jugador1+" (" + tokenp1+" tokens)"+" Tienes en tu mano: \n"
				+ "(1) "+Interfaz.reglas[manoj[0]]+"\n"
				+ "(2) "+Interfaz.reglas[manoj[1]]+"\n"
				+ "(3) Terminar el juego.\n"
				+ "�Que desea hacer? ");
		op=sc.next();
		
		if(manoj[1]==7&& op.contains("1")){
			if(manoj[0]==5 || manoj[0]==6){
				System.out.println("Descarte de Countess obligatorio!");
				op=null;
				interfaz();
			}
		}
		if(manoj[0]==7&& op.contains("2")){
			if(manoj[1]==5||manoj[1]==6){
				System.out.println("Descarte de Countess obligatorio!");
				op=null;
				interfaz();
			}
		}
		
		System.out.println("Ha jugado " + op);
		if(op.contains("1")){
			fuera = fuera+", " + manoj[0];
			if(!handmaid[1] || (manoj[0]==8||manoj[0]==7||manoj[0]==4)){
				carta(manoj[0]);
			}else{
				System.out.println(jugador2+" esta protegido, tu carta no tiene efecto");
			}
			manoj[0]=manoj[1];
			manoj[1]=0;
		}else if(op.contains("2")){
			fuera = fuera+", " + manoj[1];
			if(!handmaid[1] || (manoj[1]==8||manoj[1]==7||manoj[0]==4)){
				carta(manoj[1]);
			}else{
				System.out.println(jugador2+" esta protegido, tu carta no tiene efecto");
			}
			manoj[1]=0;
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
		
		System.out.print("\n-----------turno "+jugador1+"--------------\n");
	}

	void carta(int numero) {
		switch(numero){
		case 1:
			System.out.println("Adivine la carta carta de "+ jugador2+":\n"
						+ "(2)Priest, (3)Baron, (4)Handmaid, (5)Prience, "
						+ "(6)King, (7)Contess, (8)Princess\n"
						+ "Ingrese el numero de la carta que desea adivinar.");
			if((sc.next().compareTo(String.valueOf(manoc[0])))== 0){
				System.out.println("Has descubierto a tu oponente! Ganas el round");
				tokenp1++;
				p=0;
				round = false;
			}else{
				System.out.println("Incorrecto!");
			}
			break;
		case 2:
			System.out.println("La carta del oponete es: " + 
					Interfaz.reglas[Integer.valueOf(manoc[0])]);
			break;
		case 3:
			int valor=0;
			if(op.contains("1")){
				valor=manoj[1];
			}if(op.contains("2")){
				valor=manoj[0];
			}
			System.out.println("Tu carta es "
					+Interfaz.reglas[valor].substring(0, Interfaz.reglas[valor].indexOf(":"))
					+" La carta de "+jugador2+" es " 
					+Interfaz.reglas[manoc[0]].substring(0, Interfaz.reglas[manoc[0]].indexOf(":"))
			);
			if(valor > manoc[0]){
				System.out.println("Tu carta es mayor! ganas la ronda!");
				tokenp1++;
				if(p==1){
					p--;
				}
				if(mazo[0]==0){
					p=1;
				}
				round = false;
			}
			if(valor < manoc[0]){
				System.out.println("Tu carta es menor! pierdes la ronda!");
				if(p==2){
					p=3;
				}else{
					p=1;
				}
				if(mazo[0]==0){
					p=2;
				}
				tokenp2++;
				round = false;
			}
			if(valor == manoc[0]){
				System.out.println("las cartas son iguales!");
				if(mazo[0]==0){
					round=false;
				}
				System.out.println("Deberan seguir jugando.");
			}
			break;
		case 4:
			System.out.println("Estas protegido por 1 turno.");
			handmaid[0]=true;
			break;
		case 5:
			System.out.println("Deseas descartar tu mano o la de tu oponente?\n"
					+ "(1) tu mano?\n (2) su mano?");
			op=sc.next();
			if(op.contains("1")){
				manoj[0]=manoj[1];
				fuera=fuera+", "+manoj[0];
				System.out.println("Descartas un " + manoj[0]);
				manoj[0]=mazo[nmazo-1];
				mazo[nmazo-1]=0;
				nmazo--;
			}else if(op.contains("2")){
				/*if(manoc[0]==8){
					System.out.println(jugador2 + " ha descartado a la princesa! Ganas el round!");
					manoc[0]=0;
					fuera=fuera+", "+manoj[0];
					tokenp1++;
					p=0;
					round=false;
				}else{*/
					System.out.println(jugador2 +" ha descartado su mano.");
					fuera=fuera+", "+manoc[0];
					manoc[0]=mazo[nmazo-1];
					mazo[nmazo-1]=0;
					nmazo--;
				//}
			}else{
				System.out.println("Ingresa una opcion valida!");
				carta(5);
			}
			break;
		case 6:
			if(op.contains("1")){
				System.out.println("Se cambiaran las cartas. ");
				manoc[1]=manoj[1];
				manoj[1]=manoc[0];
				manoc[0]=manoc[1];
				manoc[1]=0;
			}if(op.contains("2")){
				System.out.println("Se cambiaran las cartas. ");
				manoj[1]=manoc[0];
				manoc[0]=manoj[0];
				manoj[0]=manoj[1];
				manoj[1]=0;
			}
			break;
		case 7:
			System.out.println("Countness descartada!");
			break;
		case 8:
			System.out.println("La princesa ha sido descartada! pierdes la ronda.");
			tokenp2++;
			p=1;
			round=false;
			break;
		}
	
	}
	
	void logica(){
		System.out.print("\n-----------turno "+jugador2+"--------------\n");
		System.out.println(jugador2 + ": Ahora es mi turno!");
		
		int jugada=0;
		if(manoc[0]==8){
			jugada=manoc[1];
		}else if(manoc[1]==8){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==7 && (manoc[1]==5||manoc[1]==6)){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[1]==7 && (manoc[0]==5||manoc[0]==6)){
			jugada=manoc[1];
		}else if(manoc[0]==7 && manoc[1]<=4){
			jugada=manoc[1];
		}else if(manoc[1]==7 && manoc[0]<=4){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==6 && manoc[1]<6){
			jugada=manoc[1];
		}else if(manoc[1]==6 && manoc[0]<6){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if((manoc[0]==5 && manoc[1]==4)||(manoc[0]==5 && manoc[1]==1)){
			jugada=manoc[1];
		}else if((manoc[1]==5 && manoc[0]==4)||(manoc[1]==5 && manoc[0]==1)){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if((manoc[0]==5 && fuera.contains("8"))||(manoc[0]==5&&manoc[1]<4)){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if((manoc[1]==5 && fuera.contains("8"))||(manoc[1]==5&&manoc[0]<4)){
			jugada=manoc[1];
		}else if(manoc[0]==4 && manoc[1]==4){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==4 && manoc[1]<4){
			jugada=manoc[1];
		}else if(manoc[1]==4 && manoc[0]<4){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==3 && manoc[1]==3){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==3 && manoc[1]<3){
			jugada=manoc[1];
		}else if(manoc[1]==3 && manoc[0]<3){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==2&& manoc[1]==2){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==2 && manoc[1]<2){
			jugada=manoc[1];
		}else if(manoc[1]==2 && manoc[0]<2){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}else if(manoc[0]==1&&manoc[1]==1){
			jugada=manoc[0];
			manoc[0]=manoc[1];
		}
		manoc[1]=0;
		
		switch(jugada){
		case 1:
			if(!handmaid[0]){
				int choice = (int)((Math.random()*6)+2);
				System.out.println("Juego con un guardia.\n Tu carta es " + choice);
				if(manoj[0]==choice){
					System.out.println("Lo sabia!");
					tokenp2++;
					p=3;
					round = false;
				}else{
					System.out.println("Adivinare la proxima!");
				}
			}else{
				System.out.println("Juego con un guardia.\n Estas protegido, solo alargas lo inevitable!");
			}
			break;
		case 2:
			if(!handmaid[0]){
				System.out.println("Juego con un Priest");
				System.out.println("Tu carta es " + manoj[0]);
			}else{
				System.out.println("Juego con un Priest... pero tu handmaid no deja ver tu carta...");
			}
			break;
		case 3:
			if(!handmaid[0]){
				System.out.println("Juego con Baron.");
				op="2";
				carta(3);
			}else{
				System.out.println("Juego con Baron.\n Handmaid te ha salvado!");
			}
			break;
		case 4:
			System.out.println("Juego con Handmaid, ahora estoy protegido.");
			handmaid[1]=true;
			break;
		case 5:
			if(manoc[0]<4 || handmaid[0]){
				System.out.println("Juego Prince, y descarto mi mano.");
				fuera= fuera + ", " + manoc[0];
				manoc[0]=mazo[nmazo-1];
				mazo[nmazo-1]=0;
				nmazo--;
			}else{
				System.out.println("Juego Prince, debes descartar tu mano");
				fuera = fuera + ", "+manoj[0];
				/*if(manoj[0]==8){
					System.out.println("Haz descartado a Princess! pierdes el round.");
					manoj[0]=0;
					tokenp2++;
					round=false;
				}else{*/
					if(nmazo!=1){
						manoj[0]=mazo[nmazo-1];
						mazo[nmazo-1]=0;
						nmazo--;
					}else{
						System.out.println("ya no hay cartas en el mazo");
					}
				//}
			}
			break;
		case 6:
			if(!handmaid[0]){
				System.out.println("Juego con King!");
				op="1";
				carta(6);
			}else{
				System.out.println("Juego con King!\n me quedare con mi carta gracias a tu handmaid.");
			}
			break;
		case 7:
			System.out.println("Juego con la Countess");
			break;
		case 8:
			System.out.println("Debo descartar a la princesa...");
			tokenp2++;
			p=2;
			round=false;
			break;
		default:
			break;
		}
		fuera= fuera + ", " + jugada;
		System.out.print("\n-----------turno "+jugador2+"--------------\n");
	}
	
	void metodoDebug(){
		if(debug){
			System.out.print("\n------------Debug-----------\nMazo: ");
			for(int i=0;i<=15;i++)System.out.print(mazo[i] +", ");
			System.out.print("\n Mano oponente: ");
			for(int i=0; i<=1;i++)System.out.print(manoc[i]+", ");
			System.out.print("\n------------Debug-----------\n");
		}
	}
}

